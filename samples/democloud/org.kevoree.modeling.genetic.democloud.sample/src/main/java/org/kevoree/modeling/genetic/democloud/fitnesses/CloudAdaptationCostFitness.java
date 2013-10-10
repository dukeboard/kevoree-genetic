package org.kevoree.modeling.genetic.democloud.fitnesses;

import org.cloud.Cloud;
import org.kevoree.modeling.api.trace.ModelAddTrace;
import org.kevoree.modeling.api.trace.ModelRemoveTrace;
import org.kevoree.modeling.api.trace.ModelTrace;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 05/09/13
 * Time: 17:52
 */

public class CloudAdaptationCostFitness implements FitnessFunction<Cloud> {



    @Override
    public double evaluate(Cloud model, GenerationContext<Cloud> cloudGenerationContext) {
        double result = 0;
        /*for (ModelTrace trace : traceSequence.getTraces()) {
            if (trace instanceof ModelAddTrace) {
                result = result +1;
            }
            if (trace instanceof ModelRemoveTrace) {
                result = result - 1;
            }
        }   */
        return result;
    }
}
