package org.kevoree.modeling.genetic.tinycloud.fitnesses;

import org.cloud.Cloud;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:14
 */
public class CloudConsumptionFitness implements FitnessFunction<Cloud> {

    private double maxNode = 5;


    @Override
    public double evaluate(Cloud model, GenerationContext<Cloud> cloudGenerationContext) {
        double pres = (model.getNodes().size() / maxNode) * 100;
        return pres;
    }

}
