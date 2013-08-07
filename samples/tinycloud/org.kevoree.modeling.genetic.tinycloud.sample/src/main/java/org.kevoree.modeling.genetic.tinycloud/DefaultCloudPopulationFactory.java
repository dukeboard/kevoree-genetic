package org.kevoree.modeling.genetic.tinycloud;

import org.cloud.Cloud;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.genetic.framework.KevoreePopulationFactory;
import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.api.ModelCloner;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:27
 */
public class DefaultCloudPopulationFactory implements KevoreePopulationFactory<Cloud> {

    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();

    @Override
    public List<Cloud> createPopulation() {
        //TODO Apply randomly to create initial population
        return Collections.singletonList(cloudfactory.createCloud());
    }

    @Override
    public ModelCloner getCloner() {
        return new org.cloud.cloner.DefaultModelCloner();
    }
}
