package org.kevoree.modeling.optimization.engine.genetic.impl;

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.api.ModelCloner
import org.kevoree.modeling.api.events.ModelElementListener
import org.kevoree.modeling.api.events.ModelEvent
import org.moeaframework.core.Variable
import org.kevoree.modeling.api.compare.ModelCompare
import org.kevoree.modeling.api.trace.Event2Trace
import org.kevoree.modeling.optimization.api.GenerationContext

public class ModelVariable<A : KMFContainer>(val model: A, val context: GenerationContext<A>, val cloner: ModelCloner, val modelCompare: ModelCompare, val event2Trace: Event2Trace, val traceAware : Boolean) : Variable, ModelElementListener {

    {
        model.addModelTreeListener(this)
    }

    public override fun copy(): Variable? {
        var clonedModel = cloner.cloneMutableOnly(model, false)!!
        var newContext = context.createChild(modelCompare, clonedModel,traceAware)
        var clonedVar = ModelVariable(clonedModel, newContext, cloner, modelCompare, event2Trace,traceAware)
        return clonedVar;
    }

    override fun elementChanged(evt: ModelEvent) {
        if(context.traceSequence!=null){
            context.traceSequence.populate(event2Trace.convert(evt).traces);
        }

    }

}

