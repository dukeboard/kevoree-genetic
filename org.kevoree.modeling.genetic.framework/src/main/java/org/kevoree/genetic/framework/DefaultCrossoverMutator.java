package org.kevoree.genetic.framework;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.api.trace.TraceSequence;
import org.kevoree.modeling.genetic.api.CrossoverOperator;

/**
 * Created by duke on 07/08/13.
 */
public class DefaultCrossoverMutator implements CrossoverOperator {

    @Override
    public KMFContainer cross(KMFContainer parentOne, TraceSequence traceOne, KMFContainer parentTwo, TraceSequence traceTwo) {
        return null;
    }

}
