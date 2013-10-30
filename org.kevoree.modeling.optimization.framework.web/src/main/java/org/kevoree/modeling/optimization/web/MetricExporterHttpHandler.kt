package org.kevoree.modeling.optimization.web

import org.webbitserver.HttpHandler
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel
import org.kevoree.modeling.optimization.executionmodel.Step
import org.kevoree.modeling.optimization.executionmodel.Metric
import java.util.ArrayList
import java.util.Comparator
import java.util.Collections
import org.kevoree.modeling.optimization.executionmodel.FitnessMetric
import org.webbitserver.HttpRequest
import org.webbitserver.HttpResponse
import org.webbitserver.HttpControl
import org.kevoree.modeling.optimization.executionmodel.Run
import org.kevoree.log.Log

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 29/10/2013
 * Time: 14:33
 */


public class MetricExporterHttpHandler(val model: ExecutionModel) : HttpHandler {

    val fieldSeperator = ","
    val lineSeparator = "\n"

    override fun handleHttpRequest(p0: HttpRequest?, p1: HttpResponse?, p2: HttpControl?) {
        p1?.content(exportMetrics(model))
        p1?.end();
    }

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

    private fun exportMetrics(model: ExecutionModel): String {
        if(model.runs.empty){
            Log.error("Run is empty, no CSV generated")
            return ""
        }
        //check run consistency
        var previousSize: Int? = null
        for(run in model.runs){
            if(previousSize != null && run.steps.size() != previousSize){
                Log.error("Unconsistant Run , const merge it in one CSV")
                return ""
            }
            previousSize = run.steps.size()
        }

        val writer = StringBuilder()
        writer.append("generation")
        for(run in model.runs){
            val step0 = run.steps.get(0)
            for(metric in sortedMetrics(step0)){
                writer.append(fieldSeperator)
                val hash = generateHashByMetric(metric)
                writer.append(run.algName + "_" + hash.substring(hash.lastIndexOf('.')+1))
                if(metric is org.kevoree.modeling.optimization.executionmodel.FitnessMetric){
                    val fitmet = metric as FitnessMetric
                    writer.append("_" + fitmet.fitness!!.name)
                } else {
                    writer.append("_pareto")
                }
            }
        }
        val sortedStepRun0 = sortedStep(model.runs.get(0))
        for(loopStep in 0..previousSize!!-1){
            writer.append(lineSeparator)
            val currentGenerationNumber = sortedStepRun0.get(loopStep).generationNumber
            writer.append(currentGenerationNumber.toString())
            for(runLoop in 0..model.runs.size()-1){
                val currentStep = sortedStep(model.runs.get(runLoop)).get(loopStep)
                if(currentStep.generationNumber != currentGenerationNumber){
                    Log.error("Unconsistant error")
                    return ""
                }
                for(metric in sortedMetrics(currentStep)){
                    writer.append(fieldSeperator)
                    writer.append(metric.value.toString())
                }
            }
        }
        /*
        for(run in model.runs){
            if(run.steps.size() > 0){
                //print line headers
                writer.append("generation")
                writer.append(fieldSeperator)
                val step0 = run.steps.get(0)
                for(metric in sortedMetrics(step0)){
                    val hash = generateHashByMetric(metric)
                    writer.append(hash.substring(hash.lastIndexOf('.')))
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
                    for(metric in sortedMetrics(step)){
                        writer.append(metric.value.toString())
                        writer.append(fieldSeperator)
                    }
                    writer.append(lineSeparator)
                }
            }
        }*/
        return writer.toString()
    }

}