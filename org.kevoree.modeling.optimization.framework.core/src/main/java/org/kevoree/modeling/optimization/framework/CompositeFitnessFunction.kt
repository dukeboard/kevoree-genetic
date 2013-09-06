package org.kevoree.modeling.optimization.framework;

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.FitnessFunction
import java.util.ArrayList
import org.kevoree.modeling.api.trace.TraceSequence

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 15/03/13
 * Time: 16:20
 */
public class CompositeFitnessFunction<A : KMFContainer> : FitnessFunction<A> {
    public override fun originAware(): Boolean {
        for(fitness in fitnesses){
            if(fitness.originAware()){
                return true ;
            }
        }
        return false;
    }

    public override fun evaluate(model: A, origin: A?, traceSeq: TraceSequence?): Double {
        var value = 0.0;
        for (fit in fitnesses) {
            value = value + fit.evaluate(model, origin, traceSeq);
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
