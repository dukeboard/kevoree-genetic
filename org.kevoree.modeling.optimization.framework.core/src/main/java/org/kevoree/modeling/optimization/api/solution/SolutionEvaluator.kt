package org.kevoree.modeling.optimization.api.solution

import org.kevoree.modeling.api.KMFContainer

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 18/09/13
 * Time: 14:39
 */

public trait SolutionEvaluator<A : KMFContainer> {

    fun evaluate(solution : Solution<A>) : Long

}