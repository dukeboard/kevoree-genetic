package org.kevoree.modeling.optimization.util

import org.kevoree.modeling.optimization.executionmodel.FitnessMetric
import org.kevoree.modeling.optimization.executionmodel.Step
import org.kevoree.modeling.optimization.executionmodel.qualityIndicator.util.MetricsUtil
import org.kevoree.modeling.optimization.executionmodel.Metric

/**
 * Created by duke on 8/6/14.
 */

object MetricUpdater {

    fun update(indicator: Metric) {
        when (indicator) {
            is org.kevoree.modeling.optimization.executionmodel.ParetoMean -> {
                if(indicator.value == null){
                    indicator.value = 10000000.toDouble()
                }
                var currentStep: Step = indicator.eContainer() as Step
                var nb = 0
                var sum = 0.0
                for(solution in currentStep.solutions){
                    for(score in solution.scores){
                        nb = nb + 1
                        sum = sum + score.value!!
                    }
                }
                indicator.value = sum / nb
            }
            is org.kevoree.modeling.optimization.executionmodel.MinMean -> {
                indicator.value = 10000000.toDouble()
                var currentStep: Step = indicator.eContainer() as Step
                for(solution in currentStep.solutions){
                    var nb = 0
                    var sum = 0.0
                    for(score in solution.scores){
                        nb = nb + 1
                        sum = sum + score.value!!
                    }
                    val currentMean = sum / nb
                    if(currentMean < indicator.value!!){
                        indicator.value = currentMean
                    }
                }
            }
            is org.kevoree.modeling.optimization.executionmodel.Min -> {
                indicator.value = 10000000.toDouble()
                var currentStep: Step = indicator.eContainer() as Step
                for(solution in currentStep.solutions){
                    for(score in solution.scores){
                        if(score.fitness == indicator.fitness){
                            if(score.value!! < indicator.value!!){
                                indicator.value = score.value!!
                            }
                        }
                    }
                }
            }
            is org.kevoree.modeling.optimization.executionmodel.Mean -> {
                if(indicator.value == null){
                    indicator.value = 10000000.toDouble()
                }
                var currentStep: Step = indicator.eContainer() as Step
                var nb = 0
                var sum = 0.0
                for(solution in currentStep.solutions){
                    for(score in solution.scores){
                        if(score.fitness == indicator.fitness){
                            nb = nb + 1
                            sum = sum + score.value!!
                        }
                    }
                }
                indicator.value = sum / nb
            }
            is org.kevoree.modeling.optimization.executionmodel.MaxMean -> {
                if(indicator.value == null){
                    indicator.value = 0.toDouble()
                }
                var currentStep: Step = indicator.eContainer() as Step
                for(solution in currentStep.solutions){
                    var nb = 0
                    var sum = 0.0
                    for(score in solution.scores){
                        nb = nb + 1
                        sum = sum + score.value!!
                    }
                    val currentMean = sum / nb
                    if(currentMean > indicator.value!!){
                        indicator.value = currentMean
                    }
                }
            }

            is org.kevoree.modeling.optimization.executionmodel.Max -> {
                if(indicator.value == null){
                    indicator.value = 0.toDouble()
                }
                var currentStep: Step = indicator.eContainer() as Step
                for(solution in currentStep.solutions){
                    for(score in solution.scores){
                        if(score.fitness == indicator.fitness){
                            if(score.value!! > indicator.value!!){
                                indicator.value = score.value!!
                            }
                        }
                    }
                }
            }

            is org.kevoree.modeling.optimization.executionmodel.InvertedGenerationalDistance -> {
                var nbObjectives = 0
                var currentStep: Step = indicator.eContainer() as Step
                val input : Array<DoubleArray> = Array<DoubleArray>(currentStep.solutions.size(),{DoubleArray(0)})
                var i = 0
                for(solution in currentStep.solutions){
                    input[i] = DoubleArray(solution.scores.size)
                    nbObjectives = 0;
                    for(score in solution.scores){
                        input[i][nbObjectives] = score.value!!
                        nbObjectives++;
                    }
                    i++
                }
                indicator.value = org.kevoree.modeling.optimization.executionmodel.qualityIndicator.InvertedGenerationalDistance.getINSTANCE()!!.invertedGenerationalDistance(input,MetricsUtil.buildKMFGenTruePareto(nbObjectives),nbObjectives)
            }

            is org.kevoree.modeling.optimization.executionmodel.Hypervolume -> {
                var nbObjectives = 0
                var currentStep: Step = indicator.eContainer() as Step
                val input : Array<DoubleArray> = Array<DoubleArray>(currentStep.solutions.size(),{DoubleArray(0)})
                var i = 0
                for(solution in currentStep.solutions){
                    input[i] = DoubleArray(solution.scores.size)
                    nbObjectives = 0;
                    for(score in solution.scores){
                        input[i][nbObjectives] = score.value!!
                        nbObjectives++;
                    }
                    i++
                }
                indicator.value = org.kevoree.modeling.optimization.executionmodel.qualityIndicator.Hypervolume.getINSTANCE()!!.hypervolume(input,MetricsUtil.buildKMFGenTruePareto(nbObjectives),nbObjectives)
            }

            is org.kevoree.modeling.optimization.executionmodel.GenerationalDistance -> {
                var nbObjectives = 0
                var currentStep: Step = indicator.eContainer() as Step
                val input : Array<DoubleArray> = Array<DoubleArray>(currentStep.solutions.size(),{DoubleArray(0)})
                var i = 0
                for(solution in currentStep.solutions){
                    input[i] = DoubleArray(solution.scores.size)
                    nbObjectives = 0;
                    for(score in solution.scores){
                        input[i][nbObjectives] = score.value!!
                        nbObjectives++;
                    }
                    i++
                }
                indicator.value = org.kevoree.modeling.optimization.executionmodel.qualityIndicator.GenerationalDistance.getINSTANCE()!!.generationalDistance(input,MetricsUtil.buildKMFGenTruePareto(nbObjectives),nbObjectives)
            }

            is org.kevoree.modeling.optimization.executionmodel.GeneralizedSpread -> {
                var nbObjectives = 0
                var currentStep: Step = indicator.eContainer() as Step
                val input : Array<DoubleArray> = Array<DoubleArray>(currentStep.solutions.size(),{DoubleArray(0)})
                var i = 0
                for(solution in currentStep.solutions){
                    input[i] = DoubleArray(solution.scores.size)
                    nbObjectives = 0;
                    for(score in solution.scores){
                        input[i][nbObjectives] = score.value!!
                        nbObjectives++;
                    }
                    i++
                }
                indicator.value = org.kevoree.modeling.optimization.executionmodel.qualityIndicator.GeneralizedSpread.getINSTANCE()!!.generalizedSpread(input,MetricsUtil.buildKMFGenTruePareto(nbObjectives),nbObjectives)
            }

            is org.kevoree.modeling.optimization.executionmodel.Epsilon -> {
                var nbObjectives = 0
                var currentStep: Step = indicator.eContainer() as Step
                val input : Array<DoubleArray> = Array<DoubleArray>(currentStep.solutions.size(),{DoubleArray(0)})
                var i = 0
                for(solution in currentStep.solutions){
                    input[i] = DoubleArray(solution.scores.size)
                    nbObjectives = 0;
                    for(score in solution.scores){
                        input[i][nbObjectives] = score.value!!
                        nbObjectives++;
                    }
                    i++
                }
                indicator.value = org.kevoree.modeling.optimization.executionmodel.qualityIndicator.Epsilon.getINSTANCE()!!.epsilon(MetricsUtil.buildKMFGenTruePareto(nbObjectives),input,nbObjectives)
            }

            is org.kevoree.modeling.optimization.executionmodel.Best -> {
                if (indicator.value == null) {
                    indicator.value = 0.toDouble()
                }
                var min = 10000000.toDouble()
                var distance = 0.toDouble()
                var cur = 0.toDouble()
                var currentStep: Step = indicator.eContainer() as Step
                for (solution in currentStep.solutions) {
                    distance = 0.toDouble()
                    for (score in solution.scores) {
                        distance = distance + Math.pow(score.value!!, 2.0)
                        if (score.fitness == indicator.fitness) {
                            cur = score.value!!
                        }
                    }
                    if (distance < min) {
                        min = distance
                        indicator.value = cur
                    }
                }
            }
        }
    }

}