package org.kevoree.modeling.optimization.executionmodel;

import org.kevoree.modeling.api.aspect

public aspect trait GeneratedAspect_ParetoMean : org.kevoree.modeling.optimization.executionmodel.ParetoMean {
    override fun update() {
        if(value == null){
            value = 10000000.toDouble()
        }
        var currentStep: Step = eContainer() as Step
        var nb = 0
        var sum = 0.0
        for(solution in currentStep.solutions){
            for(score in solution.scores){
                nb = nb + 1
                sum = sum + score.value!!
            }
        }
        value = sum / nb
    }
}
