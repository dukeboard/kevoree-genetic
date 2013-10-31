package org.kevoree.modeling.optimization.framework

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.api.trace.TraceSequence
import org.kevoree.modeling.optimization.api.Rule
import java.util.ArrayList
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction
import org.kevoree.modeling.optimization.api.GenerationContext
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/10/13
 * Time: 15:40
 */

public class RulesFitnessFunction<A : KMFContainer> : FitnessFunction<A> {

    override fun max(): Double {
        return rules.size.toDouble()
    }
    override fun min(): Double {
        return 0.0;
    }
    override fun orientation(): FitnessOrientation {
       return FitnessOrientation.MINIMIZE
    }

    var rules: MutableList<Rule<A>> = ArrayList<Rule<A>>()

    public fun addRule(rule: Rule<A>): RulesFitnessFunction<A> {
        rules.add(rule);
        return this;
    }

    override fun evaluate(model: A, context : GenerationContext<A>): Double {
        var nbViolatedRules = 0.0
        for(rule in rules){
            if(!rule.check(model)){
                nbViolatedRules++
            }
        }
        return nbViolatedRules;
        //val pres = ( (rules.size() - nbViolatedRules) / rules.size()) * 100;
        //return 100 - Math.min(Math.max(0.toDouble(), pres.toDouble()), 100.toDouble())
    }

}