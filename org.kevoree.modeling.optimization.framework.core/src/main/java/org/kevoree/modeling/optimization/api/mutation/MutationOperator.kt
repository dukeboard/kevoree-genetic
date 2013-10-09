package org.kevoree.modeling.optimization.api.mutation;

import org.kevoree.modeling.api.KMFContainer

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 15:26
 */

public trait MutationOperator<A : KMFContainer> {

    public fun enumerateVariables(model: A): List<MutationVariable>

    public fun mutate(model: A, params: MutationParameters);

}
