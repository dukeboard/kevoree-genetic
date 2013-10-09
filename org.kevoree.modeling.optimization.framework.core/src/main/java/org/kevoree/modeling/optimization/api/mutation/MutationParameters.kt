package org.kevoree.modeling.optimization.api.mutation

import java.util.HashMap

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 08/10/13
 * Time: 18:52
 */

public class MutationParameters {

    private val params = HashMap<Any, Any>()

    public fun getParam(key: Any): Any? {
        return params.get(key)
    }

    public fun setParam(key: Any, value: Any) {
        params.put(key, value)
    }

    public fun getKeys(): Set<Any> {
        return params.keySet()
    }

}