package org.kevoree.modeling.genetic.democloud.mutators;

import org.cloud.Cloud;
import org.cloud.Software;
import org.cloud.VirtualNode;

import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.optimization.api.MutationOperator;
import org.kevoree.modeling.genetic.democloud.CloudPopulationFactory;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 15:42
 */
public class CloneNodeMutator implements MutationOperator<Cloud> {


    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();




    @Override
    public void mutate(Cloud parent) {

        VirtualNode nodeclone = cloudfactory.createVirtualNode();

        VirtualNode ec2node = parent.findNodesByID("EC2_0");
        nodeclone.setId(ec2node.getId());

        parent.addNodes(nodeclone);
    }
}
