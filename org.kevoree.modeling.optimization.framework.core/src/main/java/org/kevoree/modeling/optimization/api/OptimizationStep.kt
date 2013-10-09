package org.kevoree.modeling.optimization.api

import org.kevoree.modeling.api.KMFContainer

/**
 * Created by duke on 14/08/13.
 */

public trait OptimizationStep<A : KMFContainer> {

    fun getSolutions(): List<Solution<A>> ;

    fun populateInitalSolutions(solutions: List<Solution<A>>);

    fun run();

    fun selectBestSolution(): OptimizationStep<A> ;

    fun selectBestSolutions(paretoSize: Int): OptimizationStep<A> ;

    fun selectAll(): OptimizationStep<A>;

    fun then(nextStep: OptimizationStep<A>);

}