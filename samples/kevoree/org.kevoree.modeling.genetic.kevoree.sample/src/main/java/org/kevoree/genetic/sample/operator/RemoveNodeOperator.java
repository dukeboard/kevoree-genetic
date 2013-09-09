package org.kevoree.genetic.sample.operator;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.modeling.api.KMFContainer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 26/03/13
 * Time: 19:21
 */
public class RemoveNodeOperator extends AbstractKevoreeOperator {
    @Override
    protected void applyMutation(Object target, ContainerRoot root) {
        if (target instanceof ContainerNode) {
            root.removeNodes((ContainerNode) target);
        }
    }
}
