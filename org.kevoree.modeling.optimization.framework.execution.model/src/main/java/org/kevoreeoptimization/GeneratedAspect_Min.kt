package org.kevoreeoptimization;
import org.kevoree.modeling.api.aspect;
public aspect trait GeneratedAspect_Min : org.kevoreeoptimization.Min {
	override fun update(score:org.kevoreeoptimization.Score){
        if(value==null){
            value = 10000000.toDouble()
        }
        if(score.value != null && score.value!! < value!!){
            value = score.value
        }
    }
}
