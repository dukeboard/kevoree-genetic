package org.kevoree.modeling.genetic.democloud.mutators;

import democloud.factory.DefaultDemocloudFactory;
import democloud.factory.DemocloudFactory;
import org.cloud.Cloud;
import org.cloud.VirtualNode;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.QueryVar;

import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.MutationVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/2/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class AddNodeMutator implements MutationOperator<Cloud> {

    private Random rand = new Random();
    private DemocloudFactory cloudfactory = new DefaultDemocloudFactory();

    @Override
    public List<MutationVariable> enumerateVariables(Cloud cloud) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "nodes[*]"));
    }

    @Override
    public void mutate(Cloud parent, MutationParameters mutationParameters) {

        int i = rand.nextInt(1);

        if (i==0)
        {
        VirtualNode node = cloudfactory.createAmazon();
        node.setId("EC2_"+Math.abs(rand.nextInt()));
        node.setPricePerHour(10.0);
        parent.addNodes(node);
        }

        else
        {
        VirtualNode node = cloudfactory.createRackspace();
        node.setId("Rack_"+Math.abs(rand.nextInt()));
        node.setPricePerHour(5.0);
        parent.addNodes(node);}





    }
}
