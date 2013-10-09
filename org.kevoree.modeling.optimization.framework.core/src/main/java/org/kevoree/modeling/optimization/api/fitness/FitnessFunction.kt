package org.kevoree.modeling.optimization.api.fitness;

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.GenerationContext

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 14:40
 */
public trait FitnessFunction<A : KMFContainer> {

    public fun evaluate(model: A, context: GenerationContext<A>): Double ;

}
