package org.kevoree.modeling.genetic.tinycloud.fitnesses;

import cloud.Cloud;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation;

/**
 * Created by duke on 08/08/13.
 */
public class CloudRedondencyFitness implements FitnessFunction<Cloud> {

    private double bestRedondency = 10;


    @Override
    public double evaluate(Cloud model, GenerationContext<Cloud> cloudGenerationContext) {
        return Math.min(bestRedondency, model.getNodes().size());
    }

    @Override
    public double max() {
        return bestRedondency;
    }

    @Override
    public double min() {
        return 0;
    }


    public FitnessOrientation orientation() {
        return FitnessOrientation.MAXIMIZE;
    }
}
