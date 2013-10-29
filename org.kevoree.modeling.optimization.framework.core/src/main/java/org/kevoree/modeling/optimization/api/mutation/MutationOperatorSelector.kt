package org.kevoree.modeling.optimization.api.mutation

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.Solution

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 28/10/2013
 * Time: 17:24
 */

public trait MutationOperatorSelector<A : KMFContainer> {

    val operators : List<MutationOperator<A>>

    fun select(solution : Solution<A>) : MutationOperator<A>

}