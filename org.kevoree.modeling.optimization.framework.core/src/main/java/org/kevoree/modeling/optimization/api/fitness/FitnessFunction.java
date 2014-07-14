package org.kevoree.modeling.optimization.api.fitness;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.optimization.api.GenerationContext;

/**
 * Created by duke on 4/7/14.
 */
public interface FitnessFunction<A extends KMFContainer> {

    public double evaluate(A model, GenerationContext<A> context);

}
