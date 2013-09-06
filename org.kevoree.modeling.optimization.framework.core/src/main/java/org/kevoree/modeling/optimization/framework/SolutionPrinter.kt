package org.kevoree.modeling.optimization.framework

import org.kevoree.modeling.api.KMFContainer
import java.io.PrintStream
import org.kevoree.modeling.optimization.api.Solution

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 04/09/13
 * Time: 18:59
 */

public class SolutionPrinter {
    public fun print(solution: Solution, writer: PrintStream) {
        writer.print("Solution( ")
        var isFirst: Boolean = true
        for (name : String in solution.getFitnesses())
        {
            if (!isFirst)
            {
                writer.print(" , ")
            }
            writer.print(name)
            writer.print("=")
            writer.print(solution.getScoreForFitness(name))
            isFirst = false
        }
        writer.print(" ) / " + (solution.getFitnesses()))
        writer.println()
    }

}
