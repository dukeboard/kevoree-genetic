package org.kevoree.genetic.framework;

import org.kevoree.ContainerRoot;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 27/03/13
 * Time: 14:42
 */
public abstract class KevoreeOriginAwareFitnessFunction implements KevoreeFitnessFunction {

    @Override
    public double evaluate(ContainerRoot model) {
        return 0;
    }

    public abstract double evaluate(ContainerRoot model,ContainerRoot origin);

}
