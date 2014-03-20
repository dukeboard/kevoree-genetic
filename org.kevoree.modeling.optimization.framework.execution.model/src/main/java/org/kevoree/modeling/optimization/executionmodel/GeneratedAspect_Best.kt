package org.kevoree.modeling.optimization.executionmodel;
import org.kevoree.modeling.api.aspect;
public aspect trait GeneratedAspect_Best : org.kevoree.modeling.optimization.executionmodel.Best {
	override fun update(){
        if(value == null){
            value = 0.toDouble()
        }

        var min= 10000000.toDouble()
        var distance=0.toDouble()
        var cur=0.toDouble()

        var currentStep: Step = eContainer() as Step
        for(solution in currentStep.solutions){
            distance=0.toDouble()
            for(score in solution.scores){
                distance = distance + Math.pow(score.value!!,2.0)
                if(score.fitness == fitness){
                    cur = score.value!!
                }
            }
            if(distance<min){
                min=distance
                value=cur
            }
        }
    }
}
