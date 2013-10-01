package org.kevoree.modeling.genetic.democloud.mutators;

import org.cloud.Cloud;
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

    private Random rand = new Random();
    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();


    @Override
    public void mutate(Cloud parent) {

        VirtualNode nodeclone = cloudfactory.createVirtualNode();
        //nodeclone.setId(node.getId());
        parent.addNodes(nodeclone);
    }
}
