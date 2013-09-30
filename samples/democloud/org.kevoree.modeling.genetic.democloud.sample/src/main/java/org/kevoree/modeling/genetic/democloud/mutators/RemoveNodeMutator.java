package org.kevoree.modeling.genetic.democloud.mutators;

import org.cloud.Cloud;
import org.kevoree.modeling.optimization.api.MutationOperator;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:02
 */
public class RemoveNodeMutator implements MutationOperator<Cloud> {

    private Random rand = new Random();

    @Override
    public void mutate(Cloud parent) {
        if (!parent.getNodes().isEmpty()) {
            parent.removeNodes(parent.getNodes().get(rand.nextInt(parent.getNodes().size())));
        }
    }

}
