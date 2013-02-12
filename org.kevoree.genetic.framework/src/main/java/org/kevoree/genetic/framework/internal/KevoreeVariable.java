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

    public KevoreeVariable(ContainerRoot _model) {
        this.model = _model;
    }

    @Override
    public Variable copy() {
        // make use of the modelCloner
        ModelCloner modelCloner = new ModelCloner();
        return new KevoreeVariable(modelCloner.clone(model));
    }

    public ContainerRoot getModel() {
        return model;
    }

    public void setModel(ContainerRoot model) {
        this.model = model;
    }
}
