package org.kevoree.modeling.optimization.executionmodel;
import org.kevoree.modeling.api.aspect;
public aspect trait GeneratedAspect_Min : org.kevoree.modeling.optimization.executionmodel.Min {

    override fun update(){

        value = 10000000.toDouble()
        var currentStep: Step = eContainer() as Step

        for(solution in currentStep.solutions){
            for(score in solution.scores){
                if(score.fitness == fitness){
                    if(score.value!! < value!!){
                        value = score.value!!
                    }
                }
            }
        }
	}
}
