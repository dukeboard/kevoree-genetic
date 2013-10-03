package org.kevoree.modeling.optimization.executionmodel;
import org.kevoree.modeling.api.aspect;
public aspect trait GeneratedAspect_Max : org.kevoree.modeling.optimization.executionmodel.Max {
	override fun update(score:Double){
        if(value==null){
            value = 0.toDouble()
        }
        if(score > value!!){
            value = score
        }
	}
}
