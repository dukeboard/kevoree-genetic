package org.kevoree.modeling.optimization.api.solution;

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.GenerationContext
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 13/08/13
 * Time: 17:15
 */
public trait Solution<A : KMFContainer> {

    val model: A

    val context: GenerationContext<A>

    public fun getScoreForFitness(fitnessName: FitnessFunction<A>): Double?

    public fun getFitnesses(): Set<FitnessFunction<A>>

}
