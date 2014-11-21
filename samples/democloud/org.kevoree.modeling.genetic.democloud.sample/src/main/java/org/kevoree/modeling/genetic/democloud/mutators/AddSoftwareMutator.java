package org.kevoree.modeling.genetic.democloud.mutators;

import democloud.factory.DefaultDemocloudFactory;
import democloud.factory.DemocloudFactory;
import org.cloud.Cloud;
import org.cloud.VirtualNode;
import org.cloud.Software;
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
public class AddSoftwareMutator implements MutationOperator<Cloud> {

    private Random rand = new Random();
    private DemocloudFactory cloudfactory = new DefaultDemocloudFactory();


    @Override
    public List<MutationVariable> enumerateVariables(Cloud cloud) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "nodes[*]"));
    }


    @Override
    public void mutate(Cloud parent, MutationParameters mutationParameters) {

        Software web = cloudfactory.createSoftware();
        web.setName("web");
        web.setLatency(100.0);


        VirtualNode ec2node =parent.getNodes().get(rand.nextInt(parent.getNodes().size()));
        ec2node.addSoftwares(web);



    }
}
