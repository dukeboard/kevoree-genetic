package org.kevoree.genetic.framework;

import org.kevoree.modeling.api.KMFContainer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 14:40
 */
public interface KevoreeFitnessFunction<A extends KMFContainer> {

    public double evaluate(A model);

}
