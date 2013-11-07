package org.kevoree.modeling.genetic.genetictest.mutators;

import org.genetictest.BinaryString;
import org.genetictest.MyBoolean;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.MutationVariable;
import org.kevoree.modeling.optimization.api.mutation.QueryVar;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * User: assaad.moawad
 * Date: 06/11/13
 * Time: 17:55
 * University of Luxembourg - Snt
 * assaad.mouawad@gmail.com
 */
public class SwitchMutatorAll implements MutationOperator<BinaryString> {

    private Random rand = new Random();

    @Override
    public List<MutationVariable> enumerateVariables(BinaryString bs) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "values[*]"));
        // what to do here??
    }

    @Override
    public void mutate(BinaryString parent, MutationParameters mutationParameters) {

        MyBoolean x;
        for(int i=0; i<parent.getValues().size();i++)
        {
            x=parent.getValues().get(i);
        if(rand.nextDouble()<0.1)
            x.setValue(!x.getValue());
        }




    }
}
