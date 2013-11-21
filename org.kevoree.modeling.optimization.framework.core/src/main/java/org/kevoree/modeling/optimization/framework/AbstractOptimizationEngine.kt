package org.kevoree.modeling.optimization.framework

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.OptimizationEngine
import org.kevoree.modeling.optimization.executionmodel.impl.DefaultExecutionModelFactory
import org.kevoree.modeling.optimization.api.metric.ParetoMetrics
import org.kevoree.modeling.optimization.api.metric.ParetoFitnessMetrics
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.api.mutation.MutationOperatorSelector
import org.kevoree.modeling.optimization.api.mutation.MutationSelectionStrategy
import org.kevoree.modeling.optimization.api.solution.SolutionMutationListener
import org.kevoree.modeling.optimization.framework.selector.DefaultRandomOperatorSelector
import org.kevoree.modeling.optimization.engine.genetic.selector.SputnikElitistMutationOperatorSelector
import org.kevoree.modeling.optimization.engine.genetic.selector.SputnikCasteMutationOperatorSelector

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 03/10/13
 * Time: 14:29
 */

public trait AbstractOptimizationEngine<A : KMFContainer> : OptimizationEngine<A> {

    var _operators: MutableList<MutationOperator<A>>
    var _fitnesses: MutableList<FitnessFunction<A>>
    var _executionModelFactory: DefaultExecutionModelFactory?
    var mutationSelector: MutationOperatorSelector<A>

    public override var mutationSelectionStrategy: MutationSelectionStrategy
        set(strategy){

            when(strategy) {
                MutationSelectionStrategy.RANDOM -> {
                    mutationSelector = DefaultRandomOperatorSelector(_operators)
                }
                MutationSelectionStrategy.SPUTNIK_CASTE -> {
                    if(mutationSelector is SolutionMutationListener<*>){
                        solutionMutationListeners.remove(mutationSelector)
                    }
                    mutationSelector = SputnikCasteMutationOperatorSelector(_operators, 90.0)
                    solutionMutationListeners.add(mutationSelector as SputnikElitistMutationOperatorSelector<A>)
                }
                MutationSelectionStrategy.SPUTNIK_ELITIST -> {
                    if(mutationSelector is SolutionMutationListener<*>){
                        solutionMutationListeners.remove(mutationSelector)
                    }
                    mutationSelector = SputnikElitistMutationOperatorSelector(_operators, 90.0)
                    solutionMutationListeners.add(mutationSelector as SputnikElitistMutationOperatorSelector<A>)
                }
            }
        }
        get(){
            when(mutationSelector) {
                is SputnikElitistMutationOperatorSelector -> {
                    return MutationSelectionStrategy.SPUTNIK_ELITIST
                }
                is SputnikCasteMutationOperatorSelector -> {
                    return MutationSelectionStrategy.SPUTNIK_CASTE
                }
                is DefaultRandomOperatorSelector -> {
                    return MutationSelectionStrategy.RANDOM
                }
            }
        }

    public override fun addOperator(operator: MutationOperator<A>): OptimizationEngine<A> {
        _operators.add(operator);
        return this;
    }
    public override fun addFitnessFuntion(function: FitnessFunction<A>): OptimizationEngine<A> {
        _fitnesses.add(function);
        return this;
    }

    private fun activateExecutionModel() {
        if(executionModel == null){
            _executionModelFactory = DefaultExecutionModelFactory()
            executionModel = _executionModelFactory!!.createExecutionModel()
        }
    }

    var _metricsName: MutableList<FitnessMetric>

    public override fun addFitnessMetric(fitness: FitnessFunction<A>, metric: ParetoFitnessMetrics) {
        activateExecutionModel() //if at least one metric is added, initiate the ExecutionModel
        when(metric) {
            ParetoFitnessMetrics.MAX -> {
                _metricsName.add(FitnessMetric(fitness.javaClass.getSimpleName(), "org.kevoree.modeling.optimization.executionmodel.Max"));
            }
            ParetoFitnessMetrics.MIN -> {
                _metricsName.add(FitnessMetric(fitness.javaClass.getSimpleName(), "org.kevoree.modeling.optimization.executionmodel.Min"));
            }
            ParetoFitnessMetrics.MEAN -> {
                _metricsName.add(FitnessMetric(fitness.javaClass.getSimpleName(), "org.kevoree.modeling.optimization.executionmodel.Mean"));
            }
            else -> {
            }
        }
    }

    public override fun addParetoMetric(metric: ParetoMetrics) {
        activateExecutionModel() //if at least one metric is added, initiate the ExecutionModel
        when(metric) {
            ParetoMetrics.HYPERVOLUME -> {
                _metricsName.add(FitnessMetric(null, "org.kevoree.modeling.optimization.executionmodel.Hypervolume"));
            }
            ParetoMetrics.MEAN -> {
                _metricsName.add(FitnessMetric(null, "org.kevoree.modeling.optimization.executionmodel.ParetoMean"));
            }
            ParetoMetrics.MIN_MEAN -> {
                _metricsName.add(FitnessMetric(null, "org.kevoree.modeling.optimization.executionmodel.MinMean"));
            }
            ParetoMetrics.MAX_MEAN -> {
                _metricsName.add(FitnessMetric(null, "org.kevoree.modeling.optimization.executionmodel.MaxMean"));
            }
            else -> {
            }
        }
    }

}