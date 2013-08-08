package org.kevoree.modeling.genetic.api;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.api.trace.TraceSequence;

/**
 * Created by duke on 07/08/13.
 */
public interface CrossoverOperator<A extends KMFContainer> {

    public A cross(A parentOne,TraceSequence traceOne,A parentTwo,TraceSequence traceTwo);

}
