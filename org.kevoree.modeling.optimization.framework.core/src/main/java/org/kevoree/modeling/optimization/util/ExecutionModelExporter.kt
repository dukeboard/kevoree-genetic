package org.kevoree.modeling.optimization.util

import java.io.PrintWriter
import java.io.File
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel
import java.io.FileOutputStream
import org.kevoree.modeling.optimization.executionmodel.Run
import org.kevoree.modeling.optimization.executionmodel.FitnessMetric
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import org.kevoree.modeling.optimization.executionmodel.Step
import org.kevoree.modeling.optimization.executionmodel.Metric

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/10/13
 * Time: 13:56
 */

public object ExecutionModelExporter {

    val fieldSeperator = ","
    val lineSeparator = "\n"

    private fun sortedStep(run: Run): List<Step> {
        val steps = ArrayList<Step>(run.steps.size())
        steps.addAll(run.steps)
        Collections.sort(steps, object : Comparator<Step> {
            override fun compare(o1: Step, o2: Step): Int {
                return o1.generationNumber!!.compareTo(o2.generationNumber!!)
            }
        })
        return steps
    }

    private fun generateHashByMetric(met: Metric): String {
        return when(met) {
            is FitnessMetric -> {
                met.metaClassName() + "_" + met.fitness!!.name
            }
            else -> {
                met.metaClassName()
            }
        }
    }

    private fun sortedMetrics(run: Step): List<Metric> {
        val metrics = ArrayList<Metric>(run.metrics.size())
        metrics.addAll(run.metrics)
        Collections.sort(metrics, object : Comparator<Metric> {
            override fun compare(o1: Metric, o2: Metric): Int {
                return generateHashByMetric(o1).compareTo(generateHashByMetric(o2))
            }
        })
        return metrics
    }

    fun exportMetrics(model: ExecutionModel, outdir: File) {
        if(!(outdir.exists() && outdir.isDirectory()) ){
            outdir.mkdirs()
        }
        for(run in model.runs){
            if(run.steps.size() > 0){
                val file = File(outdir, "Run_metrics_" + run.algName + "_" + run.startTime + "_" + run.endTime + ".csv")
                val fileOutputStream = FileOutputStream(file)
                val writer = PrintWriter(fileOutputStream)
                //print line headers
                writer.append("generation")
                writer.append(fieldSeperator)
                writer.append("startTime")
                writer.append(fieldSeperator)
                writer.append("endTime")
                writer.append(fieldSeperator)
                val step0 = run.steps.get(0)
                for(metric in sortedMetrics(step0)){
                    writer.append(generateHashByMetric(metric))
                    if(metric is org.kevoree.modeling.optimization.executionmodel.FitnessMetric){
                        val fitmet = metric as FitnessMetric
                        writer.append("_" + fitmet.fitness!!.name)
                    } else {
                        writer.append("_pareto")
                    }
                    writer.append(fieldSeperator)
                }
                writer.append(lineSeparator)
                //print metrics values
                for(step in sortedStep(run)){
                    writer.append(step.generationNumber.toString())
                    writer.append(fieldSeperator)
                    writer.append(step.startTime.toString())
                    writer.append(fieldSeperator)
                    writer.append(step.endTime.toString())
                    writer.append(fieldSeperator)
                    for(metric in sortedMetrics(step)){
                        writer.append(metric.value.toString())
                        writer.append(fieldSeperator)
                    }
                    writer.append(lineSeparator)
                }
                writer.flush()
                writer.close()
            }
        }
    }

    fun exportRawSolutions(model: ExecutionModel, outdir: File) {

        if(!(outdir.exists() && outdir.isDirectory()) ){
            outdir.mkdirs()
        }

        for(run in model.runs){
            if(run.steps.size() > 0){
                val file = File(outdir, "Run_solutions_" + run.algName + "_" + run.startTime + "_" + run.endTime + ".csv")
                val fileOutputStream = FileOutputStream(file)
                val writer = PrintWriter(fileOutputStream)
                //print line headers
                writer.append("generation")
                writer.append(fieldSeperator)
                writer.append("solution")
                writer.append(fieldSeperator)
                writer.append("startTime")
                writer.append(fieldSeperator)
                writer.append("endTime")
                writer.append(fieldSeperator)

                for(fit in model.fitness){
                    writer.append(fit.name)
                    writer.append(fieldSeperator)
                }

                writer.append(lineSeparator)
                //print metrics values
                for(step in sortedStep(run)){
                    var i = 0;
                    for(solution in step.solutions){
                        writer.append(step.generationNumber.toString())
                        writer.append(fieldSeperator)
                        writer.append(i.toString())
                        writer.append(fieldSeperator)
                        writer.append(step.startTime.toString())
                        writer.append(fieldSeperator)
                        writer.append(step.endTime.toString())
                        writer.append(fieldSeperator)
                        for(fit in model.fitness){
                            var score = solution.scores.find { it.fitness == fit }
                            writer.append(score!!.value.toString())
                            writer.append(fieldSeperator)
                        }
                        writer.append(lineSeparator)
                        i++;
                    }
                }
                writer.flush()
                writer.close()
            }
        }
    }


}
