package org.kevoree.modeling.optimization.api.mutation

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 08/10/13
 * Time: 18:59
 */

public data class EnumVar(override val name: String,val elements: List<Any>) : MutationVariable