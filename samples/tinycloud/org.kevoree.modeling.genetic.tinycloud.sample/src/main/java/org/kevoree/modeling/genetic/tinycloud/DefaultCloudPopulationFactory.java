package org.kevoree.modeling.genetic.tinycloud;

import org.cloud.Cloud;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.genetic.api.PopulationFactory;

import java.util.ArrayList;
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
        ArrayList<Cloud> populations = new ArrayList<Cloud>();
        for(int i=0;i<100;i++){
            populations.add(cloudfactory.createCloud());
        }
        return populations;
    }

    @Override
    public ModelCloner getCloner() {
        return new org.cloud.cloner.DefaultModelCloner();
    }
}
