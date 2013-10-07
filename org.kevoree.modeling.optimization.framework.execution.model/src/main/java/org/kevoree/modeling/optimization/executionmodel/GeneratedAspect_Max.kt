package org.kevoree.modeling.optimization.executionmodel;

import org.kevoree.modeling.api.aspect

public aspect trait GeneratedAspect_Max : org.kevoree.modeling.optimization.executionmodel.Max {
    override fun update() {
        if(value == null){
            value = 0.toDouble()
        }
        var currentStep: Step = eContainer() as Step
        for(solution in currentStep.solutions){
            for(score in solution.scores){
                if(score.fitness == fitness){
                    if(score.value!! > value!!){
                        value = score.value!!
                    }
                }
            }
        }
    }
}
