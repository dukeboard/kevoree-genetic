package org.kevoree.modeling.genetic.genetictest.mutators;


import org.genetictest.BinaryString;
import org.genetictest.MyBoolean;
import org.genetictest.impl.DefaultGeneticTestFactory;
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
public class AddBoolMutator implements MutationOperator<BinaryString> {

    private Random rand = new Random();
    private static DefaultGeneticTestFactory binaryfactory = new DefaultGeneticTestFactory();

   @Override
    public List<MutationVariable> enumerateVariables(BinaryString bs) {

       return Arrays.asList((MutationVariable) new QueryVar("target", "*"));
    }

    @Override
    public void mutate(BinaryString parent, MutationParameters mutationParameters) {
        MyBoolean mb = binaryfactory.createMyBoolean();
        parent.addValues(mb);
        System.out.println("Inside mutator "+parent.getValues().size());
    }
}
