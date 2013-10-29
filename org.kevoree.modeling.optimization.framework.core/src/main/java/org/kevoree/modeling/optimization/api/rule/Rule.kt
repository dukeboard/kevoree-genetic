package org.kevoree.modeling.optimization.api

import org.kevoree.modeling.api.KMFContainer

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/10/13
 * Time: 15:37
 */

public trait Rule<A : KMFContainer> {

    public fun check(model: A): Boolean;

}
