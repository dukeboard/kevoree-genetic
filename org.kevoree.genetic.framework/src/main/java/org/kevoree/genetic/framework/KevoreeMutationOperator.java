package org.kevoree.genetic.framework;

import org.kevoree.ContainerRoot;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 15:26
 */
public interface KevoreeMutationOperator {

    public ContainerRoot mutate(ContainerRoot parent);

}
