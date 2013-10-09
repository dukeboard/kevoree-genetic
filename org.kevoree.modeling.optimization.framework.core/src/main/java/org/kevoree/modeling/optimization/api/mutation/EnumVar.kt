package org.kevoree.modeling.optimization.api.mutation

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 08/10/13
 * Time: 18:59
 */

public data class EnumVar(val elements: List<Any>) : MutationVariable {
    override var name: String = "default"
}