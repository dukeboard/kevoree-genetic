package org.kevoree.modeling.optimization.engine.genetic.selector

import org.kevoree.modeling.optimization.api.mutation.MutationOperatorSelector
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.modeling.optimization.api.mutation.MutationOperator
import java.util.Random
import java.util.HashMap
import org.kevoree.modeling.optimization.framework.selector.MutatorRanking
import org.kevoree.modeling.optimization.api.solution.Solution
import org.kevoree.modeling.optimization.api.solution.SolutionMutationListener
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 28/10/2013
 * Time: 18:29
 */
public class DarwinMutationOperatorSelector<A : KMFContainer>(override val operators: List<MutationOperator<A>>, val probability: Double) : MutationOperatorSelector<A>, SolutionMutationListener<A> {

    val random = Random()
    val ranking = HashMap<FitnessFunction<A>, HashMap<MutationOperator<A>, MutatorRanking<A>>>()
    val positiveRanking = HashMap<FitnessFunction<A>, HashMap<MutationOperator<A>, MutatorRanking<A>>>()

    private fun updateSelectionProbability(fitness: FitnessFunction<A>) {
        val fitnessRanks = positiveRanking.get(fitness)
        if(fitnessRanks != null && !fitnessRanks.isEmpty()){
            var bestRanking: MutatorRanking<A>? = null
            for (r in fitnessRanks.values()) {
                if (bestRanking == null || r.positiveMean > bestRanking!!.positiveSum){
                    bestRanking = r
                    r.selectionProbability = 1.0
                } else {
                    r.selectionProbability = 0.0
                }
            }
        }
    }

    override fun process(previousSolution: Solution<A>, solution: Solution<A>) {
        for(fitness in solution.getFitnesses()){
            val currentScore = solution.getScoreForFitness(fitness)!!
            val previousScore = previousSolution.getScoreForFitness(fitness)!!
            val impactOnFitness = previousScore - currentScore
            val lastOperator = solution.context.operator!!
            val rankinValues: HashMap<MutationOperator<A>, MutatorRanking<A>> = ranking.getOrPut(fitness, {
                HashMap<MutationOperator<A>, MutatorRanking<A>>()
            })
            val positiveRankingValues: HashMap<MutationOperator<A>, MutatorRanking<A>> = positiveRanking.getOrPut(fitness, {
                HashMap<MutationOperator<A>, MutatorRanking<A>>()
            })
            val currentRanking: MutatorRanking<A> = rankinValues.getOrPut(lastOperator, {
                MutatorRanking<A>(0.0, 0.0, 0.0, 0.0, 0, 0.0)
            })
            if(impactOnFitness > 0 && currentRanking.positiveSum == 0.0){
                positiveRankingValues.put(lastOperator, currentRanking)
            }
            if(impactOnFitness <= 0){
                currentRanking.negativeSum += impactOnFitness
            } else {
                currentRanking.positiveSum += impactOnFitness
            }
            currentRanking.nbSelection += 1
            currentRanking.positiveMean = currentRanking.positiveSum / currentRanking.nbSelection
            currentRanking.negativeMean = currentRanking.negativeSum / currentRanking.nbSelection
            updateSelectionProbability(fitness)
        }
    }

    override fun select(solution: Solution<A>): MutationOperator<A> {
        val randomNb = random.nextInt(100)
        if(randomNb < probability){
            val worstFitnessToFix = selectWorstCurrentFitness(solution)
            val potentialRanks = positiveRanking.get(worstFitnessToFix)
            if(potentialRanks == null){
                return randomSelector()
            }
            var indiceBegin = 0.0
            var indice = random.nextDouble()
            for(rank in potentialRanks){
                val loopRank = rank.getValue()
                if(rank.getValue().positiveMean > 0 && loopRank.selectionProbability > 0){
                    val normalizedProba = indiceBegin + loopRank.selectionProbability
                    if(indice >= indiceBegin && indice < normalizedProba){
                        return rank.getKey()
                    }
                    indiceBegin += loopRank.selectionProbability //move lower boundary
                }
            }
            return randomSelector();
        } else {
            //random selection
            var indice = random.nextInt(operators.size())
            return operators.get(indice)
        }
    }

    private fun randomSelector(): MutationOperator<A> {
        var indice = random.nextInt(operators.size())
        return operators.get(indice)
    }

    private fun selectWorstCurrentFitness(solution: Solution<A>): FitnessFunction<A> {
        var worstScore = 0.0
        var worst: FitnessFunction<A>? = null
        for(fitness in solution.getFitnesses()){
            val loopScore = solution.getScoreForFitness(fitness)!!
            if(worst == null || worstScore <= solution.getScoreForFitness(fitness)!!){
                worst = fitness
                worstScore = loopScore
            }
        }
        return worst!!
    }

    public fun toString(): String {
        val buffer = StringBuffer()
        buffer.append("DarwinMutationOperatorSelector\n")
        buffer.append("Best Current Ranking\n")
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
                buffer.append("(selectionProbability=")
                buffer.append(value.selectionProbability)
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

