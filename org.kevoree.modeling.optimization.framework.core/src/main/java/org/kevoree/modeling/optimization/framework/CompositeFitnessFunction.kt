package org.kevoree.modeling.optimization.framework;

import org.kevoree.modeling.api.KMFContainer
import java.util.ArrayList
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.api.GenerationContext

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 15/03/13
 * Time: 16:20
 */
public class CompositeFitnessFunction<A : KMFContainer> : FitnessFunction<A> {

    override fun evaluate(model: A, context: GenerationContext<A>): Double {
        var value = 0.0;
        for (fit in fitnesses) {
            value = value + fit.evaluate(model, context);
        }
        return value / fitnesses.size();
    }

    val fitnesses = ArrayList<FitnessFunction<A>>();

    public fun addFitness(newfit: FitnessFunction<A>): CompositeFitnessFunction<A> {
        fitnesses.add(newfit);
        return this;
    }

    public fun getFitnesses(): List<FitnessFunction<A>> {
        return fitnesses;
    }

}
