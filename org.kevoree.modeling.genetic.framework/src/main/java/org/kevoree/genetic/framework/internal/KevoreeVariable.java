package org.kevoree.genetic.framework.internal;

import jet.runtime.typeinfo.JetValueParameter;
import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.api.events.ModelElementListener;
import org.kevoree.modeling.api.events.ModelEvent;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.moeaframework.core.Variable;

/**
 * Created with IntelliJ IDEA.
 * User: jbourcie
 * Date: 12/02/13
 * Time: 13:51
 */
public class KevoreeVariable implements Variable, ModelElementListener {

    private KMFContainer model;

    private ModelCloner modelCloner = null;

    public ModelCloner getModelCloner() {
        return modelCloner;
    }

    private KMFContainer origin = null;

    public KMFContainer getOrigin() {
        return origin;
    }

    private TraceSequence traceSequence = null;

    public TraceSequence getTraceSequence() {
        return traceSequence;
    }

    public void setTraceSequence(TraceSequence traceSequence) {
        this.traceSequence = traceSequence;
    }

    public KevoreeVariable setOrigin(KMFContainer origin) {
        this.origin = origin;
        return this;
    }

    public KevoreeVariable(KMFContainer _model, ModelCloner cloner) {
        this.model = _model;
        this.modelCloner = cloner;
    }

    @Override
    public Variable copy() {
        KevoreeVariable clonedVar = new KevoreeVariable(modelCloner.cloneMutableOnly(model, false), modelCloner);
        clonedVar.setOrigin(getOrigin());
        //TODO Trace sequence clone
        clonedVar.getModel().addModelTreeListener(clonedVar);
        return clonedVar;
    }

    public KMFContainer getModel() {
        return model;
    }

    public void setModel(KMFContainer model) {
        this.model = model;
        model.setRecursiveReadOnly();
    }

    @Override
    public void elementChanged(ModelEvent modelEvent) {
        //TODO
    }
}
