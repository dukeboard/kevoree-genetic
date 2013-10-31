package org.kevoree.modeling.optimization.executionmodel.qualityIndicator.util;

/**
 * This class provides some facilities for metrics.
 **/
public class MetricsUtil {

    public static double[][] buildKMFGenTruePareto(int noObjectives){
        double [][] truePareto = new double[2][];
        double [] worst = new double[noObjectives];
        for(int i=0;i<noObjectives;i++){
            worst[i] = 1;
        }
        double [] best = new double[noObjectives];
        for(int i=0;i<noObjectives;i++){
            best[i] = 0;
        }
        truePareto[0] = worst;
        truePareto[1] = best;
        return truePareto;
    }


    /** Gets the maximun values for each objectives in a given pareto
     *  front
     *  @param front The pareto front
     *  @param noObjectives Number of objectives in the pareto front
     *  @return double [] An array of noOjectives values whit the maximun values
     *  for each objective
     **/
    public double [] getMaximumValues(double [][] front, int noObjectives) {
        double [] maximumValue = new double[noObjectives];
        for (int i = 0; i < noObjectives; i++)
            maximumValue[i] =  Double.NEGATIVE_INFINITY;


        for (int i =0; i < front.length;i++ ) {
            for (int j = 0; j < front[i].length; j++) {
                if (front[i][j] > maximumValue[j])
                    maximumValue[j] = front[i][j];
            }
        }

        return maximumValue;
    } // getMaximumValues


    /** Gets the minimun values for each objectives in a given pareto
     *  front
     *  @param front The pareto front
     *  @param noObjectives Number of objectives in the pareto front
     *  @return double [] An array of noOjectives values whit the minimum values
     *  for each objective
     **/
    public double [] getMinimumValues(double [][] front, int noObjectives) {
        double [] minimumValue = new double[noObjectives];
        for (int i = 0; i < noObjectives; i++)
            minimumValue[i] = Double.MAX_VALUE;

        for (int i = 0;i < front.length; i++) {
            for (int j = 0; j < front[i].length; j++) {
                if (front[i][j] < minimumValue[j])
                    minimumValue[j] = front[i][j];
            }
        }
        return minimumValue;
    } // getMinimumValues


    /**
     *  This method returns the distance (taken the euclidean distance) between
     *  two points given as <code>double []</code>
     *  @param a A point
     *  @param b A point
     *  @return The euclidean distance between the points
     **/
    public double distance(double [] a, double [] b) {
        double distance = 0.0;

        for (int i = 0; i < a.length; i++) {
            distance += Math.pow(a[i]-b[i],2.0);
        }
        return Math.sqrt(distance);
    } // distance


    /**
     * Gets the distance between a point and the nearest one in
     * a given front (the front is given as <code>double [][]</code>)
     * @param point The point
     * @param front The front that contains the other points to calculate the
     * distances
     * @return The minimun distance between the point and the front
     **/
    public double distanceToClosedPoint(double [] point, double [][] front) {
        double minDistance = distance(point,front[0]);


        for (int i = 1; i < front.length; i++) {
            double aux = distance(point,front[i]);
            if (aux < minDistance) {
                minDistance = aux;
            }
        }

        return minDistance;
    } // distanceToClosedPoint


    /**
     * Gets the distance between a point and the nearest one in
     * a given front, and this distance is greater than 0.0
     * @param point The point
     * @param front The front that contains the other points to calculate the
     * distances
     * @return The minimun distances greater than zero between the point and
     * the front
     */
    public double distanceToNearestPoint(double [] point, double [][] front) {
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < front.length; i++) {
            double aux = distance(point,front[i]);
            if ((aux < minDistance) && (aux > 0.0)) {
                minDistance = aux;
            }
        }

        return minDistance;
    } // distanceToNearestPoint

    /**
     * This method receives a pareto front and two points, one whit maximun values
     * and the other with minimun values allowed, and returns a the normalized
     * pareto front.
     * @param front A pareto front.
     * @param maximumValue The maximun values allowed
     * @param minimumValue The mininum values allowed
     * @return the normalized pareto front
     **/
    public double [][] getNormalizedFront(double [][] front,
                                          double [] maximumValue,
                                          double [] minimumValue) {

        double [][] normalizedFront = new double[front.length][];

        for (int i = 0; i < front.length;i++) {
            normalizedFront[i] = new double[front[i].length];
            for (int j = 0; j < front[i].length; j++) {
                normalizedFront[i][j] = (front[i][j] - minimumValue[j]) /
                        (maximumValue[j] - minimumValue[j]);
            }
        }
        return normalizedFront;
    } // getNormalizedFront

    /**
     * This method receives a normalized pareto front and return the inverted one.
     * This operation needed for minimization problems
     * @param front The pareto front to inverse
     * @return The inverted pareto front
     **/
    public double[][] invertedFront(double [][] front) {
        double [][] invertedFront = new double[front.length][];

        for (int i = 0; i < front.length; i++) {
            invertedFront[i] = new double[front[i].length];
            for (int j = 0; j < front[i].length; j++) {
                if (front[i][j] <= 1.0 && front[i][j]>= 0.0) {
                    invertedFront[i][j] = 1.0 - front[i][j];
                } else if (front[i][j] > 1.0) {
                    invertedFront[i][j] = 0.0;
                } else if (front[i][j] < 0.0) {
                    invertedFront[i][j] = 1.0;
                }
            }
        }
        return invertedFront;
    } // invertedFront

}
