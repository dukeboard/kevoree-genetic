package org.kevoree.modeling.optimization.framework.selector

import org.kevoree.modeling.api.KMFContainer

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 29/10/2013
 * Time: 09:38
 */

data class MutatorRanking<A : KMFContainer>(
        var positiveMean: Double,
        var negativeMean: Double,
        var positiveSum: Double,
        var negativeSum: Double,
        var nbSelection: Int,
        var selectionProbability : Double
)
