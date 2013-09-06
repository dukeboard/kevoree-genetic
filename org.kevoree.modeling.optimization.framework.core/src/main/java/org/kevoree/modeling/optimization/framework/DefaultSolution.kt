package org.kevoree.modeling.optimization.framework;

import org.kevoree.modeling.optimization.api.Solution
import org.kevoree.modeling.api.KMFContainer
import java.util.HashMap
import org.kevoree.modeling.api.trace.TraceSequence

/**
 * Created by duke on 14/08/13.
 */
public class DefaultSolution(override val model: KMFContainer,override val origin: KMFContainer?,override val traceSequence: TraceSequence?) : Solution {

    var results = HashMap<String, Double>()

    public override fun getFitnesses(): Set<String> {
        return results.keySet()
    }

    public override fun getScoreForFitness(fitnessName: String): Double? {
        return results.get(fitnessName)
    }

}
