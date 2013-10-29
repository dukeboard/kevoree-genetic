package org.kevoree.modeling.optimization.engine.genetic.impl

import org.moeaframework.core.Solution
import java.util.Random
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine
import org.moeaframework.core.Variation
import org.kevoree.modeling.api.KMFContainer

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 04/09/13
 * Time: 17:54
 */
class CompoundVariation<A : KMFContainer>(val engine : GeneticEngine<A>) : Variation {

    override fun getArity(): Int {
        return 1
    }

    public override fun evolve(parents: Array<out Solution>?): Array<Solution>? {
        val solution = parents!!.get(0)
        val selectedOperator = engine.mutationSelector.select(solution as org.kevoree.modeling.optimization.api.solution.Solution<A>) as Variation
        return selectedOperator.evolve(parents);
    }
}