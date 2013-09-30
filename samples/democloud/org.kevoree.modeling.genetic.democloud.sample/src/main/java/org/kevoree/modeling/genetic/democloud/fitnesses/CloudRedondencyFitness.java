package org.kevoree.modeling.genetic.tinycloud.fitnesses;

import org.cloud.Cloud;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.kevoree.modeling.optimization.api.FitnessFunction;

/**
 * Created by duke on 08/08/13.
 */
public class CloudRedondencyFitness implements FitnessFunction<Cloud> {

    private double bestRedondency = 3;

    @Override
    public boolean originAware(){
        return false;
    }

    @Override
    public double evaluate(Cloud model, Cloud origin, TraceSequence traceSequence) {
        double pres = (  (bestRedondency - model.getNodes().size()) / bestRedondency) * 100;
        return Math.min(Math.max(0,pres),100);
    }
}
