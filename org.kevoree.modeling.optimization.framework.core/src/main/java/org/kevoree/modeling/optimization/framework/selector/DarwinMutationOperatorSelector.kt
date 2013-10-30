package org.kevoree.modeling.optimization.engine.genetic.selector

import org.kevoree.modeling.optimization.api.mutation.MutationOperatorSelector
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import java.util.Random
import java.util.HashMap
import org.kevoree.modeling.optimization.framework.selector.MutatorRanking
import org.kevoree.modeling.optimization.api.solution.Solution
import org.kevoree.modeling.optimization.api.solution.SolutionMutationListener

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 28/10/2013
 * Time: 18:29
 */
public class DarwinMutationOperatorSelector<A : KMFContainer>(override val operators: List<MutationOperator<A>>, val probability: Double) : MutationOperatorSelector<A>, SolutionMutationListener<A> {

    val random = Random()
    val ranking = HashMap<String, HashMap<MutationOperator<A>, MutatorRanking<A>>>()
    val bestRanks = HashMap<String, MutationOperator<A>>()
    val bestRanking = HashMap<String, MutatorRanking<A>>()

    override fun process(previousSolution: Solution<A>, solution: Solution<A>) {
        for(fitness in solution.getFitnesses()){
            val currentScore = solution.getScoreForFitness(fitness)!!
            val previousScore = previousSolution.getScoreForFitness(fitness)!!
            val impactOnFitness = previousScore - currentScore
            val lastOperator = solution.context.operator!!
            val rankinValues: HashMap<MutationOperator<A>, MutatorRanking<A>> = ranking.getOrPut(fitness, {
                HashMap<MutationOperator<A>, MutatorRanking<A>>()
            })
            val currentRanking: MutatorRanking<A> = rankinValues.getOrPut(lastOperator, {
                MutatorRanking<A>(0.0, 0.0,0.0, 0.0, 0)
            })

            //println(lastOperator.javaClass.getSimpleName()+"->"+impactOnFitness+"("+previousScore+"->"+currentScore+")")

            if(impactOnFitness <= 0){
                currentRanking.negativeSum += impactOnFitness
            } else {
                currentRanking.positiveSum += impactOnFitness
            }
            currentRanking.nbSelection += 1
            currentRanking.positiveMean = currentRanking.positiveSum / currentRanking.nbSelection
            currentRanking.negativeMean = currentRanking.negativeSum / currentRanking.nbSelection

            //update best score
            val bestFoundedRanking = bestRanking.get(fitness)
            if(bestFoundedRanking == null){
                bestRanks.put(fitness, lastOperator)
                bestRanking.put(fitness, currentRanking)
            } else {
                if(bestFoundedRanking.positiveMean < currentRanking.positiveMean){
                    bestRanks.put(fitness, lastOperator)
                    bestRanking.put(fitness, currentRanking)
                }
            }
        }

    }

    override fun select(solution: Solution<A>): MutationOperator<A> {
        val randomNb = random.nextInt(100)
        if(randomNb < probability){
            val worstFitnessToFix = selectWorstCurrentFitness(solution)
            val currentBestOperator = bestRanks.get(worstFitnessToFix)
            if(currentBestOperator == null){
                //random
                var indice = random.nextInt(operators.size())
                return operators.get(indice)
            } else {
                return currentBestOperator
            }
        } else {
            //random selection
            var indice = random.nextInt(operators.size())
            return operators.get(indice)
        }
    }

    private fun selectWorstCurrentFitness(solution: Solution<A>): String {
        var worstScore = 0.0
        var worstName: String = ""
        for(fitness in solution.getFitnesses()){
            val loopScore = solution.getScoreForFitness(fitness)!!
            if(worstScore < solution.getScoreForFitness(fitness)!!){
                worstName = fitness
                worstScore = loopScore
            }
        }
        return worstName
    }

    public fun toString(): String {
        val buffer = StringBuffer()
        buffer.append("DarwinMutationOperatorSelector\n")
        buffer.append("Best Current Ranking\n")
        for(rank in bestRanks){
            buffer.append("fitness ")
            buffer.append(rank.key)
            buffer.append(" -> ")
            buffer.append(rank.value.javaClass.getSimpleName())
            buffer.append("\n")
        }
        buffer.append("Full ranking report\n")
        for(rank in ranking){
            buffer.append("fitness ")
            buffer.append(rank.key)
            buffer.append("\n")
            for(mut in rank.getValue()){
                buffer.append("mutator ")
                buffer.append(mut.getKey().javaClass.getSimpleName())
                buffer.append(" -> ")
                val value = mut.getValue()
                buffer.append("(positiveMean=")
                buffer.append(value.positiveMean)
                buffer.append("(negativeMean=")
                buffer.append(value.negativeMean)
                buffer.append(",positiveSum=")
                buffer.append(value.positiveSum)
                buffer.append(",negativeSum=")
                buffer.append(value.negativeSum)
                buffer.append(",nbSelection=")
                buffer.append(value.nbSelection)
                buffer.append(")\n")
            }
        }
        return buffer.toString()
    }

}

