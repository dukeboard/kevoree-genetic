package org.kevoree.modeling.optimization.engine.genetic.impl;

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.api.ModelCloner
import org.kevoree.modeling.api.events.ModelElementListener
import org.kevoree.modeling.api.events.ModelEvent
import org.kevoree.modeling.api.trace.TraceSequence
import org.moeaframework.core.Variable
import org.kevoree.modeling.api.compare.ModelCompare
import org.kevoree.modeling.api.trace.Event2Trace

public class ModelVariable(val origin: KMFContainer?, val model: KMFContainer?, val cloner: ModelCloner, val traceSequence: TraceSequence?, val modelCompare: ModelCompare, val event2Trace : Event2Trace) : Variable, ModelElementListener {

    {
        model?.addModelTreeListener(this)
    }

    public override fun copy(): Variable? {
        if(model != null){
            var clonedTraceSeq: TraceSequence? = null
            if(traceSequence != null){
                clonedTraceSeq = modelCompare.createSequence();
                clonedTraceSeq!!.populate(traceSequence.traces);
            }
            var clonedVar = ModelVariable(origin, cloner.cloneMutableOnly(model!!, false), cloner, clonedTraceSeq, modelCompare,event2Trace)
            return clonedVar;
        }
        return null;
    }

    override fun elementChanged(evt: ModelEvent) {
        traceSequence?.populate(event2Trace.convert(evt).traces);
    }

}

