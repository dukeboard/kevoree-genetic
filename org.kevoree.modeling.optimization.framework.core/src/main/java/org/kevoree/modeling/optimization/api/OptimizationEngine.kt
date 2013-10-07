package org.kevoree.modeling.optimization.api;

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 13/08/13
 * Time: 17:12
 */
public trait OptimizationEngine<A: KMFContainer> {

    public fun addOperator(operator: MutationOperator<A>): OptimizationEngine<A>

    public fun addFitnessFuntion(function: FitnessFunction<A>): OptimizationEngine<A>

    public fun setPopulationFactory(populationFactory: PopulationFactory<A>): OptimizationEngine<A>

    public fun setMaxGeneration(maxGeneration: Int): OptimizationEngine<A>

    public fun setMaxTime(maxTime: Long): OptimizationEngine<A>

    public fun solve(): List<Solution>

    public fun getExecutionModel() : ExecutionModel?

    public fun activateExecutionModel()

    public fun addFitnessMetric(fitness : FitnessFunction<A>, metric : ParetoFitnessMetrics)

    public fun addParetoMetric(metric : ParetoMetrics)

}
