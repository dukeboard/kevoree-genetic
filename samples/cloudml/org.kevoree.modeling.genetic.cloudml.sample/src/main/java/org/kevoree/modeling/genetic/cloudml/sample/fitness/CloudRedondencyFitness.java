package org.kevoree.modeling.genetic.cloudml.sample.fitness;

import org.cloudml.core.DeploymentModel;
import org.kevoree.modeling.genetic.api.FitnessFunction;

/**
 * Created by duke on 08/08/13.
 */
public class CloudRedondencyFitness implements FitnessFunction<DeploymentModel> {

    private double bestRedondency = 3;

    @Override
    public double evaluate(DeploymentModel model) {
        double pres = (  (bestRedondency - model.getNodeInstances().size()) / bestRedondency) * 100;
        return Math.min(Math.max(0,pres),100);
    }
}
