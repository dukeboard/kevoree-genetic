package org.kevoree.modeling.optimization.api;

import org.kevoree.modeling.api.KMFContainer

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 13/08/13
 * Time: 17:15
 */
public trait Solution<A : KMFContainer> {

    val model: A

    val context: GenerationContext<A>

    public fun getScoreForFitness(fitnessName: String): Double?

    public fun getFitnesses(): Set<String>

}
