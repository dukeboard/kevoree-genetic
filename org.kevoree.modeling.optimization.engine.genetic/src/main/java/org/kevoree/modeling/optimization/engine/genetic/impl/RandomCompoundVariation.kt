package org.kevoree.modeling.optimization.engine.genetic.impl

import org.moeaframework.core.operator.CompoundVariation
import org.moeaframework.core.Solution
import java.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 04/09/13
 * Time: 17:54
 */
class RandomCompoundVariation : CompoundVariation() {

    var rand = Random();

    public override fun evolve(parents: Array<out Solution>?): Array<Solution>? {
         var indice = rand.nextInt(operators!!.size());
        return operators!!.get(indice).evolve(parents);
    }
}