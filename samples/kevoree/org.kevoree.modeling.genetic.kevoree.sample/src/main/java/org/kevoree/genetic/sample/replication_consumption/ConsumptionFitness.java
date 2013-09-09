package org.kevoree.modeling.optimization.genetic.sample.replication_consumption;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.kevoree.modeling.optimization.api.FitnessFunction;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 08/03/13
 * Time: 18:22
 */
public class ConsumptionFitness implements FitnessFunction<ContainerRoot> {

    private static double costPerComponent = 0.5;

    @Override
    public boolean originAware(){
        return true;
    }

    @Override
    public double evaluate(ContainerRoot model, ContainerRoot origin, TraceSequence traceSequence) {
        double res = 0;
        for (ContainerNode n : model.getNodes()) {
            res = res + n.getComponents().size() * costPerComponent;
        }
        return res;
    }

}
