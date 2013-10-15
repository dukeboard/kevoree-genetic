package org.kevoree.modeling.optimization.engine.fullsearch

import java.util.HashMap
import org.kevoree.modeling.optimization.api.Solution
import org.kevoree.modeling.api.KMFContainer

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 13/10/13
 * Time: 22:11
 */

public class IndexSolutionHandler<A : KMFContainer> {

    private val solutions = HashMap<String, List<Solution<A>>>()

    public fun addSolution(solution: Solution<A>): Boolean {



        return false
    }



}
