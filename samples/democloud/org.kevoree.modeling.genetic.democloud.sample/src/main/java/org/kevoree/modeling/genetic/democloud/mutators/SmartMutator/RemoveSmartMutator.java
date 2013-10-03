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
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoveSmartMutator implements MutationOperator<Cloud> {

    private Random rand = new Random();
    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();

    @Override

    /* Removes  components in the most available VM to free the VM */

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


        smallestvm.removeAllSoftwares();





    }
}
