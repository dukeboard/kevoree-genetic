package org.kevoree.modeling.optimization.api;


import org.kevoree.modeling.api.KMFContainer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 15:26
 */
public trait MutationOperator<A : KMFContainer> {

    public fun mutate(parent : A);

}
