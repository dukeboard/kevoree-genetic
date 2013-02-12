package org.kevoree.genetic.framework;

import org.kevoree.ContainerRoot;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:13
 */
public interface KevoreePopulationFactory {

    public List<ContainerRoot> createPopulation();

}
