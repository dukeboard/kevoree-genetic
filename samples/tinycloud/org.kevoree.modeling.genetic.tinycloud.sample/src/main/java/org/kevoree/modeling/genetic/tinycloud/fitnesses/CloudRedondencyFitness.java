package org.kevoree.modeling.genetic.tinycloud.fitnesses;

import org.cloud.Cloud;
import org.kevoree.modeling.optimization.api.FitnessFunction;

/**
 * Created by duke on 08/08/13.
 */
public class CloudRedondencyFitness implements FitnessFunction<Cloud> {

    private double bestRedondency = 3;

    @Override
    public double evaluate(Cloud model) {
        double pres = (  (bestRedondency - model.getNodes().size()) / bestRedondency) * 100;
        return pres;
    }
}
