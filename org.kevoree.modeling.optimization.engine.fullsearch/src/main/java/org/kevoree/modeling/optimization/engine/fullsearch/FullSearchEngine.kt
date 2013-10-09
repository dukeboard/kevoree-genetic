package org.kevoree.modeling.optimization.engine.fullsearch

import org.kevoree.modeling.optimization.api.OptimizationEngine
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.PopulationFactory
import org.kevoree.modeling.optimization.api.Solution
import java.util.ArrayList
import org.kevoree.modeling.optimization.framework.DefaultSolution
import org.kevoree.modeling.api.trace.TraceSequence
import org.kevoree.modeling.optimization.executionmodel.impl.DefaultExecutionModelFactory
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel
import org.kevoree.modeling.optimization.framework.AbstractOptimizationEngine
import org.kevoree.modeling.optimization.framework.FitnessMetric
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction

/**
 * Created by duke on 14/08/13.
 */

public class FullSearchEngine<A : KMFContainer> : AbstractOptimizationEngine<A> {

    override var _operators: MutableList<MutationOperator<A>> = ArrayList<MutationOperator<A>>()
    override var _fitnesses: MutableList<FitnessFunction<A>> = ArrayList<FitnessFunction<A>>()
    override var _populationFactory: PopulationFactory<A>? = null
    override var _maxGeneration: Int = 100
    override var _maxTime: Long = -1.toLong()
    override var _executionModel: ExecutionModel? = null
    override var _executionModelFactory: DefaultExecutionModelFactory? = null
    override var _metricsName: MutableList<FitnessMetric> = ArrayList<FitnessMetric>()

    var originAware = true

    override fun desactivateOriginAware() {
        originAware = false;
    }

    public override fun solve(): List<Solution<A>> {
        if (_operators.isEmpty()) {
            throw Exception("No operators are configured, please configure at least one");
        }
        if (_fitnesses.isEmpty()) {
            throw Exception("No fitness function are configured, please configure at least one");
        }
        if(_populationFactory == null){
            throw Exception("No population factory are configured, please configure at least one");
        }
        var solutions = ArrayList<Solution<A>>()
        var population = _populationFactory!!.createPopulation();
        for(initElem in population){
            for(operator in _operators){
                  val enumerationVariables = operator.enumerateVariables();

            }
        }



        var bestSolution: Solution<A> = if(originAware){
            buildSolution(model, model, _populationFactory!!.getModelCompare().createSequence())
        } else {
            buildSolution(model, null, null)
        }
        var iterationNB = _maxGeneration;


        solutions.add(bestSolution);
        return solutions;
    }

    private fun nextIteration(previousSolution: Solution): List<Solution> {
        var solutions = ArrayList<Solution>()


        return solutions;
    }

    private fun buildSolution(model: A, origin: A?, traceSeq: TraceSequence?): Solution {
        var newSolution = DefaultSolution(model, origin, traceSeq)
        for(fitness in _fitnesses){
            newSolution.results.put(fitness.javaClass.getSimpleName(), fitness.evaluate(model, origin, traceSeq))
        }
        return newSolution;
    }


}