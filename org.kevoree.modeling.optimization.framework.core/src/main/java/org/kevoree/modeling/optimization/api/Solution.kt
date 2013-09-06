package org.kevoree.modeling.optimization.api;

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.api.trace.TraceSequence

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 13/08/13
 * Time: 17:15
 */
public trait Solution {

    val model: KMFContainer

    val origin: KMFContainer?

    val traceSequence: TraceSequence?

    public fun getScoreForFitness(fitnessName: String): Double?

    public fun getFitnesses(): Set<String>

}
