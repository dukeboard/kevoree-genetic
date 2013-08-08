package org.kevoree.modeling.genetic.cloudml.sample;

import org.cloudml.core.DeploymentModel;
import org.cloudml.core.impl.DefaultCoreFactory;
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
public class DefaultCloudPopulationFactory implements PopulationFactory<DeploymentModel> {

    private DefaultCoreFactory cloudfactory = new DefaultCoreFactory();

    @Override
    public List<DeploymentModel> createPopulation() {

        ArrayList<DeploymentModel> populations = new ArrayList<DeploymentModel>();
        for(int i=0;i<100;i++){
             populations.add(cloudfactory.createDeploymentModel());
        }
        return populations;
    }

    @Override
    public ModelCloner getCloner() {
        return new org.cloudml.cloner.DefaultModelCloner();
    }
}
