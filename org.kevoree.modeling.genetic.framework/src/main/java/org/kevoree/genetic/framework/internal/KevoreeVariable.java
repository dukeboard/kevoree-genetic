package org.kevoree.genetic.framework.internal;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.api.ModelCloner;
import org.moeaframework.core.Variable;

/**
 * Created with IntelliJ IDEA.
 * User: jbourcie
 * Date: 12/02/13
 * Time: 13:51
 */
public class KevoreeVariable implements Variable {

    private KMFContainer model;

    private ModelCloner modelCloner = null;//new ModelCloner();

    public ModelCloner getModelCloner() {
        return modelCloner;
    }

    private KMFContainer origin = null;

    public KMFContainer getOrigin() {
        return origin;
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
        return new KevoreeVariable(modelCloner.cloneMutableOnly(model, true), modelCloner).setOrigin(getOrigin());
    }

    public KMFContainer getModel() {
        return model;
    }

    public void setModel(KMFContainer model) {
        this.model = model;
        model.setRecursiveReadOnly();
    }
}
