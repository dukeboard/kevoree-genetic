package org.kevoree.modeling.optimization.executionmodel;

import org.kevoree.modeling.api.aspect
import org.kevoree.modeling.optimization.executionmodel.qualityIndicator.util.MetricsUtil

public aspect trait GeneratedAspect_Hypervolume : org.kevoree.modeling.optimization.executionmodel.Hypervolume {
    /*override fun update() {
        var nbObjectives = 0
        var currentStep: Step = eContainer() as Step
        val input : Array<DoubleArray> = Array<DoubleArray>(currentStep.solutions.size(),{DoubleArray(0)})
        //val inputNull : Array<DoubleArray> = Array<DoubleArray>(currentStep.solutions.size(),{DoubleArray(0)})
        var i = 0
        for(solution in currentStep.solutions){
            input[i] = DoubleArray(solution.scores.size)
            //inputNull[i] = DoubleArray(solution.scores.size)
            nbObjectives = 0;
            for(score in solution.scores){
                input[i][nbObjectives] = score.value!!
                //inputNull[i][nbObjectives] = 0.toDouble() //default reference point
                nbObjectives++;
            }
            i++
        }
        value = HyperVolumeIndicator.calculateHypervolume(input,currentStep.solutions.size(),nbObjectives)
    } */

    override fun update() {
        var nbObjectives = 0
        var currentStep: Step = eContainer() as Step
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
        value = org.kevoree.modeling.optimization.executionmodel.qualityIndicator.Hypervolume.getINSTANCE()!!.hypervolume(input,MetricsUtil.buildKMFGenTruePareto(nbObjectives),nbObjectives)
    }

}
