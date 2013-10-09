package org.kevoree.genetic.framework.internal;

import org.kevoree.modeling.api.ModelCloner
import org.moeaframework.core.Solution
import org.moeaframework.problem.AbstractProblem
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.engine.genetic.impl.ModelVariable
import org.kevoree.modeling.api.compare.ModelCompare
import java.util.HashMap
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.engine.genetic.impl.HybridSolution

/**
 * User: duke
 * Date: 11/02/13
 * Time: 14:57
 */
public class ModelOptimizationProblem<A : KMFContainer>(val fitnesses: List<FitnessFunction<A>>, val cloner: ModelCloner, val modelCompare: ModelCompare) : AbstractProblem(1, fitnesses.size) {

    val fitnessFromIndice = HashMap<Int, FitnessFunction<A>>();
    val indiceFromFitness = HashMap<String, Int>();

    {
        var i = 0;
        for(fitness in fitnesses){
            fitnessFromIndice.put(i, fitness)
            indiceFromFitness.put(fitness.javaClass.getCanonicalName()!!, i)
            i++;
        }
    }

    public override fun evaluate(solution: Solution?) {
        var i = 0;
        for(fitness in fitnesses){
            val vloop = solution?.getVariable(0) as? ModelVariable<A>;
            if(vloop != null){
                var fitness = fitnesses.get(i)
                var result = fitness.evaluate(vloop.model, vloop.context);
                solution?.setObjective(i, result);
            }
            i++;
        }
    }

    public override fun newSolution(): Solution? {
        var solution = HybridSolution(numberOfObjectives, this);
        return solution;
    }

}
