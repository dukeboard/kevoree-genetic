package org.kevoree.modeling.optimization.executionmodel.qualityIndicator;

import org.kevoree.modeling.optimization.executionmodel.qualityIndicator.util.LexicoGraphicalComparator;
import org.kevoree.modeling.optimization.executionmodel.qualityIndicator.util.MetricsUtil;
import org.kevoree.modeling.optimization.executionmodel.qualityIndicator.util.ValueComparator;

import java.util.Arrays;

/**
 * This class implements the generalized spread metric for two or more dimensions.
 * It can be used also as command line program just by typing.
 * $ java GeneralizedSpread <solutionFrontFile> <trueFrontFile> <numberOfObjectives>
 * It is recommendable to see the metric description.
 * Reference: A. Zhou, Y. Jin, Q. Zhang, B. Sendhoff, and E. Tsang
 *           Combining model-based and genetics-based offspring generation for
 *           multi-objective optimization using a convergence criterion,
 *           2006 IEEE Congress on Evolutionary Computation, 2006, pp. 3234-3241.
 */
public class GeneralizedSpread {

    static MetricsUtil utils_;  // MetricsUtil provides some
    // utilities for implementing
    // the metric

    /**
     * Constructor
     * Creates a new instance of GeneralizedSpread
     */
    public GeneralizedSpread() {
        utils_ = new MetricsUtil();
    } // GeneralizedSpread

    private static GeneralizedSpread INSTANCE = null;
    public static GeneralizedSpread getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new GeneralizedSpread();
        }
        return INSTANCE;
    }

    /**
     *  Calculates the generalized spread metric. Given the
     *  pareto front, the true pareto front as <code>double []</code>
     *  and the number of objectives, the method return the value for the
     *  metric.
     *  @param paretoFront The pareto front.
     *  @param paretoTrueFront The true pareto front.
     *  @param numberOfObjectives The number of objectives.
     *  @return the value of the generalized spread metric
     **/
    public double generalizedSpread(double [][] paretoFront,
                                    double [][] paretoTrueFront,
                                    int numberOfObjectives) {

        /**
         * Stores the maximum values of true pareto front.
         */
        double [] maximumValue;

        /**
         * Stores the minimum values of the true pareto front.
         */
        double [] minimumValue;

        /**
         * Stores the normalized front.
         */
        double [][] normalizedFront;

        /**
         * Stores the normalized true Pareto front.
         */
        double [][] normalizedParetoFront;

        // STEP 1. Obtain the maximum and minimum values of the Pareto front
        maximumValue = utils_.getMaximumValues(paretoTrueFront,numberOfObjectives);
        minimumValue = utils_.getMinimumValues(paretoTrueFront,numberOfObjectives);

        normalizedFront = utils_.getNormalizedFront(paretoFront,
                maximumValue,
                minimumValue);

        // STEP 2. Get the normalized front and true Pareto fronts
        normalizedParetoFront = utils_.getNormalizedFront(paretoTrueFront,
                maximumValue,
                minimumValue);

        // STEP 3. Find extremal values
        double [][] extremValues = new double[numberOfObjectives][numberOfObjectives];
        for (int i = 0; i < numberOfObjectives; i++) {
            Arrays.sort(normalizedParetoFront, new ValueComparator(i));
            for (int j = 0; j < numberOfObjectives; j++) {
                extremValues[i][j] = normalizedParetoFront[normalizedParetoFront.length-1][j];
            }
        }

        int numberOfPoints     = normalizedFront.length;
        int numberOfTruePoints = normalizedParetoFront.length;


        // STEP 4. Sorts the normalized front
        Arrays.sort(normalizedFront,new LexicoGraphicalComparator());

        // STEP 5. Calculate the metric value. The value is 1.0 by default
        if (utils_.distance(normalizedFront[0],normalizedFront[normalizedFront.length-1])==0.0) {
            return 1.0;
        } else {

            double dmean = 0.0;

            // STEP 6. Calculate the mean distance between each point and its nearest neighbor
            for (int i = 0; i < normalizedFront.length; i++) {
                dmean += utils_.distanceToNearestPoint(normalizedFront[i],normalizedFront);
            }

            dmean = dmean / (numberOfPoints);

            // STEP 7. Calculate the distance to extremal values
            double dExtrems = 0.0;
            for (int i = 0; i < extremValues.length; i++) {
                dExtrems += utils_.distanceToClosedPoint(extremValues[i],normalizedFront);
            }

            // STEP 8. Computing the value of the metric
            double mean = 0.0;
            for (int i = 0; i < normalizedFront.length; i++) {
                mean += Math.abs(utils_.distanceToNearestPoint(normalizedFront[i],normalizedFront) -
                        dmean);
            }

            double value = (dExtrems + mean) / (dExtrems + (numberOfPoints*dmean));
            return value;

        }
    } // generalizedSpread
}
