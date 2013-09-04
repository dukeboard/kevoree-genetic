package org.kevoree.modeling.optimization.framework

import org.kevoree.modeling.optimization.api.OptimizationStep
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.Solution
import org.kevoree.modeling.optimization.api.OptimizationEngine
import java.util.ArrayList

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 14/08/13
 * Time: 16:26
 */

public class AbstractOptimizationStep<A : KMFContainer> : OptimizationStep<A> {

    private var engine: OptimizationEngine<A>? = null;

    private var solutions: List<Solution>? = null;

    override fun getSolutions(): List<Solution> {
        if(solutions != null){
            return solutions!!
        }                   else {
            return ArrayList<Solution>()
        }
    }
    override fun populateInitalSolutions(solutions: List<Solution>) {
        throw UnsupportedOperationException()
    }
    override fun run() {
        throw UnsupportedOperationException()
    }
    override fun selectBestSolution(): OptimizationStep<A> {
        throw UnsupportedOperationException()
    }
    override fun selectBestSolutions(paretoSize: Int): OptimizationStep<A> {
        throw UnsupportedOperationException()
    }
    override fun selectAll(): OptimizationStep<A> {
        throw UnsupportedOperationException()
    }
    override fun then(nextStep: OptimizationStep<A>) {
        throw UnsupportedOperationException()
    }


}