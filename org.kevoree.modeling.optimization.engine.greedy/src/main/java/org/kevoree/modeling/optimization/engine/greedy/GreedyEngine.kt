package org.kevoree.modeling.optimization.engine.greedy;

import org.kevoree.modeling.optimization.api.OptimizationEngine
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.MutationOperator
import org.kevoree.modeling.optimization.api.FitnessFunction
import org.kevoree.modeling.optimization.api.PopulationFactory
import org.kevoree.modeling.optimization.api.Solution
import java.util.ArrayList
import org.kevoree.modeling.optimization.framework.DefaultSolution

/**
 * Created by duke on 14/08/13.
 */
public class GreedyEngine<A: KMFContainer>: OptimizationEngine<A> {

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

        var model = _populationFactory!!.createPopulation().get(0);

        var solutions = ArrayList<Solution>()
        return solutions;
    }


}
