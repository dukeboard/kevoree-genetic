package org.kevoree.modeling.optimization.api.fitness;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.optimization.api.GenerationContext;

/**
 * Created by duke on 4/7/14.
 */
public abstract class FitnessFunction<A extends KMFContainer> {

    public abstract double evaluate(A model, GenerationContext<A> context);

    public abstract double max();

    public abstract double min();

    public FitnessOrientation orientation = FitnessOrientation.MINIMIZE;

}
