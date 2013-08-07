package org.kevoree.genetic.framework;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.api.ModelCloner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:13
 */
public interface KevoreePopulationFactory<A extends KMFContainer> {

    public List<A> createPopulation();

    public ModelCloner getCloner();

}
