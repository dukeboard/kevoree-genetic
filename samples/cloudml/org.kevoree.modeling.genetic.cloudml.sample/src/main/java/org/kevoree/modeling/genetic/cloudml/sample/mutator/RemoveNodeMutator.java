package org.kevoree.modeling.genetic.cloudml.sample.mutator;

import org.cloudml.core.DeploymentModel;
import org.kevoree.modeling.genetic.api.MutationOperator;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:02
 */
public class RemoveNodeMutator implements MutationOperator<DeploymentModel> {

    private Random rand = new Random();

    @Override
    public void mutate(DeploymentModel parent) {
        if (!parent.getNodeInstances().isEmpty()) {
            parent.removeNodeInstances(parent.getNodeInstances().get(rand.nextInt(parent.getNodeInstances().size())));
        }
    }

}
