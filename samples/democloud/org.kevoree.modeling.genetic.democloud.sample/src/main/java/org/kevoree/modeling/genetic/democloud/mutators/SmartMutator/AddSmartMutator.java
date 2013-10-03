package org.kevoree.modeling.genetic.democloud.mutators.SmartMutator;

import org.cloud.Cloud;
import org.cloud.Software;
import org.cloud.VirtualNode;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.optimization.api.MutationOperator;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/3/13
 * Time: 6:59 PM
 * To change this template use File | Settings | File Templates.
 */

public class AddSmartMutator implements MutationOperator<Cloud> {

    private Random rand = new Random();
    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();

    @Override

    /*Adds a component in the VM node that contains less components*/

    public void mutate(Cloud parent) {


        int componentnumber =0;

        VirtualNode smallestvm = parent.getNodes().get(rand.nextInt(parent.getNodes().size())) ;
        int min   =   smallestvm.getSoftwares().size();

        for (VirtualNode vmnode :parent.getNodes())
        {
        componentnumber = vmnode.getSoftwares().size();

            if (min > componentnumber)
            {
                min =   componentnumber;
                smallestvm   =   vmnode;
            }

        }

        Software web = cloudfactory.createSoftware();
        web.setName("web");
        web.setLatency(100.0);
        smallestvm.addSoftwares(web);





    }
}
