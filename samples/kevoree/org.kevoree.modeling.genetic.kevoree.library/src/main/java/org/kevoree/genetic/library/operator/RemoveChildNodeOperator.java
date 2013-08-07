package org.kevoree.genetic.library.operator;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.modeling.api.KMFContainer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/03/13
 * Time: 19:33
 */
public class RemoveChildNodeOperator extends AbstractKevoreeOperator {

    @Override
    protected void applyMutation(Object target, KMFContainer root) {
        if (target instanceof ContainerNode && root instanceof ContainerRoot ) {
            ContainerNode targetObject = (ContainerNode) target;
            targetObject.getHost().removeHosts(targetObject);
            ((ContainerRoot)root).removeNodes(targetObject);
        }
    }

}
