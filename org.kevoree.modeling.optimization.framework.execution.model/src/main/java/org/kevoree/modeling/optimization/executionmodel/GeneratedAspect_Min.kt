package org.kevoree.modeling.optimization.executionmodel;
import org.kevoree.modeling.api.aspect;
public aspect trait GeneratedAspect_Min : org.kevoree.modeling.optimization.executionmodel.Min {
	override fun update(score:Double){
        if(value==null){
            value = 10000000.toDouble()
        }
        if(score < value!!){
            value = score
        }
	}
}
