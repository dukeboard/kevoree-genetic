package org.kevoree.modeling.genetic.democloud.greedy;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/15/13
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */



/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/10/13
 * Time: 9:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleRunnerGreedy {



    public static void main(String[] args) throws Exception {

  /*      GreedyEngine<Cloud> engine = new GreedyEngine<Cloud>();

        //engine.desactivateOriginAware();



        engine.addOperator(new AddNodeMutator());
        engine.addOperator(new RemoveNodeMutator());
        engine.addOperator(new AddSoftwareMutator());

        engine.addOperator(new CloneNodeMutator());

        engine.addOperator(new RemoveSoftwareMutator());
        engine.addOperator(new AddSmartMutator());
        engine.addOperator(new RemoveSmartMutator());

        engine.addFitnessFuntion(new CloudCostFitness());
        engine.addFitnessFuntion(new CloudLatencyFitness());
        engine.addFitnessFuntion(new CloudRedundancyFitness());
        engine.addFitnessFuntion(new CloudSimilarityFitness());
        //engine.addFitnessFuntion(new CloudAdaptationFitness());


        engine.setMaxGeneration(50);
        engine.setPopulationFactory(new CloudPopulationFactory().setSize(10));


        engine.addFitnessMetric(new CloudCostFitness(), ParetoFitnessMetrics.MIN);
        engine.addFitnessMetric(new CloudCostFitness(), ParetoFitnessMetrics.MAX);
        engine.addParetoMetric(ParetoMetrics.MEAN);


        engine.setMaxGeneration(100);
        engine.setPopulationFactory(new CloudPopulationFactory().setSize(10));


        engine.addFitnessMetric(new CloudCostFitness(), ParetoFitnessMetrics.MIN);
        engine.addFitnessMetric(new CloudCostFitness(), ParetoFitnessMetrics.MAX);
        engine.addParetoMetric(ParetoMetrics.HYPERVOLUME);


        List<Solution<Cloud>> result = engine.solve();
        for (Solution sol : result) {
            SolutionPrinter.instance$.print(sol, System.out);
        }


        ExecutionModel model = engine.getExecutionModel();
        ExecutionModelExporter.instance$.exportMetrics(model,new File("results")); */

    }
}
