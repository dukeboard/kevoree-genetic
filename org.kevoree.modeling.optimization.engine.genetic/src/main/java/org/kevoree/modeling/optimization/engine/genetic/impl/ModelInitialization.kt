package org.kevoree.modeling.optimization.engine.genetic.impl

import org.moeaframework.core.Initialization
import org.moeaframework.core.Solution
import org.kevoree.modeling.optimization.api.PopulationFactory
import org.kevoree.genetic.framework.internal.ModelOptimizationProblem
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.api.trace.Event2Trace
import org.kevoree.modeling.optimization.api.GenerationContext
import org.kevoree.modeling.optimization.api.Context

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 04/09/13
 * Time: 16:43
 */

public class ModelInitialization<A : KMFContainer>(val factory: PopulationFactory<A>, val problem: ModelOptimizationProblem<A>, val traceAware: Boolean, val ctx : Context) : Initialization {
    public override fun initialize(): Array<Solution>? {
        val modelCompare = factory.getModelCompare()
        val event2trace = Event2Trace(modelCompare)
        val models = factory.createPopulation()
        return Array<Solution>(models.size(), { i ->
            val s = problem.newSolution()!!
            val model = models.get(i)
            var initialTraceSeq = if(traceAware){modelCompare.createSequence()}else{null}
            val initialContext = GenerationContext(null, model, model, initialTraceSeq,null,ctx)
            s.setVariable(0, ModelVariable(model, initialContext, factory.getCloner(), modelCompare, event2trace, traceAware));
            s
        });
    }

}