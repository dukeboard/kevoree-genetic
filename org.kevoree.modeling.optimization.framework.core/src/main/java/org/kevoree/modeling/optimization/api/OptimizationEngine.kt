package org.kevoree.modeling.optimization.api;

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.solution.SolutionMutationListener
import org.kevoree.modeling.optimization.api.metric.ParetoFitnessMetrics
import org.kevoree.modeling.optimization.api.metric.ParetoMetrics
import org.kevoree.modeling.optimization.api.mutation.MutationSelectionStrategy
import org.kevoree.modeling.optimization.api.solution.SolutionComparator
import org.kevoree.modeling.optimization.api.solution.Solution

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 13/08/13
 * Time: 17:12
 */
public trait OptimizationEngine<A : KMFContainer> {

    public var maxTime: Long
    public var maxGeneration: Int
    public var populationFactory: PopulationFactory<A>?
    public var executionModel: ExecutionModel?
    public var mutationSelectionStrategy: MutationSelectionStrategy
    public var solutionComparator : SolutionComparator<A>
    public var solutionMutationListeners: MutableList<SolutionMutationListener<A>>

    /* Execution Metric configuration */
    public fun addFitnessMetric(fitness: FitnessFunction<A>, metric: ParetoFitnessMetrics)
    public fun addParetoMetric(metric: ParetoMetrics)

    /* Optimization configuration */
    public fun addOperator(operator: MutationOperator<A>): OptimizationEngine<A>
    public fun addFitnessFuntion(function: FitnessFunction<A>): OptimizationEngine<A>
    /* Optimization control methods */
    public fun solve(): List<Solution<A>>

    /* Internal use only, memory optimization */
    public fun desactivateOriginAware()

}