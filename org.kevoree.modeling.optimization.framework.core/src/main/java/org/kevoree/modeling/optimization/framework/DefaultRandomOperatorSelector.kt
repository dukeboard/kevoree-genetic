package org.kevoree.modeling.optimization.framework

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.mutation.MutationOperatorSelector
import org.kevoree.modeling.optimization.api.Solution
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import java.util.Random

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 28/10/2013
 * Time: 19:39
 */

class DefaultRandomOperatorSelect<A : KMFContainer>(override val operators: List<MutationOperator<A>>) : MutationOperatorSelector<A> {

    val rand = Random()

    override fun select(solution: Solution<A>): MutationOperator<A> {
        var indice = rand.nextInt(operators.size());
        return operators.get(indice);
    }

}