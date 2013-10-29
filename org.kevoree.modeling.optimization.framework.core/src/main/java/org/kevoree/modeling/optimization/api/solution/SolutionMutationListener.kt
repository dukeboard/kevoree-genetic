package org.kevoree.modeling.optimization.solution

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.solution.Solution

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 28/10/2013
 * Time: 18:24
 */

public trait SolutionMutationListener<A : KMFContainer> {

    fun process(previousSolution: Solution<A>,solution: Solution<A>)

}
