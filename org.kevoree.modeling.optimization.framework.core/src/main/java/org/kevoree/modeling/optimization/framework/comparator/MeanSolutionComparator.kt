package org.kevoree.modeling.optimization.framework.comparator

import org.kevoree.modeling.optimization.api.solution.SolutionComparator
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.solution.Solution

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 13/10/13
 * Time: 22:25
 */

public class MeanSolutionComparator<A : KMFContainer> : SolutionComparator<A> {

    override fun compare(sol: Solution<A>, sol2: Solution<A>): Boolean {
        return getMean(sol2) < getMean(sol)
    }

    private fun getMean(solution: Solution<A>): Double {
        var solCumul = 0.0
        var solNb = 0
        for(fit in solution.getFitnesses()){
            solNb++
            solCumul = solCumul + solution.getScoreForFitness(fit)!!
        }
        return solCumul / solNb
    }

}
