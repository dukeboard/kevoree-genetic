package org.kevoree.modeling.genetic.api;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.api.ModelCloner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:13
 */
public interface PopulationFactory<A extends KMFContainer> {

    public List<A> createPopulation();

    public ModelCloner getCloner();

}
