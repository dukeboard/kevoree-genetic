package org.kevoree.genetic.framework;

import org.kevoree.modeling.api.KMFContainer;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 27/03/13
 * Time: 14:42
 */
public abstract class KevoreeOriginAwareFitnessFunction implements KevoreeFitnessFunction {

    @Override
    public double evaluate(KMFContainer model) {
        return 0;
    }

    public abstract double evaluate(KMFContainer model,KMFContainer origin);

}
