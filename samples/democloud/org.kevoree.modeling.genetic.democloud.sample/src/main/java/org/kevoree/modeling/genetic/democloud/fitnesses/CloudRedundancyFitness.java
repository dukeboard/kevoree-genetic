package org.kevoree.modeling.genetic.democloud.fitnesses;

import org.cloud.Cloud;
import org.cloud.VirtualNode;
import org.cloud.impl.DefaultCloudFactory;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.kevoree.modeling.genetic.democloud.Requirements;
import org.kevoree.modeling.optimization.api.FitnessFunction;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/2/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class CloudRedundancyFitness implements FitnessFunction<Cloud> {




    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();

    @Override
    public boolean originAware(){
        return false;
    }

    @Override
    public double evaluate(Cloud model, Cloud origin, TraceSequence traceSequence)
    {
        int size =0;

        Requirements redundancy  =new Requirements();
        double count =redundancy.Redunduncy(model) ;



        for(VirtualNode vnode : model.getNodes())
        {
            size = size + vnode.getSoftwares().size();
            System.out.println("redunduncy is"+ size )  ;
        }


        double redundancyeval =   (count/size) * 100;
        System.out.println("redunduncy is"+ size )  ;
        return redundancyeval;
    }

}
