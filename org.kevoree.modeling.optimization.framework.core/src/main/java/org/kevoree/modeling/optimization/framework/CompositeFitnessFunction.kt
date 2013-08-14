package org.kevoree.modeling.optimization.framework;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.optimization.api.FitnessFunction;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 15/03/13
 * Time: 16:20
 */
public class CompositeFitnessFunction<A: KMFContainer>: FitnessFunction<A> {

    val fitnesses = ArrayList<FitnessFunction<A>>();

    public override fun evaluate(model: A): Double {
        var value = 0.0;
        for (fit in fitnesses) {
            value = value + fit.evaluate(model);
        }
        return value / fitnesses.size();
    }

    public fun addFitness(newfit: FitnessFunction<A>): CompositeFitnessFunction<A> {
        fitnesses.add(newfit);
        return this;
    }

    public fun getFitnesses(): List<FitnessFunction<A>> {
        return fitnesses;
    }

}
