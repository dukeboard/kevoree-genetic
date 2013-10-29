package org.kevoree.modeling.optimization.executionmodel;
import org.kevoree.modeling.api.aspect;
public aspect trait GeneratedAspect_MinMean : org.kevoree.modeling.optimization.executionmodel.MinMean {
    override fun update() {
        if(value == null){
            value = 10000000.toDouble()
        }
        var currentStep: Step = eContainer() as Step
        for(solution in currentStep.solutions){
            var nb = 0
            var sum = 0.0
            for(score in solution.scores){
                nb = nb + 1
                sum = sum + score.value!!
            }
            val currentMean = sum / nb
            if(currentMean < value!!){
                value = currentMean
            }
        }
    }

}
