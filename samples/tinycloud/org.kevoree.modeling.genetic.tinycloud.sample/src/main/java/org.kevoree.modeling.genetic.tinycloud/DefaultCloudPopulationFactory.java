package org.kevoree.modeling.genetic.tinycloud;

import org.cloud.Cloud;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.genetic.api.PopulationFactory;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:27
 */
public class DefaultCloudPopulationFactory implements PopulationFactory<Cloud> {

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
