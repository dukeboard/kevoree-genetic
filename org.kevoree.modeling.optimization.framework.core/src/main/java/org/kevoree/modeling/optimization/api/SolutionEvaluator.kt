package org.kevoree.modeling.optimization.api

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 18/09/13
 * Time: 14:39
 */

public trait SolutionEvaluator {

    fun evaluate(solution : Solution) : Long

}