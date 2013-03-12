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

    public KevoreeVariable(ContainerRoot _model) {
        this.model = _model;
    }

    @Override
    public Variable copy() {
        return new KevoreeVariable(modelCloner.cloneMutableOnly(model,true));
    }

    public ContainerRoot getModel() {
        return model;
    }

    public void setModel(ContainerRoot model) {
        this.model = model;
        model.setRecursiveReadOnly();
    }
}
