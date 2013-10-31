package org.kevoree.modeling.genetic.tinycloud.fitnesses;

import org.cloud.Cloud;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:14
 */
public class CloudConsumptionFitness implements FitnessFunction<Cloud> {

    private double maxNode = 10;

    @Override
    public double evaluate(Cloud model, GenerationContext<Cloud> cloudGenerationContext) {
        return model.getNodes().size();
    }

    @Override
    public double max() {
        return maxNode;
    }

    @Override
    public double min() {
        return 0;
    }

    @Override
    public FitnessOrientation orientation() {
        return FitnessOrientation.MINIMIZE;
    }

}
