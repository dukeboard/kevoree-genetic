package org.kevoree.modeling.optimization.engine.genetic.impl;

import org.kevoree.modeling.optimization.api.MutationOperator
import org.moeaframework.core.Solution
import org.moeaframework.core.Variation
import org.kevoree.modeling.api.KMFContainer

/**
 * Created by duke on 07/08/13.
 */
public class MutationVariationAdaptor<A : KMFContainer>(val operator: MutationOperator<A>) : Variation {
    public override fun getArity(): Int {
        return 1;
    }

    public override fun evolve(parents: Array<out Solution>?): Array<Solution>? {
        try {
            var clonedSolution = parents?.get(0)?.copy()
            var clonedVar = clonedSolution?.getVariable(0) as? ModelVariable;
            operator.mutate(clonedVar?.model!! as A);
            val result = Array<Solution>(1, { i->
                clonedSolution!!
            });
            return result
        } catch (e: Throwable) {
            e.printStackTrace();
            return null;
        }

    }

}
