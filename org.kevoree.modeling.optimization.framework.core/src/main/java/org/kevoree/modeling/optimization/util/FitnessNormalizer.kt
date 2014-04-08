package org.kevoree.modeling.optimization.util

import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation
import org.kevoree.modeling.optimization.api.fitness.GaussianFitnessFunction

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 31/10/2013
 * Time: 10:26
 */

public object FitnessNormalizer {

    /* Transform raw value between 0 and 1 and 0 is the best value */
    fun norm(rawValue: Double, fitness: FitnessFunction<*>): Double {
        val minValue = fitness.min()
        val maxValue = fitness.max()
        //check fitnessRawValue
        if (rawValue > maxValue || rawValue < minValue) {
            throw Exception("out of bound value " + rawValue + " for " + fitness);
        }
        var normalizedValue = (rawValue - minValue) / (maxValue - minValue)
        if (fitness.orientation == FitnessOrientation.MAXIMIZE) {
            normalizedValue = 1 - normalizedValue
        }
        if (fitness is GaussianFitnessFunction) {
            var target = fitness.target()
            var normalizedTarget = (target - minValue) / (maxValue - minValue)
            var variance = fitness.variance()
            var normalizedVariance = (variance ) / (maxValue - minValue)
            normalizedValue = 1 - Math.exp(-(normalizedValue - normalizedTarget) * (normalizedValue - normalizedTarget) / (normalizedVariance*normalizedVariance) )
        }
        return normalizedValue
    }

}
