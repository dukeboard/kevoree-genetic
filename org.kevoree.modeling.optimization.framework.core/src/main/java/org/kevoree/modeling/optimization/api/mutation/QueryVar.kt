package org.kevoree.modeling.optimization.api.mutation

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 08/10/13
 * Time: 18:58
 */

public data class QueryVar(val query: String) : MutationVariable {
    override var name: String = "default"
}