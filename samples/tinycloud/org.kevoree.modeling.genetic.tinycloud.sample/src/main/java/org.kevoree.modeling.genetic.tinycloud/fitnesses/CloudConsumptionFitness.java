package org.kevoree.modeling.genetic.tinycloud.fitnesses;

import org.cloud.Cloud;
import org.kevoree.genetic.framework.KevoreeFitnessFunction;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:14
 */
public class CloudConsumptionFitness implements KevoreeFitnessFunction<Cloud> {

    private int maxNode = 5;

    @Override
    public double evaluate(Cloud model) {
            return (maxNode - model.getNodes().size())/maxNode * 100;
    }

}
