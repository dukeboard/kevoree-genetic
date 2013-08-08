package org.kevoree.modeling.genetic.cloudml.sample.fitness;

import org.cloudml.core.DeploymentModel;
import org.kevoree.modeling.genetic.api.FitnessFunction;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:14
 */
public class CloudConsumptionFitness implements FitnessFunction<DeploymentModel> {

    private double maxNode = 5;

    @Override
    public double evaluate(DeploymentModel model) {
            double pres = (model.getNodeInstances().size() / maxNode) * 100;
            return pres;
    }

}
