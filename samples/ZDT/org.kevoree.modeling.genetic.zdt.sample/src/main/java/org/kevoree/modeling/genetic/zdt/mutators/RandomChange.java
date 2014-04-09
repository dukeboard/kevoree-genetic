package org.kevoree.modeling.genetic.zdt.mutators;

import jet.runtime.typeinfo.JetValueParameter;
import org.jetbrains.annotations.NotNull;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.MutationVariable;
import org.kevoree.modeling.optimization.api.mutation.QueryVar;
import org.zdt.Dlist;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by assaad.moawad on 4/9/2014.
 */
public class RandomChange implements MutationOperator<Dlist> {

    private Random random = new Random();

    @NotNull
    @Override
    public List<MutationVariable> enumerateVariables(@NotNull @JetValueParameter(name = "model") Dlist model) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "nodes[*]"));
    }

    @NotNull
    @Override
    public void mutate(@NotNull @JetValueParameter(name = "model") Dlist model, @NotNull @JetValueParameter(name = "params") MutationParameters params) {

        int x= random.nextInt(model.getValues().size());
        model.getValues().get(x).setDoubleValue(random.nextDouble());

    }
}
