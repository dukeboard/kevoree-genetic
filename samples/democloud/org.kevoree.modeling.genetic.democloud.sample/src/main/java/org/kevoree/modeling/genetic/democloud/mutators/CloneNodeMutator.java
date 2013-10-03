package org.kevoree.modeling.genetic.democloud.mutators;

import org.cloud.Cloud;
import org.cloud.Software;
import org.cloud.VirtualNode;

import org.cloud.cloner.DefaultModelCloner;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.optimization.api.MutationOperator;
import org.kevoree.modeling.genetic.democloud.CloudPopulationFactory;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/2/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class CloneNodeMutator implements MutationOperator<Cloud> {


    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();
    private ModelCloner cloner = new DefaultModelCloner();
    private Random rand = new Random();

    @Override
    public void mutate(Cloud parent) {


       //locate a node

        VirtualNode ec2node = parent.findNodesByID("EC2_"+rand.nextInt(parent.getNodes().size()));

        if (ec2node!=null)

        {

       // System.out.println("Clone"+ec2node.getId());

        VirtualNode clonedNode= cloner.clone(ec2node);

        clonedNode.setId("EC2_"+parent.getNodes().size());

        parent.addNodes(clonedNode);

        // System.out.println("cloned"+clonedNode.getId());

    }
}
}