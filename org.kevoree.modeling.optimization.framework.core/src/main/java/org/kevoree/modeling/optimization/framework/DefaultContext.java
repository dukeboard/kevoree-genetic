package org.kevoree.modeling.optimization.framework;

import org.kevoree.modeling.optimization.api.Context;

import java.util.HashMap;

/**
 * Created by duke on 5/27/14.
 */
public class DefaultContext implements Context {

    private HashMap<String, Object> keys = new HashMap<String, Object>();

    @Override
    public Object get(String key) {
        return keys.get(key);
    }

    @Override
    public void set(String key, Object elem) {
        keys.put(key, elem);
    }
}
