package org.kevoree.genetic.framework;

import org.kevoree.modeling.api.KMFContainer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 15:30
 */
public interface KevoreeCrossOverOperator extends KevoreeOperator {

    public KMFContainer cross(KMFContainer parent, KMFContainer parent2);

}
