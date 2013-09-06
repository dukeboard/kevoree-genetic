package org.kevoree.modeling.optimization.engine.fullsearch

import org.kevoree.modeling.optimization.api.OptimizationEngine
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.MutationOperator
import org.kevoree.modeling.optimization.api.FitnessFunction
import org.kevoree.modeling.optimization.api.PopulationFactory
import org.kevoree.modeling.optimization.api.Solution
import java.util.ArrayList
import org.kevoree.modeling.optimization.framework.DefaultSolution
import org.kevoree.modeling.api.trace.TraceSequence

/**
 * Created by duke on 14/08/13.
 */

public class FullSearchEngine<A : KMFContainer> : OptimizationEngine<A> {

    var _operators: MutableList<MutationOperator<A>> = ArrayList<MutationOperator<A>>();
    var _fitnesses: MutableList<FitnessFunction<A>> = ArrayList<FitnessFunction<A>>();
    var _populationFactory: PopulationFactory<A>? = null;
    var _maxGeneration = 100;
    var _maxTime: Long = -1.toLong();

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

    public override fun solve(): List<Solution> {
        if (_operators.isEmpty()) {
            throw Exception("No operators are configured, please configure at least one");
        }
        if (_fitnesses.isEmpty()) {
            throw Exception("No fitness function are configured, please configure at least one");
        }
        if(_populationFactory == null){
            throw Exception("No population factory are configured, please configure at least one");
        }
        var originAware = false
        for(fitness in _fitnesses){
            if(fitness.originAware()){
                originAware = true
            }
        }
        var model = _populationFactory!!.createPopulation().get(0);
        var bestSolution: Solution = if(originAware){
            buildSolution(model, model, _populationFactory!!.getModelCompare().createSequence())
        } else {
            buildSolution(model, null, null)
        }
        var iterationNB = _maxGeneration;
        while(iterationNB > 0){

            iterationNB = iterationNB - 1;
        }

        var solutions = ArrayList<Solution>()
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