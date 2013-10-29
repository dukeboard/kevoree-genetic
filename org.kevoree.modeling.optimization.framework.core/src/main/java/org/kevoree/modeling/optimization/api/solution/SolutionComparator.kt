package org.kevoree.modeling.optimization.api

import org.kevoree.modeling.api.KMFContainer

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 09/10/13
 * Time: 17:56
 */

public trait SolutionComparator<A : KMFContainer> {

    /* return true is sol2 is greater than sol */
    fun compare(sol: Solution<A>, sol2: Solution<A>): Boolean

}