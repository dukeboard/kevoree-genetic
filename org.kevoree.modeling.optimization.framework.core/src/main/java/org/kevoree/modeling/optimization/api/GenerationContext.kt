package org.kevoree.modeling.optimization.api

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.api.trace.TraceSequence
import org.kevoree.modeling.api.compare.ModelCompare
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import java.util.ArrayList

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
        var operator: MutationOperator<A>?,
        val context: Context) {

    fun createChild(modelCompare: ModelCompare, newModel: A, traceAware: Boolean): GenerationContext<A> {
        var nexTrace = if (traceAware) {
            TraceSequence(modelCompare.factory)
        } else {
            null
        }
        val child = GenerationContext(this, modelOrigin, newModel, nexTrace, operator,context);
        return child;
    }

    fun createOperatorsStack(): List<MutationOperator<A>> {
        val stack = ArrayList<MutationOperator<A>>()
        populateOperatorsStack(this, stack)
        return stack
    }

    private fun populateOperatorsStack(current: GenerationContext<A>, stack: MutableList<MutationOperator<A>>) {
        val cop = current.operator
        if (cop != null) {
            stack.add(cop)
        }
        if (current.parentContext != null) {
            populateOperatorsStack(current.parentContext, stack)
        }
    }

}