package org.kevoree.genetic.sample.replication_consumption;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.genetic.framework.KevoreeFitnessFunction;
import org.kevoree.modeling.api.KMFContainer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 08/03/13
 * Time: 18:22
 */
public class ConsumptionFitness implements KevoreeFitnessFunction {

    private static double costPerComponent = 0.5;

    @Override
    public double evaluate(KMFContainer model) {
        double res = 0;
        for (ContainerNode n : ((ContainerRoot)model).getNodes()) {
            res = res + n.getComponents().size() * costPerComponent;
        }
        return res;
    }

}
