package org.kevoree.modeling.optimization.framework

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.OptimizationEngine
import org.kevoree.modeling.optimization.api.PopulationFactory
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel
import org.kevoree.modeling.optimization.executionmodel.impl.DefaultExecutionModelFactory
import org.kevoree.modeling.optimization.api.ParetoMetrics
import org.kevoree.modeling.optimization.api.ParetoFitnessMetrics
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.SolutionMutationListener
import org.kevoree.modeling.optimization.api.mutation.MutationOperatorSelector
import org.kevoree.modeling.optimization.api.MutationSelectionStrategy
import org.kevoree.modeling.optimization.engine.genetic.DarwinMutationOperatorSelector

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 03/10/13
 * Time: 14:29
 */

public trait AbstractOptimizationEngine<A : KMFContainer> : OptimizationEngine<A> {

    var _operators: MutableList<MutationOperator<A>>
    var _fitnesses: MutableList<FitnessFunction<A>>
    var _populationFactory: PopulationFactory<A>?
    var _maxGeneration: Int
    var _maxTime: Long
    var _executionModel: ExecutionModel?
    var _executionModelFactory: DefaultExecutionModelFactory?
    var solutionMutationListeners: MutableList<SolutionMutationListener<A>>
    var mutationSelector: MutationOperatorSelector<A>

    override fun setMutationSelectionStrategy(strategy: MutationSelectionStrategy) {
        if(strategy == MutationSelectionStrategy.RANDOM){
            mutationSelector = DefaultRandomOperatorSelect(_operators)
        }
        if(strategy == MutationSelectionStrategy.DARWIN){
            //unregister previous
            if(mutationSelector is SolutionMutationListener<*>){
                solutionMutationListeners.remove(mutationSelector)
            }
            mutationSelector = DarwinMutationOperatorSelector(_operators, 25.0)
            addSolutionMutationListener(mutationSelector as DarwinMutationOperatorSelector<A>)
        }
    }

    public override fun addSolutionMutationListener(listener: SolutionMutationListener<A>) {
        solutionMutationListeners.add(listener)
    }

    public override fun addOperator(operator: MutationOperator<A>): OptimizationEngine<A> {
        _operators.add(operator);
        return this;
    }
    public override fun addFitnessFuntion(function: FitnessFunction<A>): OptimizationEngine<A> {
        _fitnesses.add(function);
        return this;
    }
    public override fun setPopulationFactory(populationFactory: PopulationFactory<A>): OptimizationEngine<A> {
        _populationFactory = populationFactory;
        return this;
    }
    public override fun setMaxGeneration(maxGeneration: Int): OptimizationEngine<A> {
        _maxGeneration = maxGeneration;
        return this;
    }
    public override fun setMaxTime(maxTime: Long): OptimizationEngine<A> {
        _maxTime = maxTime;
        return this;
    }

    private fun activateExecutionModel() {
        if(_executionModel == null){
            _executionModelFactory = DefaultExecutionModelFactory()
            _executionModel = _executionModelFactory!!.createExecutionModel()
        }
    }
    public override fun getExecutionModel(): ExecutionModel? {
        return _executionModel
    }

    var _metricsName: MutableList<FitnessMetric>

    public override fun addFitnessMetric(fitness: FitnessFunction<A>, metric: ParetoFitnessMetrics) {
        activateExecutionModel() //if at least one metric is added, initiate the ExecutionModel
        when(metric) {
            ParetoFitnessMetrics.Max -> {
                _metricsName.add(FitnessMetric(fitness.javaClass.getSimpleName(), "org.kevoree.modeling.optimization.executionmodel.Max"));
            }
            ParetoFitnessMetrics.Min -> {
                _metricsName.add(FitnessMetric(fitness.javaClass.getSimpleName(), "org.kevoree.modeling.optimization.executionmodel.Min"));
            }
            ParetoFitnessMetrics.Mean -> {
                _metricsName.add(FitnessMetric(fitness.javaClass.getSimpleName(), "org.kevoree.modeling.optimization.executionmodel.Mean"));
            }
            else -> {
            }
        }
    }

    public override fun addParetoMetric(metric: ParetoMetrics) {
        activateExecutionModel() //if at least one metric is added, initiate the ExecutionModel
        when(metric) {
            ParetoMetrics.Hypervolume -> {
                _metricsName.add(FitnessMetric(null, "org.kevoree.modeling.optimization.executionmodel.Hypervolume"));
            }
            ParetoMetrics.Mean -> {
                _metricsName.add(FitnessMetric(null, "org.kevoree.modeling.optimization.executionmodel.ParetoMean"));
            }
            else -> {
            }
        }
    }

}