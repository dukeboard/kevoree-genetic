package org.kevoree.modeling.optimization.framework

import org.kevoree.modeling.api.KMFContainer
import java.io.PrintStream
import org.kevoree.modeling.optimization.api.solution.Solution

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 04/09/13
 * Time: 18:59
 */

public object SolutionPrinter {
    public fun print(solution: Solution<*>, writer: PrintStream) {
        writer.print("Solution( ")
        var isFirst: Boolean = true
        for (fitness in solution.getFitnesses())
        {
            if (!isFirst)
            {
                writer.print(" , ")
            }
            writer.print(fitness.javaClass.getSimpleName())
            writer.print("=")
            writer.print(solution.getScoreForFitness(fitness))
            isFirst = false
        }
        writer.print(" )")
        writer.println()
        printModelStructure(solution, writer)
        printOperatorsStack(solution, writer)
    }

    public fun printOperatorsStack(solution: Solution<*>, writer: PrintStream) {
        writer.println()
        val stack = solution.context.createOperatorsStack()
        writer.append("operators stack / last=" + solution.context?.operator?.javaClass?.getSimpleName() + "- (")
        for(op in stack){
            writer.append("," + op.javaClass.getName())
        }
        writer.append(")")
        writer.println()
    }

    public fun printModelStructure(solution: Solution<*>, writer: PrintStream) {
        val attPrinter = object : org.kevoree.modeling.api.util.ModelAttributeVisitor{
            override fun visit(value: Any?, name: String, parent: KMFContainer) {
                writer.print(name)
                writer.print("=")
                writer.print(value)
                writer.print(",")
            }
        }
        var i = 0;
        solution.model.visit(object : org.kevoree.modeling.api.util.ModelVisitor(){
            override fun beginVisitElem(elem: KMFContainer) {
                i++
                for(r in 0..1){
                    writer.print(" ")
                }
                writer.print(elem.metaClassName() + " (")
                elem.visitAttributes(attPrinter)
                writer.print(")")
                writer.println()
            }
            override fun endVisitElem(elem: org.kevoree.modeling.api.KMFContainer) {
                i--
            }
            override fun visit(elem: KMFContainer, refNameInParent: String, parent: KMFContainer) {
            }
        }, true, true, false)
    }

}
