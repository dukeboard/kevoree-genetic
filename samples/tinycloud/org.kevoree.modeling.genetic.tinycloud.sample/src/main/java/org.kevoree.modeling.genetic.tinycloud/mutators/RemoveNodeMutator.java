package org.kevoree.modeling.genetic.tinycloud.mutators;

import org.cloud.Cloud;
import org.kevoree.genetic.framework.KevoreeMutationOperator;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:02
 */
public class RemoveNodeMutator implements KevoreeMutationOperator<Cloud> {

    private Random rand = new Random();

    @Override
    public Cloud mutate(Cloud parent) {
        if (!parent.getNodes().isEmpty()) {
            parent.removeNodes(parent.getNodes().get(rand.nextInt(parent.getNodes().size())));
        }
        return parent;
    }

}
