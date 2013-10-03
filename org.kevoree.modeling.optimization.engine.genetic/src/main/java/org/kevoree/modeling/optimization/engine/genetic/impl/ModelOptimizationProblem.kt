package org.kevoree.genetic.framework.internal;

import org.kevoree.modeling.api.ModelCloner
import org.moeaframework.core.Solution
import org.moeaframework.problem.AbstractProblem
import org.kevoree.modeling.optimization.api.FitnessFunction
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.engine.genetic.impl.ModelVariable
import org.kevoree.modeling.api.compare.ModelCompare
import org.kevoree.modeling.api.trace.Event2Trace
import java.util.HashMap

/**
 * Created with IntelliJ IDEA.
 * User: jbourcie
 * Date: 11/02/13
 * Time: 14:57
 */
public class ModelOptimizationProblem<A : KMFContainer>(val fitnesses: List<FitnessFunction<A>>, val cloner: ModelCloner, val modelCompare: ModelCompare) : AbstractProblem(1, fitnesses.size) {

    var emptyKevVariable: ModelVariable? = null;
    val fitnessFromIndice = HashMap<Int,FitnessFunction<A>>();
    val indiceFromFitness = HashMap<FitnessFunction<A>,Int>();

    {
        emptyKevVariable = ModelVariable(null, null, cloner, null, modelCompare, Event2Trace(modelCompare));
        var i = 0;
        for(fitness in fitnesses){
            fitnessFromIndice.put(i,fitness)
            indiceFromFitness.put(fitness,i)
            i++;
        }
    }

    public override fun evaluate(solution: Solution?) {
        var i = 0;
        for(fitness in fitnesses){
            var vloop = solution?.getVariable(0) as? ModelVariable;
            if(vloop != null){
                var fitness = fitnesses.get(i)
                var result = fitness.evaluate(vloop!!.model as A,vloop!!.origin as? A?,vloop!!.traceSequence);
                solution?.setObjective(i, result);
            }
            i++;
        }
    }

    public override fun newSolution(): Solution? {
        var solution = Solution(1, numberOfObjectives);
        solution.setVariable(0, emptyKevVariable);
        return solution;
    }

}
