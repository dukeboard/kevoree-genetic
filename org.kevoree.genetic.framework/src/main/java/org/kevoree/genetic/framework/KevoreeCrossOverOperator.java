package org.kevoree.genetic.framework;

import org.kevoree.ContainerRoot;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 15:30
 */
public interface KevoreeCrossOverOperator {

    public ContainerRoot cross(ContainerRoot parent, ContainerRoot parent2);

}
