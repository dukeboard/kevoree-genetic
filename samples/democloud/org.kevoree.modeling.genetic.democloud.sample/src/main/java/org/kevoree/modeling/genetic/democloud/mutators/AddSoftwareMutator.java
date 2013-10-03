package org.kevoree.modeling.genetic.democloud.mutators;

import org.cloud.Cloud;
import org.cloud.Software;
import org.cloud.VirtualNode;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.optimization.api.MutationOperator;
import org.kevoree.modeling.genetic.democloud.CloudPopulationFactory;

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
    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();

    @Override

    public void mutate(Cloud parent) {

        Software web = cloudfactory.createSoftware();
        web.setName("web");
        web.setLatency(100.0);


        VirtualNode ec2node =parent.getNodes().get(rand.nextInt(parent.getNodes().size()));
        ec2node.addSoftwares(web);



    }
}
