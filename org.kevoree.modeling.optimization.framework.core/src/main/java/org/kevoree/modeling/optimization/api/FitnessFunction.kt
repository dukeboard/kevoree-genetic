package org.kevoree.modeling.optimization.api;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.api.trace.TraceSequence

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 12/02/13
 * Time: 14:40
 */
public trait FitnessFunction<A : KMFContainer> {

    public fun evaluate(model: A, origin: A?, traceSeq: TraceSequence?): Double ;

    public fun originAware() : Boolean;

}