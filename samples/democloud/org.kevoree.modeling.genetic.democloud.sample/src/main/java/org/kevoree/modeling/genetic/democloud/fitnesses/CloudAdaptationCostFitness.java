package org.kevoree.modeling.genetic.democloud.fitnesses;

import org.cloud.Cloud;
import org.kevoree.modeling.api.trace.ModelAddTrace;
import org.kevoree.modeling.api.trace.ModelRemoveTrace;
import org.kevoree.modeling.api.trace.ModelTrace;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation;

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
        for (ModelTrace trace : cloudGenerationContext.getTraceSequence().getTraces()) {


            if (trace instanceof ModelAddTrace) {
                result = result +1;
            }
            if (trace instanceof ModelRemoveTrace) {
                result = result - 1;
            }
        }
        return result;
    }
    @Override
    public double min() {

        return 0.0;
    }
    public double max() {

        return 5.0;
    }
    public FitnessOrientation orientation() {

        return FitnessOrientation.MINIMIZE;
    }
}
