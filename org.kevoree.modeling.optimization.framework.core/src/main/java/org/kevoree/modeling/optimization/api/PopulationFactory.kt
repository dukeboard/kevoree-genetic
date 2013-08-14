package org.kevoree.modeling.optimization.api;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.api.ModelCloner;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 17:13
 */
public trait PopulationFactory<A : KMFContainer> {

    public fun createPopulation() : List<A>;

    public fun getCloner() : ModelCloner;

}
