package org.kevoree.genetic.framework;

import org.kevoree.ContainerRoot;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 14:40
 */
public interface KevoreeFitnessFunction {

    public double evaluate(ContainerRoot model);

}
