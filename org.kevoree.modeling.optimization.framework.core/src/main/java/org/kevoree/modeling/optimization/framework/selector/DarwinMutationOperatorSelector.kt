package org.kevoree.modeling.optimization.engine.genetic.selector

import org.kevoree.modeling.optimization.api.mutation.MutationOperatorSelector
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import java.util.Random
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.LinkedList
import java.util.Comparator
import org.kevoree.modeling.optimization.framework.selector.MutatorRanking
import org.kevoree.modeling.optimization.api.solution.Solution
import org.kevoree.modeling.optimization.api.solution.SolutionMutationListener
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import java.util.SortedMap
import java.util.LinkedHashMap

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 28/10/2013
 * Time: 18:29
 */
public class DarwinMutationOperatorSelector<A : KMFContainer>(override val operators: List<MutationOperator<A>>, val probability: Double) : MutationOperatorSelector<A>, SolutionMutationListener<A> {

    val random = Random()
    val ranking = HashMap<FitnessFunction<A>, HashMap<MutationOperator<A>, MutatorRanking<A>>>()

    private fun updateSelectionProbability(fitness: FitnessFunction<A>) {
        val fitnessRanks = ranking.get(fitness)
        //select positive impacted values

        var probacounter = 0

        for ((mutationoperator, mutatorranking) in fitnessRanks) {


            println("$mutationoperator -> $mutatorranking")
            if (mutatorranking.positiveSum > 0)
            {
                probacounter += 1
            }
        }





        //determine i value
        val nomalizer  = 1/probacounter

        //assign for each positive value a rank
       // val list = arrayListOf<MutationOperator<A>>()


        val sortedMap =  LinkedHashMap<MutationOperator<A>, MutatorRanking<A>>

        var i  = 0.0

        for ((mutationoperator, mutatorranking) in sortedMap) {

            if (mutatorranking.positiveSum > 0)
            {
                mutatorranking.selectionProbability  = nomalizer * (probacounter-i)
            }
            i += 1
        }









    }

    override fun process(previousSolution: Solution<A>, solution: Solution<A>) {
        for(fitness in solution.getFitnesses()){
            val currentScore = solution.getScoreForFitness(fitness)!!
            val previousScore = previousSolution.getScoreForFitness(fitness)!!
            val impactOnFitness = previousScore - currentScore
            val lastOperator = solution.context.operator!!
            val rankinValues: HashMap<MutationOperator<A>, MutatorRanking<A>> = ranking.getOrPut(fitness, {
                HashMap<MutationOperator<A>, MutatorRanking<A>>()
            })
            val currentRanking: MutatorRanking<A> = rankinValues.getOrPut(lastOperator, {
                MutatorRanking<A>(0.0, 0.0, 0.0, 0.0, 0)
            })

            if(impactOnFitness <= 0){
                currentRanking.negativeSum += impactOnFitness
            } else {
                currentRanking.positiveSum += impactOnFitness
            }
            currentRanking.nbSelection += 1
            currentRanking.positiveMean = currentRanking.positiveSum / currentRanking.nbSelection
            currentRanking.negativeMean = currentRanking.negativeSum / currentRanking.nbSelection

            //update best score
            val bestFoundedRanking = bestRanking.get(fitness)
            if(bestFoundedRanking == null){
                bestRanks.put(fitness, lastOperator)
                bestRanking.put(fitness, currentRanking)
            } else {
                if(bestFoundedRanking.positiveMean < currentRanking.positiveMean){
                    bestRanks.put(fitness, lastOperator)
                    bestRanking.put(fitness, currentRanking)
                }
            }
            updateSelectionProbability(fitness)
        }
    }

    override fun select(solution: Solution<A>): MutationOperator<A> {
        val randomNb = random.nextInt(100)
        if(randomNb < probability){
            val worstFitnessToFix = selectWorstCurrentFitness(solution)
            val potentialRanks = ranking.get(worstFitnessToFix)
            if(potentialRanks == null){
                return randomSelector()
            }
            var indiceBegin = 0
            var currentBestOperator: MutationOperator<A>? = null
            var indice = random.nextDouble()
            for(rank in potentialRanks){
                val loopRank = rank.getValue()
                if(rank.getValue().positiveMean > 0){
                    if(indice >= indiceBegin && indice < loopRank.selectionProbability){
                        currentBestOperator = rank.getKey()
                        break;
                    }
                    indiceBegin = loopRank.selectionProbability //move lower boundary
                }
            }
            if(currentBestOperator == null){
                throw Exception("Bad proba selection behavior !!!, internal problem")
            } else {
                return currentBestOperator
            }
        } else {
            //random selection
            var indice = random.nextInt(operators.size())
            return operators.get(indice)
        }
    }

    private fun randomSelector(): MutationOperator<A> {
        var indice = random.nextInt(operators.size())
        return operators.get(indice)
    }

    private fun selectWorstCurrentFitness(solution: Solution<A>): FitnessFunction<A> {
        var worstScore = 0.0
        var worst: FitnessFunction<A>? = null
        for(fitness in solution.getFitnesses()){
            val loopScore = solution.getScoreForFitness(fitness)!!
            if(worst == null || worstScore <= solution.getScoreForFitness(fitness)!!){
                worst = fitness
                worstScore = loopScore
            }
        }
        return worst!!
    }

    public fun toString(): String {
        val buffer = StringBuffer()
        buffer.append("DarwinMutationOperatorSelector\n")
        buffer.append("Best Current Ranking\n")
        for(rank in bestRanks){
            buffer.append("fitness ")
            buffer.append(rank.key)
            buffer.append(" -> ")
            buffer.append(rank.value.javaClass.getSimpleName())
            buffer.append("\n")
        }
        buffer.append("Full ranking report\n")
        for(rank in ranking){
            buffer.append("fitness ")
            buffer.append(rank.key)
            buffer.append("\n")
            for(mut in rank.getValue()){
                buffer.append("mutator ")
                buffer.append(mut.getKey().javaClass.getSimpleName())
                buffer.append(" -> ")
                val value = mut.getValue()
                buffer.append("(positiveMean=")
                buffer.append(value.positiveMean)
                buffer.append("(negativeMean=")
                buffer.append(value.negativeMean)
                buffer.append(",positiveSum=")
                buffer.append(value.positiveSum)
                buffer.append(",negativeSum=")
                buffer.append(value.negativeSum)
                buffer.append(",nbSelection=")
                buffer.append(value.nbSelection)
                buffer.append(")\n")
            }
        }
        return buffer.toString()
    }

}

