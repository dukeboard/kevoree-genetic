package org.kevoree.modeling.genetic.api;

import org.kevoree.modeling.api.KMFContainer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 14:40
 */
public interface FitnessFunction<A extends KMFContainer> {

    public double evaluate(A model);

}
