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
        writer.print(" ) ")
        writer.println()
    }

}
