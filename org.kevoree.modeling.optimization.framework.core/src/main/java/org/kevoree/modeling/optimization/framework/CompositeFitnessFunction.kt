package org.kevoree.modeling.optimization.framework;

import org.kevoree.modeling.api.KMFContainer
import java.util.ArrayList
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.api.GenerationContext
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 15/03/13
 * Time: 16:20
 */
public class CompositeFitnessFunction<A : KMFContainer> : FitnessFunction<A> {

    var minValue: Double = java.lang.Double.MAX_VALUE

    var maxValue: Double = java.lang.Double.MIN_VALUE

    var orientationValue: FitnessOrientation = FitnessOrientation.MINIMIZE

    override fun max(): Double {
        return maxValue
    }
    override fun min(): Double {
        return minValue
    }
    override fun orientation(): FitnessOrientation {
        return orientationValue
    }

    override fun evaluate(model: A, context: GenerationContext<A>): Double {
        var value = 0.0;
        for (fit in fitnesses) {
            value = value + fit.evaluate(model, context);
        }
        return value / fitnesses.size();
    }

    val fitnesses = ArrayList<FitnessFunction<A>>();

    public fun addFitness(newfit: FitnessFunction<A>): CompositeFitnessFunction<A> {
        if(fitnesses.isEmpty()){
            orientationValue = newfit.orientation()
        } else {
            if(newfit.orientation() != orientationValue){
                System.err.println("Unconsistancy in composite fitness all fitness must have the same orientation")
                throw Exception("Unconsistancy composite fitness errror")
            }
        }
        val currentFitMin = newfit.min();
        if(currentFitMin < minValue){
            minValue = currentFitMin
        }
        val currentFitMax = newfit.max();
        if(currentFitMax > maxValue){
            maxValue = currentFitMax
        }
        fitnesses.add(newfit);
        return this;
    }

    public fun getFitnesses(): List<FitnessFunction<A>> {
        return fitnesses;
    }

}
