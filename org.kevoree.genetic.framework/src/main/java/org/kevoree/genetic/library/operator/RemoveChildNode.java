package org.kevoree.genetic.library.operator;

import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/03/13
 * Time: 19:33
 */
public class RemoveChildNode extends AbstractKevoreeOperator {

    @Override
    protected void applyMutation(Object target, ContainerRoot root) {
        if (target instanceof ContainerNode) {
            ContainerNode targetObject = (ContainerNode) target;
            targetObject.getHost().removeHosts(targetObject);
            root.removeNodes(targetObject);
        }
    }

}
