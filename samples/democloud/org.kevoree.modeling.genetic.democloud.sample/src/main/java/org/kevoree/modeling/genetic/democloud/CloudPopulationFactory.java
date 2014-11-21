package org.kevoree.modeling.genetic.democloud;

import democloud.factory.DefaultDemocloudFactory;
import democloud.factory.DemocloudFactory;
import org.cloud.Cloud;
import org.cloud.VirtualNode;
import org.cloud.Software;



import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.api.compare.ModelCompare;
import org.kevoree.modeling.optimization.api.PopulationFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 9/30/13
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class CloudPopulationFactory implements PopulationFactory<Cloud> {

    private DemocloudFactory cloudfactory = new DefaultDemocloudFactory();

    private Integer size = 5;

    public CloudPopulationFactory setSize(Integer nSize) {
        size = nSize;
        return this;
    }

    @Override
    public List<Cloud> createPopulation() {
        ArrayList<Cloud> populations = new ArrayList<Cloud>();
        for (int i = 0; i < size; i++) {

            Cloud cloud = cloudfactory.createCloud();
            for (int j = 0; j < 50; j++) {
            VirtualNode myAmazonEC2node = cloudfactory.createAmazon();
            VirtualNode myRackspacenode = cloudfactory.createRackspace();

            Software web = cloudfactory.createSoftware();
            web.setName("web");
            web.setLatency(100.0);


            myAmazonEC2node.setId("EC2_"+j);
            myAmazonEC2node.setPricePerHour(10.0);
            myAmazonEC2node.addSoftwares(web);
            cloud.addNodes(myAmazonEC2node);


            myRackspacenode.setId("Rack_"+j);
            myRackspacenode.setPricePerHour(5.0);
            myRackspacenode.addSoftwares(web);
            cloud.addNodes(myRackspacenode);
            }

            populations.add(cloud);
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
