

package org.kevoree.modeling.genetic.democloud.mutators;

import org.cloud.Cloud;
import org.cloud.Software;
import org.cloud.VirtualNode;
import org.kevoree.modeling.optimization.api.MutationOperator;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/2/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class RemoveSoftwareMutator implements MutationOperator<Cloud> {

    private Random rand = new Random();

    @Override

    public void mutate(Cloud parent) {


        VirtualNode ec2node =parent.getNodes().get(rand.nextInt(parent.getNodes().size()));

        if (ec2node!=null)
        ec2node.removeAllSoftwares();



    }

}
