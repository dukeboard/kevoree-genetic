package org.kevoree.modeling.genetic.democloud;




import democloud.factory.DefaultDemocloudFactory;
import democloud.factory.DemocloudFactory;
import org.cloud.Cloud;

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

    private DemocloudFactory cloudfactory = new DefaultDemocloudFactory();

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
        return cloudfactory.createModelCloner();
    }

    @Override
    public ModelCompare getModelCompare() {
        return cloudfactory.createModelCompare();
    }

}
