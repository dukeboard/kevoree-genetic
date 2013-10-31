package org.kevoree.modeling.optimization.framework;

import org.kevoree.modeling.optimization.api.solution.Solution
import org.kevoree.modeling.api.KMFContainer
import java.util.HashMap
import org.kevoree.modeling.optimization.api.GenerationContext
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction

/**
 * Created by duke on 14/08/13.
 */
public class DefaultSolution<A : KMFContainer>(override val model: A, override val context: GenerationContext<A>) : Solution<A> {

    var results = HashMap<FitnessFunction<A>, Double>()

    public override fun getFitnesses(): Set<FitnessFunction<A>> {
        return results.keySet()
    }

    public override fun getScoreForFitness(fitnessName: FitnessFunction<A>): Double? {
        return results.get(fitnessName)
    }

}
