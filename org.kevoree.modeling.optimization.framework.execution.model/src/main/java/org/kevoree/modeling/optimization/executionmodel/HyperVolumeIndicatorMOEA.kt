package org.kevoree.modeling.optimization.executionmodel

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.text.MessageFormat
import java.util.ArrayList
import java.util.List
import org.moeaframework.core.FrameworkException
import org.moeaframework.core.NondominatedPopulation
import org.moeaframework.core.Population
import org.moeaframework.core.PopulationIO
import org.moeaframework.core.Problem
import org.moeaframework.core.indicator.NormalizedIndicator
import org.moeaframework.core.Settings
import org.moeaframework.core.Solution
import org.moeaframework.util.io.RedirectStream

public open class HyperVolumeIndicatorMOEA(problem : Problem, referenceSet : NondominatedPopulation) : NormalizedIndicator(problem, referenceSet) {

    public open fun evaluate(approximationSet : NondominatedPopulation) : Double

    {
        return evaluate(problem, normalize(approximationSet))
    }

    class object {
        protected open fun invert(problem : Problem, solution : org.moeaframework.core.Solution) : Unit {
            for (j in 0..problem.getNumberOfObjectives() - 1) {
                var value : Double = solution.getObjective(j)!!
                if (value < 0.0)
                {
                    value = 0.0
                }
                else
                    if (value > 1.0)
                    {
                        value = 1.0
                    }
                solution.setObjective(j, 1.0 - value)
            }
        }
        private open fun dominates(solution1 : org.moeaframework.core.Solution, solution2 : org.moeaframework.core.Solution, numberOfObjectives : Int) : Boolean {
            var betterInAnyObjective : Boolean = false
            var worseInAnyObjective : Boolean = false
            for (i in 0..numberOfObjectives - 1) {
                if (worseInAnyObjective)
                {
                    break
                }
                if ((solution1.getObjective(i))!! > (solution2.getObjective(i))!!)
                {
                    betterInAnyObjective = true
                }
                else
                    if ((solution1.getObjective(i))!! < (solution2.getObjective(i))!!)
                    {
                        worseInAnyObjective = true
                    }
            }
            return !worseInAnyObjective && betterInAnyObjective
        }
        private open fun swap(population : List<org.moeaframework.core.Solution>, i : Int, j : Int) : Unit {
            var temp : org.moeaframework.core.Solution = population.get(i)
            population.set(i, population.get(j))
            population.set(j, temp)
        }
        private open fun filterNondominatedSet(population : List<org.moeaframework.core.Solution>, numberOfSolutions : Int, numberOfObjectives : Int) : Int {
            var i : Int = 0
            var n : Int = numberOfSolutions
            while (i < n)
            {
                var j : Int = i + 1
                while (j < n)
                {
                    if (dominates(population.get(i), population.get(j), numberOfObjectives)!!)
                    {
                        n--
                        swap(population, j, n)
                    }
                    else
                        if (dominates(population.get(j), population.get(i), numberOfObjectives)!!)
                        {
                            n--
                            swap(population, i, n)
                            i--
                            break
                        }
                        else
                        {
                            j++
                        }
                }
                i++
            }
            return n
        }
        private open fun surfaceUnchangedTo(population : List<org.moeaframework.core.Solution>, numberOfSolutions : Int, objective : Int) : Double {
            var min : Double = population.get(0).getObjective(objective)!!
            for (i in 1..numberOfSolutions - 1) {
                min = Math.min(min, population.get(i).getObjective(objective))
            }
            return min
        }
        private open fun reduceNondominatedSet(population : List<org.moeaframework.core.Solution>, numberOfSolutions : Int, objective : Int, threshold : Double) : Int {
            var n : Int = numberOfSolutions
            for (i in 0..n - 1) {
                if ((population.get(i).getObjective(objective))!! <= threshold)
                {
                    n--
                    swap(population, i, n)
                }
            }
            return n
        }
        private open fun calculateHypervolume(population : List<org.moeaframework.core.Solution>, numberOfSolutions : Int, numberOfObjectives : Int) : Double {
            var volume : Double = 0.0
            var distance : Double = 0.0
            var n : Int = numberOfSolutions
            while (n > 0)
            {
                var numberOfNondominatedPoints : Int = filterNondominatedSet(population, n, numberOfObjectives - 1)
                var tempVolume : Double = 0.0
                if (numberOfObjectives < 3)
                {
                    tempVolume = population.get(0).getObjective(0)
                }
                else
                {
                    tempVolume = calculateHypervolume(population, numberOfNondominatedPoints, numberOfObjectives - 1)
                }
                var tempDistance : Double = surfaceUnchangedTo(population, n, numberOfObjectives - 1)
                volume += tempVolume * (tempDistance - distance)
                distance = tempDistance
                n = reduceNondominatedSet(population, n, numberOfObjectives - 1, distance)
            }
            return volume
        }
        open fun evaluate(problem : Problem, approximationSet : NondominatedPopulation) : Double {
            var isInverted : Boolean = true
            var isCustomHypervolume : Boolean = (Settings.getHypervolume() != null) && ((problem.getNumberOfObjectives())!! > 2)
            if (isCustomHypervolume)
            {
                isInverted = Settings.isHypervolumeInverted()
            }
            var solutions : List<org.moeaframework.core.Solution> = ArrayList<org.moeaframework.core.Solution>()
            for (solution : org.moeaframework.core.Solution in approximationSet)
            {
                for (i in 0..solution.getNumberOfObjectives() - 1) {
                    if ((solution.getObjective(i))!! > 1.0)
                    {
                        continue
                    }
                }
                var clone : org.moeaframework.core.Solution = solution.copy()!!
                if (isInverted)
                {
                    invert(problem, clone)
                }
                solutions.add(clone)
            }
            if (isCustomHypervolume)
            {
                return invokeNativeHypervolume(problem, solutions, isInverted)
            }
            else
            {
                return calculateHypervolume(solutions, (solutions.size())!!, (problem.getNumberOfObjectives())!!)!!
            }
        }
        protected open fun invokeNativeHypervolume(problem : Problem, solutions : List<org.moeaframework.core.Solution>, isInverted : Boolean) : Double {
            try
            {
                var command : String = Settings.getHypervolume()
                var nadirPoint : Double
                if (isInverted)
                {
                    nadirPoint = 0.0 - Settings.getHypervolumeDelta()
                }
                else
                {
                    nadirPoint = 1.0 + Settings.getHypervolumeDelta()
                }
                var approximationSetFile : File = File.createTempFile("approximationSet", null)
                approximationSetFile.deleteOnExit()
                PopulationIO.writeObjectives(approximationSetFile, solutions)
                var referencePointFile : File = null
                if (command.contains("{3}")!!)
                {
                    referencePointFile = File.createTempFile("referencePoint", null)
                    referencePointFile.deleteOnExit()
                    var referencePoint : org.moeaframework.core.Solution = org.moeaframework.core.Solution(DoubleArray(problem.getNumberOfObjectives()))
                    for (i in 0..problem.getNumberOfObjectives() - 1) {
                        referencePoint.setObjective(i, nadirPoint)
                    }
                    PopulationIO.writeObjectives(referencePointFile, Population(array<Solution>(referencePoint)))
                }
                var referencePointString : StringBuilder = null
                if (command.contains("{4}")!!)
                {
                    referencePointString = StringBuilder()
                    for (i in 0..problem.getNumberOfObjectives() - 1) {
                        if (i > 0)
                        {
                            referencePointString.append(' ')
                        }
                        referencePointString.append(nadirPoint)
                    }
                }
                var arguments : Array<Any> = array<Any>((problem.getNumberOfObjectives() as Int), (solutions.size() as Int), approximationSetFile.getCanonicalPath(), (if (referencePointFile == null)
                    ""
                else
                    referencePointFile.getCanonicalPath()), (if (referencePointString == null)
                    ""
                else
                    referencePointString.toString()))
                return invokeNativeProcess(MessageFormat.format(command, arguments))
            }
            catch (e : IOException) {
                throw FrameworkException(e)
            }
        }
        private open fun invokeNativeProcess(command : String) : Double {
            var process : Process = ProcessBuilder(Settings.parseCommand(command)).start()
            RedirectStream.redirect(process.getErrorStream(), System.err)
            var reader : BufferedReader = null
            var lastLine : String = null
            try
            {
                reader = BufferedReader(InputStreamReader(process.getInputStream()))
                var line : String = null
                while ((line = reader.readLine()) != null)
                {
                    lastLine = line
                }
            }
            finally
            {
                if (reader != null)
                {
                    reader.close()
                }
            }
            var tokens : Array<String> = lastLine.split("\\s+")
            return Double.parseDouble(tokens[(tokens.length)!! - 1])!!
        }
    }
}