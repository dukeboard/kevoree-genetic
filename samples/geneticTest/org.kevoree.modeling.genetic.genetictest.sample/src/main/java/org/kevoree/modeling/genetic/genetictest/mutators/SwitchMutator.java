package org.kevoree.modeling.genetic.genetictest.mutators;


import org.genetictest.BinaryString;
import org.genetictest.MyBoolean;
import org.kevoree.modeling.optimization.api.mutation.*;

import java.util.ArrayList;
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
public class SwitchMutator implements MutationOperator<BinaryString> {

    private Random rand = new Random();

   @Override
    public List<MutationVariable> enumerateVariables(BinaryString bs) {

       ArrayList< MyBoolean> lsBool = new ArrayList<MyBoolean>();

       for(MyBoolean b : bs.getValues())
       {
       if(!b.getValue()){
           lsBool.add(b);
       }
       }

       EnumVar temp = new EnumVar("target",lsBool);
        return Arrays.asList((MutationVariable) temp);
    }

    @Override
    public void mutate(BinaryString parent, MutationParameters mutationParameters) {
        MyBoolean x = ((MyBoolean) mutationParameters.getParam("target"));
        //if(rand.nextDouble()<0.1)

        //x.setValue(!x.getValue());
        x.setValue(true);
    }
}
