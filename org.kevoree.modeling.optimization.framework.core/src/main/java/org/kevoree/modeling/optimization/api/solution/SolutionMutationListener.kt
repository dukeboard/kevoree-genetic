package org.kevoree.modeling.optimization

import org.kevoree.modeling.optimization.api.Solution
import org.kevoree.modeling.api.KMFContainer

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 28/10/2013
 * Time: 18:24
 */

public trait SolutionMutationListener<A : KMFContainer> {

    fun process(previousSolution: Solution<A>,solution: Solution<A>)

}
