package org.kevoree.modeling.genetic.democloud.mutators.SmartMutator;

import org.cloud.Cloud;
import org.cloud.VirtualNode;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.QueryVar;

import org.kevoree.modeling.optimization.api.mutation.MutationVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    public List<MutationVariable> enumerateVariables(Cloud cloud) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "nodes[*]"));
    }


    @Override
    public void mutate(Cloud parent, MutationParameters mutationParameters) {



        int cnumber =0;

        VirtualNode smallestvm = parent.getNodes().get(rand.nextInt(parent.getNodes().size())) ;
        int min   =   smallestvm.getSoftwares().size();

        for (VirtualNode vmnode :parent.getNodes())
        {
            cnumber = vmnode.getSoftwares().size();

            if (min > cnumber)
            {
                min =   cnumber;
                smallestvm   =   vmnode;
            }

        }




        smallestvm.removeAllSoftwares();





    }
}
