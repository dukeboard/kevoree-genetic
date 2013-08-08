package org.kevoree.modeling.genetic.api;


import org.kevoree.modeling.api.KMFContainer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 15:26
 */
public interface MutationOperator<A extends KMFContainer> {

    public void mutate(A parent);

}
