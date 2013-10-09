package org.kevoree.modeling.genetic.tinycloud.mutators;

import org.cloud.Cloud;
import org.cloud.Node;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.MutationVariable;
import org.kevoree.modeling.optimization.api.mutation.QueryVar;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:02
 */
public class RemoveNodeMutator implements MutationOperator<Cloud> {

    @Override
    public List<MutationVariable> enumerateVariables(Cloud cloud) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "nodes[*]"));
    }

    @Override
    public void mutate(Cloud model, MutationParameters mutationParameters) {
        Node target = (Node) mutationParameters.getParam("target");
        if (target != null) {
            model.removeNodes(target);
        }
    }

}
