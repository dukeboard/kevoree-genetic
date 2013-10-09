package org.kevoree.modeling.genetic.tinycloud.mutators;

import org.cloud.Cloud;
import org.cloud.Node;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.optimization.api.mutation.EnumVar;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.MutationVariable;

import java.util.Arrays;
import java.util.List;
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
    private Integer maxNode = 10;

    @Override
    public List<MutationVariable> enumerateVariables(Cloud cloud) {
        if (cloud.getNodes().size() < maxNode) {
            Node node = cloudfactory.createNode();
            return Arrays.asList((MutationVariable) new EnumVar("target", Arrays.asList(node)));
        } else {
            return Arrays.asList();
        }
    }

    @Override
    public void mutate(Cloud model, MutationParameters mutationParameters) {
        Node target = (Node) mutationParameters.getParam("target");
        if (target != null) {
            target.setId("node_" + Math.abs(rand.nextInt()));
            model.addNodes(target);
        }
    }
}
