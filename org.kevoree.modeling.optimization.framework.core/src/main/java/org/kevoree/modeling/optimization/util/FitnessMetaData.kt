package org.kevoree.modeling.optimization.util

import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation
import org.kevoree.modeling.api.KMFContainer

/**
 * Created by duke on 7/14/14.
 */

data class FitnessMetaData<A : KMFContainer>(val fitness : FitnessFunction<A>, val min : Double, val max : Double, val orientation : FitnessOrientation, val target : Double?, val std : Double?)