package org.kevoree.modeling.genetic.api;

import org.kevoree.genetic.framework.GeneticEngine;
import org.kevoree.genetic.framework.KevoreeSolution;

import java.util.List;

/**
 * Created by duke on 07/08/13.
 */
public interface ResolutionEngine {

    public GeneticEngine addOperator(MutationOperator operator);

    public GeneticEngine addFitnessFuntion(FitnessFunction function);

    public GeneticEngine setPopulationFactory(PopulationFactory _populationFactory);

    public GeneticEngine setMaxGeneration(Integer maxGeneration);

    public GeneticEngine setMaxTime(Long maxTime);

    public List<KevoreeSolution> solve() throws Exception;

}
