package org.kevoree.modeling.optimization.executionmodel;
import org.kevoree.modeling.api.aspect;
public aspect trait GeneratedAspect_Min : org.kevoree.modeling.optimization.executionmodel.Min {
	override fun update(){
        if(value == null){
            value = 10000000.toDouble()
        }
        var currentStep: Step = eContainer() as Step
        for(solution in currentStep.solutions){
            var currentSolutionScore = solution.findScoresByID(fitness!!.name!!)!!.value!!
            if(currentSolutionScore < value!!){
                value = currentSolutionScore
            }
        }
	}
}
