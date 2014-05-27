package org.kevoree.modeling.optimization.api;

/**
 * Created by duke on 5/27/14.
 */
public interface Context {

    public Object get(String key);

    public void set(String key, Object elem);

}
