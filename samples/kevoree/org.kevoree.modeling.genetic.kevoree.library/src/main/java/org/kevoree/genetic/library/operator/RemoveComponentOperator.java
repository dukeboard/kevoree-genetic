package org.kevoree.genetic.library.operator;

import org.kevoree.*;
import org.kevoree.modeling.api.KMFContainer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/03/13
 * Time: 13:48
 */
public class RemoveComponentOperator extends AbstractKevoreeOperator {
    @Override
    protected void applyMutation(Object target, KMFContainer root_) {

        ContainerRoot root = (ContainerRoot) root_;

        if (target instanceof ComponentInstance) {
            ComponentInstance targetObject = (ComponentInstance) target;
            Object targetParent = targetObject.eContainer();
            if (targetParent instanceof ContainerNode) {
                ((ContainerNode) targetParent).removeComponents(targetObject);
                /* CLEANUP BINDING TO THIS INSTANCE  */
                for (Port p : targetObject.getProvided()) {
                    for (MBinding mb : p.getBindings()) {
                        root.removeMBindings(mb);
                    }
                    p.removeAllBindings();
                }
                for (Port p : targetObject.getRequired()) {
                    for (MBinding mb : p.getBindings()) {
                        root.removeMBindings(mb);
                    }
                    p.removeAllBindings();
                }
            }
        }
    }
}
