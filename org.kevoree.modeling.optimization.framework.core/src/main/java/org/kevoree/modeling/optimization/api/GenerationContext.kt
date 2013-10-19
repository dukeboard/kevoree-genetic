package org.kevoree.modeling.optimization.api

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.api.trace.TraceSequence
import org.kevoree.modeling.api.compare.ModelCompare
import org.kevoree.modeling.optimization.api.mutation.MutationOperator

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 09/10/13
 * Time: 14:55
 */

public class GenerationContext<A : KMFContainer>(
        val parentContext: GenerationContext<A>?,
        var modelOrigin: A,
        var currentModel: A,
        val traceSequence: TraceSequence?,
        var operator : MutationOperator<A>?){

    fun createChild(modelCompare: ModelCompare,newModel : A, traceAware : Boolean): GenerationContext<A> {
        var nexTrace = if(traceAware){modelCompare.createSequence()}else{null}
        val child = GenerationContext(this, modelOrigin, newModel, nexTrace,operator);
        return child;
    }

}