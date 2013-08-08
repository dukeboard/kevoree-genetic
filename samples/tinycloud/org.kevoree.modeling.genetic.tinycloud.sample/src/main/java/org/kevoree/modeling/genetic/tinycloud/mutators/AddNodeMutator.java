package org.kevoree.modeling.genetic.tinycloud.mutators;

import org.cloud.Cloud;
import org.cloud.Node;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.genetic.api.MutationOperator;

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
        Node node = cloudfactory.createNode();
        node.setId("node_"+Math.abs(rand.nextInt()));
        parent.addNodes(node);
    }
}