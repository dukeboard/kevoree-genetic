package org.kevoree.modeling.optimization.api.mutation

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.GenerationContext

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 13/10/13
 * Time: 21:56
 */

public trait MutationCrossover<A : KMFContainer> {

    public fun mutate(modelA: A, contextA: GenerationContext<A>,modelB: A, contextB: GenerationContext<A>) : A;

}