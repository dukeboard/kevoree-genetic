package org.kevoree.modeling.optimization.engine.greedy;

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.MutationOperator
import org.kevoree.modeling.optimization.api.FitnessFunction
import org.kevoree.modeling.optimization.api.PopulationFactory
import org.kevoree.modeling.optimization.api.Solution
import java.util.ArrayList
import org.kevoree.modeling.optimization.framework.AbstractOptimizationEngine
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel
import org.kevoree.modeling.optimization.executionmodel.impl.DefaultExecutionModelFactory
import org.kevoree.modeling.optimization.framework.FitnessMetric

/**
 * Created by duke on 14/08/13.
 */
public class GreedyEngine<A : KMFContainer> : AbstractOptimizationEngine<A> {

    override var _operators: MutableList<MutationOperator<A>> = ArrayList<MutationOperator<A>>()
    override var _fitnesses: MutableList<FitnessFunction<A>> = ArrayList<FitnessFunction<A>>()
    override var _populationFactory: PopulationFactory<A>? = null
    override var _maxGeneration: Int = 100
    override var _maxTime: Long = -1.toLong()
    override var _executionModel: ExecutionModel? = null
    override var _executionModelFactory: DefaultExecutionModelFactory? = null
    override var _metricsName: MutableList<FitnessMetric> = ArrayList<FitnessMetric>()

    public override fun solve(): List<Solution> {
        var model = _populationFactory!!.createPopulation().get(0);
        var solutions = ArrayList<Solution>()
        return solutions;
    }


}
