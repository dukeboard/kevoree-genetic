package org.kevoreeoptimization;
import org.kevoree.modeling.api.aspect;
public aspect trait GeneratedAspect_Max : org.kevoreeoptimization.Max {
	override fun update(score:org.kevoreeoptimization.Score){
        if(value==null){
            value = 0.toDouble()
        }
        if(score.value != null && score.value!! > value!!){
             value = score.value
        }
	}
}
