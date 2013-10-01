package org.kevoree.modeling.genetic.democloud;




import org.cloud.Cloud;
import org.cloud.compare.DefaultModelCompare;
import org.cloud.impl.DefaultCloudFactory;

import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.api.compare.ModelCompare;
import org.kevoree.modeling.optimization.api.PopulationFactory;


import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:27
 */
public class DefaultCloudPopulationFactory implements PopulationFactory<Cloud> {

    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();

    private Integer size = 100;

    public DefaultCloudPopulationFactory setSize(Integer nSize) {
        size = nSize;
        return this;
    }

    @Override
    public List<Cloud> createPopulation() {
        ArrayList<Cloud> populations =  new ArrayList<Cloud>();
        for (int i = 0; i < size; i++) {
            populations.add(cloudfactory.createCloud());
        }
        return populations;
    }

    @Override
    public ModelCloner getCloner() {
        return new org.cloud.cloner.DefaultModelCloner();
    }

    @Override
    public ModelCompare getModelCompare() {
        return new DefaultModelCompare();
    }

}
