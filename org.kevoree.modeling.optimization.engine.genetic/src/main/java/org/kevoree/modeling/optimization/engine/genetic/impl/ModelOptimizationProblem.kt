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
import org.kevoree.modeling.optimization.util.FitnessNormalizer

/**
 * User: duke
 * Date: 11/02/13
 * Time: 14:57
 */
public class ModelOptimizationProblem<A : KMFContainer>(val fitnesses: List<FitnessFunction<A>>, val cloner: ModelCloner, val modelCompare: ModelCompare) : AbstractProblem(1, fitnesses.size) {

    val fitnessFromIndice = HashMap<Int, FitnessFunction<A>>();
    val indiceFromFitness = HashMap<FitnessFunction<A>, Int>();

    {
        //asign a unique indice for each fitness
        var i = 0;
        for(fitness in fitnesses){
            fitnessFromIndice.put(i, fitness)
            indiceFromFitness.put(fitness, i)
            i++;
        }
    }

    public override fun evaluate(solution: Solution?) {
        val hybridSol = solution as HybridSolution<*>
        if(hybridSol.alreadyEvaluated){
            return
        } else {
            for(fitnessT in indiceFromFitness){
                val vloop = solution?.getVariable(0) as? ModelVariable<A>;
                if(vloop != null){
                    //var fitness = fitnesses.get(i)
                    val rawValue = fitnessT.key.evaluate(vloop.model, vloop.context)
                    solution.rawResults.put(fitnessT.key,rawValue)
                    solution.setObjective(fitnessT.value, FitnessNormalizer.norm(rawValue, fitnessT.key));
                }
            }
            hybridSol.alreadyEvaluated = true
        }
    }

    public override fun newSolution(): Solution? {
        var solution = HybridSolution(numberOfObjectives, this);
        return solution;
    }

}
