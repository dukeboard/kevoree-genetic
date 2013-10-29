package org.kevoree.modeling.optimization.engine.genetic.impl

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.GenerationContext
import org.kevoree.genetic.framework.internal.ModelOptimizationProblem
import org.moeaframework.core.Solution

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 09/10/13
 * Time: 16:28
 */

public class HybridSolution<A : KMFContainer>(val numberOfObjectives: Int, val problem: ModelOptimizationProblem<A>) : org.moeaframework.core.Solution(1, numberOfObjectives), org.kevoree.modeling.optimization.api.solution.Solution<A> {

    override val model: A
        get(){
            val modelVar = getVariable(0) as ModelVariable<A>
            return modelVar.model
        }

    override val context: GenerationContext<A>
        get(){
            val modelVar = getVariable(0) as ModelVariable<A>
            return modelVar.context
        }

    override fun getScoreForFitness(fitnessName: String): Double? {
        val fitnessIndice = problem.indiceFromFitness.get(fitnessName)!!
        return getObjective(fitnessIndice)
    }
    override fun getFitnesses(): Set<String> {
        return problem.indiceFromFitness.keySet()
    }

    override fun copy(): Solution? {
        val newSolution = HybridSolution(numberOfObjectives, problem);
        newSolution.setVariable(0, getVariable(0)!!.copy());
        return newSolution;
    }
}
