package org.kevoree.modeling.genetic.cloudml.sample.mutator;

import org.cloudml.core.Node;
import org.cloudml.core.NodeInstance;
import org.cloudml.core.impl.DefaultCoreFactory;
import org.kevoree.modeling.genetic.api.MutationOperator;

import java.util.Random;
import org.cloudml.core.DeploymentModel;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 15:42
 */
public class AddNodeMutator implements MutationOperator<DeploymentModel> {

    private Random rand = new Random();
    private DefaultCoreFactory cloudfactory = new DefaultCoreFactory();

    @Override
    public void mutate(DeploymentModel parent) {
        NodeInstance node = cloudfactory.createNodeInstance();
        node.setName("node_" + Math.abs(rand.nextInt()));
        parent.addNodeInstances(node);
    }
}
