package org.kevoree.genetic.framework;


import org.kevoree.modeling.api.KMFContainer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 15:26
 */
public interface KevoreeMutationOperator<A extends KMFContainer> extends KevoreeOperator {

    public A mutate(A parent);

}
