package org.kevoree.modeling.genetic.democloud.mutators;

import org.cloud.Cloud;
import org.cloud.VirtualNode;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.QueryVar;

import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.MutationVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


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
    public List<MutationVariable> enumerateVariables(Cloud cloud) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "nodes[*]"));
    }

    @Override
    public void mutate(Cloud parent, MutationParameters mutationParameters) {
        if (!parent.getNodes().isEmpty()) {
           parent.removeNodes(parent.getNodes().get(rand.nextInt(parent.getNodes().size())));
        }
    }

}
