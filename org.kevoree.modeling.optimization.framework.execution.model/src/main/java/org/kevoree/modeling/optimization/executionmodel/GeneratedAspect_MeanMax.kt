package org.kevoree.modeling.optimization.executionmodel;

import org.kevoree.modeling.api.aspect

public aspect trait GeneratedAspect_MeanMax : org.kevoree.modeling.optimization.executionmodel.Mean {
    override fun update() {
        if(value == null){
            value = 10000000.toDouble()
        }
        var currentStep: Step = eContainer() as Step
        var nb = 0
        var sum = 0.0

        for(solution in currentStep.solutions){
            for(score in solution.scores){
               if(score.fitness == fitness){
                    if(score.value!! > value!!){
                        value = score.value!!
                    }
                }




            }
        }

        value = sum / nb
    }
}
