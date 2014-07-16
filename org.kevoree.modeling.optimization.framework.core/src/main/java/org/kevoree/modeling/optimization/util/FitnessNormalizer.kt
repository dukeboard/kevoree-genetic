package org.kevoree.modeling.optimization.util

import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 31/10/2013
 * Time: 10:26
 */

public object FitnessNormalizer {

    /* Transform raw value between 0 and 1 and 0 is the best value */
    fun norm(rawValue: Double, fitness: FitnessMetaData<*>): Double {
        //check fitnessRawValue
        if (rawValue > fitness.max || rawValue < fitness.min) {
            throw Exception("out of bound value " + rawValue + " for " + fitness);
        }
        var normalizedValue = (rawValue - fitness.min) / (fitness.max - fitness.min)

        if (fitness.target != null && fitness.std != null) {
            var normalizedTarget = (fitness.target - fitness.min) / (fitness.max - fitness.min)
            var normalizedVariance = (fitness.std ) / (fitness.max - fitness.min)
            normalizedVariance = normalizedVariance * normalizedVariance
            normalizedValue = 1 - Math.exp(-(normalizedValue - normalizedTarget) * (normalizedValue - normalizedTarget) / (2 * normalizedVariance))
            return normalizedValue
        }

        if (fitness.orientation == FitnessOrientation.MAXIMIZE) {
           normalizedValue = 1 - normalizedValue
        }


        return normalizedValue
    }


}
