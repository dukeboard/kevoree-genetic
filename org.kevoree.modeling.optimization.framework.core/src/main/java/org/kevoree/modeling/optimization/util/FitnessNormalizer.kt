package org.kevoree.modeling.optimization.util

import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation

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
        if(rawValue > maxValue || rawValue < minValue){
            throw Exception("out of bound value "+rawValue+" for "+fitness);
        }
        val normalizedValue = (rawValue - minValue) / (maxValue - minValue)
        if(fitness.orientation() == FitnessOrientation.MAXIMIZE){
            return 1 - normalizedValue
        } else {
            return normalizedValue
        }
    }

}