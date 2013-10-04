package org.kevoree.modeling.optimization.executionmodel;

import org.kevoree.modeling.api.aspect

public aspect trait GeneratedAspect_Mean : org.kevoree.modeling.optimization.executionmodel.Mean {
    override fun update() {
        if(value == null){
            value = 10000000.toDouble()
        }
        var currentStep: Step = eContainer() as Step
        var nb = 0
        var sum = 0.0
        for(solution in currentStep.solutions){
            var currentSolutionScore = solution.findScoresByID(fitness!!.name!!)!!.value!!
            nb = nb + 1
            sum = sum + currentSolutionScore
        }
        value = sum / nb
    }
}
