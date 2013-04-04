package org.kevoree.genetic.framework.internal;

import org.kevoree.ContainerRoot;
import org.kevoree.cloner.ModelCloner;
import org.moeaframework.core.Variable;

/**
 * Created with IntelliJ IDEA.
 * User: jbourcie
 * Date: 12/02/13
 * Time: 13:51
 */
public class KevoreeVariable implements Variable {

    private ContainerRoot model;
    private ModelCloner modelCloner = new ModelCloner();

    private ContainerRoot origin = null;

    public ContainerRoot getOrigin() {
        return origin;
    }

    public KevoreeVariable setOrigin(ContainerRoot origin) {
        this.origin = origin;
        return this;
    }

    public KevoreeVariable(ContainerRoot _model) {
        this.model = _model;
    }

    @Override
    public Variable copy() {
        return new KevoreeVariable(modelCloner.cloneMutableOnly(model, true)).setOrigin(getOrigin());
    }

    public ContainerRoot getModel() {
        return model;
    }

    public void setModel(ContainerRoot model) {
        this.model = model;
        model.setRecursiveReadOnly();
    }
}
