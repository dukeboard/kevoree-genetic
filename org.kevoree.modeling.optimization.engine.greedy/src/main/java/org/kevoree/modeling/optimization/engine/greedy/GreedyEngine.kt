package org.kevoree.modeling.optimization.engine.greedy;

import org.kevoree.modeling.optimization.api.OptimizationEngine
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.MutationOperator
import org.kevoree.modeling.optimization.api.FitnessFunction
import org.kevoree.modeling.optimization.api.PopulationFactory
import org.kevoree.modeling.optimization.api.Solution

/**
 * Created by duke on 14/08/13.
 */
public class GreedyEngine<A: KMFContainer>: OptimizationEngine<A> {

    public override fun addOperator(operator: MutationOperator<A>): OptimizationEngine<A> {
        throw UnsupportedOperationException()
    }
    public override fun addFitnessFuntion(function: FitnessFunction<A>): OptimizationEngine<A> {
        throw UnsupportedOperationException()
    }
    public override fun setPopulationFactory(populationFactory: PopulationFactory<A>): OptimizationEngine<A> {
        throw UnsupportedOperationException()
    }
    public override fun setMaxGeneration(maxGeneration: Int): OptimizationEngine<A> {
        throw UnsupportedOperationException()
    }
    public override fun setMaxTime(maxTime: Long): OptimizationEngine<A> {
        throw UnsupportedOperationException()
    }
    public override fun solve(): List<Solution> {
        throw UnsupportedOperationException()
    }


}
