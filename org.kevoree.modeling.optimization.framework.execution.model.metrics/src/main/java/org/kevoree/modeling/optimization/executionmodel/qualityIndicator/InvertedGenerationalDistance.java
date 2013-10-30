package org.kevoree.modeling.optimization.executionmodel.qualityIndicator;

import org.kevoree.modeling.optimization.executionmodel.qualityIndicator.util.MetricsUtil;

/**
 * This class implements the inverted generational distance metric.
 * It can be used also as a command line by typing:
 * "java InvertedGenerationalDistance <solutionFrontFile> <trueFrontFile>
 * <numberOfObjectives>"
 * Reference: Van Veldhuizen, D.A., Lamont, G.B.: Multiobjective Evolutionary
 *            Algorithm Research: A History and Analysis.
 *            Technical Report TR-98-03, Dept. Elec. Comput. Eng., Air Force
 *            Inst. Technol. (1998)
 */
public class InvertedGenerationalDistance {

    private static InvertedGenerationalDistance INSTANCE = null;
    public static InvertedGenerationalDistance getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new InvertedGenerationalDistance();
        }
        return INSTANCE;
    }

    MetricsUtil utils_;  //utils_ is used to access to the
    //MetricsUtil funcionalities

    static final double pow_ = 2.0;          //pow. This is the pow used for the
    //distances

    /**
     * Constructor.
     * Creates a new instance of the generational distance metric.
     */
    public InvertedGenerationalDistance() {
        utils_ = new MetricsUtil();
    } // GenerationalDistance

    /**
     * Returns the inverted generational distance value for a given front
     * @param front The front
     * @param trueParetoFront The true pareto front
     */
    public double invertedGenerationalDistance(double [][] front,
                                               double [][] trueParetoFront,
                                               int numberOfObjectives) {

        /**
         * Stores the maximum values of true pareto front.
         */
        double [] maximumValue ;

        /**
         * Stores the minimum values of the true pareto front.
         */
        double [] minimumValue ;

        /**
         * Stores the normalized front.
         */
        double [][] normalizedFront ;

        /**
         * Stores the normalized true Pareto front.
         */
        double [][] normalizedParetoFront ;

        // STEP 1. Obtain the maximum and minimum values of the Pareto front
        maximumValue = utils_.getMaximumValues(trueParetoFront, numberOfObjectives);
        minimumValue = utils_.getMinimumValues(trueParetoFront, numberOfObjectives);

        // STEP 2. Get the normalized front and true Pareto fronts
        normalizedFront       = utils_.getNormalizedFront(front,
                maximumValue,
                minimumValue);
        normalizedParetoFront = utils_.getNormalizedFront(trueParetoFront,
                maximumValue,
                minimumValue);

        // STEP 3. Sum the distances between each point of the true Pareto front and
        // the nearest point in the true Pareto front
        double sum = 0.0;
        for (int i = 0; i < normalizedParetoFront.length; i++)
            sum += Math.pow(utils_.distanceToClosedPoint(normalizedParetoFront[i],
                    normalizedFront),
                    pow_);


        // STEP 4. Obtain the sqrt of the sum
        sum = Math.pow(sum,1.0/pow_);

        // STEP 5. Divide the sum by the maximum number of points of the front
        double generationalDistance = sum / normalizedParetoFront.length;

        return generationalDistance;
    } // generationalDistance

}
