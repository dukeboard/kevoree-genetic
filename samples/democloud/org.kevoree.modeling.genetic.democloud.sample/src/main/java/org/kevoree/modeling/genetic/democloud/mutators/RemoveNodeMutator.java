package org.kevoree.modeling.genetic.democloud.mutators;

import org.cloud.Cloud;
import org.kevoree.modeling.optimization.api.MutationOperator;

import java.util.Random;
/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/2/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
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
