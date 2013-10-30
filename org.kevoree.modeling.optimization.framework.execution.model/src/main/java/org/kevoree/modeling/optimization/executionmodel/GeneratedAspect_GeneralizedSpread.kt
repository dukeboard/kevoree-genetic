package org.kevoree.modeling.optimization.executionmodel;
import org.kevoree.modeling.api.aspect;
import org.kevoree.modeling.optimization.executionmodel.qualityIndicator.util.MetricsUtil

public aspect trait GeneratedAspect_GeneralizedSpread : org.kevoree.modeling.optimization.executionmodel.GeneralizedSpread {
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
        value = org.kevoree.modeling.optimization.executionmodel.qualityIndicator.GeneralizedSpread.getINSTANCE()!!.generalizedSpread(input,MetricsUtil.buildKMFGenTruePareto(nbObjectives),nbObjectives)
    }
}
