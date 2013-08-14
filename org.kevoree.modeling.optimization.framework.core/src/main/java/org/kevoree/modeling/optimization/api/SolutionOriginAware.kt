package org.kevoree.modeling.optimization.api;

import org.kevoree.modeling.api.KMFContainer;
import org.kevoree.modeling.api.trace.TraceSequence;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 13/08/13
 * Time: 17:16
 */
public trait SolutionOriginAware : Solution {

    public fun getOriginModel() : KMFContainer

    public fun getTraces() : TraceSequence

}
