package org.kevoree.modeling.genetic.tinycloud.fitnesses;

import org.cloud.Cloud;
import org.kevoree.modeling.api.trace.ModelAddTrace;
import org.kevoree.modeling.api.trace.ModelRemoveTrace;
import org.kevoree.modeling.api.trace.ModelTrace;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.kevoree.modeling.optimization.api.FitnessFunctionOriginAware;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 05/09/13
 * Time: 17:52
 */

public class CloudAdaptationCostFitness implements FitnessFunctionOriginAware<Cloud> {

    @Override
    public boolean originAware(){
        return true;
    }


    @Override
    public double evaluate(Cloud cloud, Cloud origin, TraceSequence traceSequence) {
        double result = 0;
        for (ModelTrace trace : traceSequence.getTraces()) {
            if (trace instanceof ModelAddTrace) {
                result = result +1;
            }
            if (trace instanceof ModelRemoveTrace) {
                result = result - 1;
            }
        }
        return result;
    }
}
