package org.kevoree.modeling.genetic.democloud.mutators;

import org.cloud.Cloud;
import org.cloud.VirtualNode;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.optimization.api.MutationOperator;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 15:42
 */
public class AddNodeMutator implements MutationOperator<Cloud> {

    private Random rand = new Random();
    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();

    @Override
    public void mutate(Cloud parent) {
        VirtualNode node = cloudfactory.createVirtualNode();
        node.setId("node_"+Math.abs(rand.nextInt()));
        parent.addNodes(node);
    }
}
