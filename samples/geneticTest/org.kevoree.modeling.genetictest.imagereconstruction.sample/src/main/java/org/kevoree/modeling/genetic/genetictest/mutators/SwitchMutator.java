package org.kevoree.modeling.genetic.genetictest.mutators;


import org.genetictest.BinaryString;
import org.genetictest.MyBoolean;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.QueryVar;

import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.MutationVariable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/2/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class SwitchMutator implements MutationOperator<BinaryString> {

    private Random rand = new Random();

   @Override
    public List<MutationVariable> enumerateVariables(BinaryString bs) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "values[*]"));
        // what to do here??
    }

    @Override
    public void mutate(BinaryString parent, MutationParameters mutationParameters) {



        MyBoolean x = ((MyBoolean) mutationParameters.getParam("target"));

        //if(rand.nextDouble()<0.1)
            x.setValue(!x.getValue());




    }
}
