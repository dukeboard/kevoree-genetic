package org.kevoree.modeling.optimization.engine.fullsearch

import java.util.HashMap
import org.kevoree.modeling.optimization.api.solution.Solution
import org.kevoree.modeling.api.KMFContainer
import java.util.ArrayList
import org.kevoree.modeling.api.compare.ModelCompare

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 13/10/13
 * Time: 22:11
 */

public class IndexSolutionHandler<A : KMFContainer> {

    private val solutions = HashMap<String, MutableList<Solution<A>>>()

    public var modelCompare: ModelCompare? = null

    public fun clear() {
        solutions.clear()
        nbSolutions = 0
    }

    private var nbSolutions = 0
    public fun getNumberOfSolution(): Int {
        return nbSolutions;
    }

    public fun addSolution(solution: Solution<A>): Boolean {
        val hash = computeSolutionResultHash(solution)
        if(!solutions.containsKey(hash)){
            var hashedSolutions = ArrayList<Solution<A>>()
            hashedSolutions.add(solution)
            solutions.put(hash, hashedSolutions)
            nbSolutions++
        } else {
            val allPreviousSolutions = solutions.get(hash)!!
            for(previousSolution in allPreviousSolutions){
                if(previousSolution.model.modelEquals(solution.model)){
                    return false
                }
            }
            //no match found
            allPreviousSolutions.add(solution)
            nbSolutions++
        }
        return true
    }

    public fun computeSolutionResultHash(solution: Solution<A>): String {
        var hash = StringBuffer();
        var orderedFitness = solution.getFitnesses().toSortedList()
        for(fit in orderedFitness){
            var value = solution.getScoreForFitness(fit)
            hash.append(value)
        }
        return hash.toString()
    }

    public fun getAllSolutions(): List<Solution<A>> {
        var solutionsRes = ArrayList<Solution<A>>()
        for(sols in solutions.values()){
            solutionsRes.addAll(sols)
        }
        return solutionsRes
    }

}
