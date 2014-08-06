package org.kevoree.modeling.optimization.executionmodel

public object HyperVolumeIndicator {

    fun dominates(point1: DoubleArray, point2: DoubleArray, noObjectives: Int): Boolean {
        var i: Int
        var betterInAnyObjective: Int
        betterInAnyObjective = 0
        i = 0
        while (i < noObjectives && point1[i] >= point2[i])
        {
            if (point1[i] > point2[i]){
                betterInAnyObjective = 1
            }
            i++
        }
        return ((i >= noObjectives) && (betterInAnyObjective > 0))
    }

    fun swap(front: Array<DoubleArray>, i: Int, j: Int): Unit {
        var temp: DoubleArray
        temp = front[i]
        front[i] = front[j]
        front[j] = temp
    }

    fun filterNondominatedSet(front: Array<DoubleArray>, noPoints: Int, noObjectives: Int): Int {
        var i: Int
        var j: Int
        var n: Int
        n = noPoints
        i = 0
        while (i < n)
        {
            j = i + 1
            while (j < n)
            {
                if (dominates(front[i], front[j], noObjectives))
                {
                    n--
                    swap(front, j, n)
                }
                else
                    if (dominates(front[j], front[i], noObjectives))
                    {
                        n--
                        swap(front, i, n)
                        i--
                        break
                    }
                    else
                        j++
            }
            i++
        }
        return n
    }
    fun surfaceUnchangedTo(front: Array<DoubleArray>, noPoints: Int, objective: Int): Double {
        var i: Int
        var minValue: Double
        var value: Double
        if (noPoints < 1){
            throw Exception("run-time error")
        }
        minValue = front[0][objective]
        i = 1
        while (i < noPoints){
            value = front[i][objective]
            if (value < minValue){
                minValue = value
            }
            i++
        }
        return minValue
    }
    fun reduceNondominatedSet(front: Array<DoubleArray>, noPoints: Int, objective: Int, threshold: Double): Int {
        var n: Int
        var i: Int
        n = noPoints
        i = 0
        while (i < n){
            if (front[i][objective] <= threshold){
                n--
                swap(front, i, n)
            }
            i++
        }
        return n
    }
    public fun calculateHypervolume(front: Array<DoubleArray>, noPoints: Int, noObjectives: Int): Double {
        var n: Int
        var volume: Double
        var distance: Double
        volume = 0.toDouble()
        distance = 0.toDouble()
        n = noPoints
        while (n > 0){
            var noNondominatedPoints: Int
            var tempVolume: Double
            var tempDistance: Double
            noNondominatedPoints = filterNondominatedSet(front, n, noObjectives - 1)
            tempVolume = 0.toDouble()
            if (noObjectives < 3){
                if (noNondominatedPoints < 1){
                    throw Exception("run-time error")
                }
                tempVolume = front[0][0]
            }
            else{
                tempVolume = calculateHypervolume(front, noNondominatedPoints, noObjectives - 1)
            }
            tempDistance = surfaceUnchangedTo(front, n, noObjectives - 1)
            volume += tempVolume * (tempDistance - distance)
            distance = tempDistance
            n = reduceNondominatedSet(front, n, noObjectives - 1, distance)
        }
        return volume
    }
    fun mergeFronts(front1: Array<DoubleArray>, sizeFront1: Int, front2: Array<DoubleArray>, sizeFront2: Int, noObjectives: Int): Array<DoubleArray> {
        var i: Int
        var j: Int
        var noPoints: Int
        var frontPtr: Array<DoubleArray>
        noPoints = sizeFront1 + sizeFront2
        frontPtr = Array<DoubleArray>(noPoints, { DoubleArray(noObjectives) })
        noPoints = 0
        i = 0
        while (i < sizeFront1){
            j = 0
            while (j < noObjectives){
                frontPtr[noPoints][j] = front1[i][j]
                j++
            }
            noPoints++
            i++
        }
        i = 0
        while (i < sizeFront2){
            j = 0
            while (j < noObjectives){
                frontPtr[noPoints][j] = front2[i][j]
                j++
            }
            noPoints++
            i++
        }
        return frontPtr
    }
    public fun hypervolume(paretoFront: Array<DoubleArray>, paretoTrueFront: Array<DoubleArray>, numberOfObjectives: Int): Double {
        var maximumValues: DoubleArray
        var minimumValues: DoubleArray
        var normalizedFront: Array<DoubleArray>
        var invertedFront: Array<DoubleArray>
        maximumValues = getMaximumValues(paretoTrueFront, numberOfObjectives)
        minimumValues = getMinimumValues(paretoTrueFront, numberOfObjectives)
        normalizedFront = getNormalizedFront(paretoFront, maximumValues, minimumValues)
        invertedFront = invertedFront(normalizedFront)
        return this.calculateHypervolume(invertedFront, (invertedFront.size), numberOfObjectives)
    }

    public fun getMaximumValues(front: Array<DoubleArray>, noObjectives: Int): DoubleArray {
        var maximumValue = DoubleArray(noObjectives)
        for(i in 0..noObjectives){
            maximumValue[i] = java.lang.Double.NEGATIVE_INFINITY
        }
        for (aFront in front) {
            for (j in 0..aFront.size) {
                if (aFront[j] > maximumValue[j]){
                    maximumValue[j] = aFront[j];
                }
            }
        }
        return maximumValue;
    }
    public fun getMinimumValues(front: Array<DoubleArray>, noObjectives: Int): DoubleArray {
        var minimumValue = DoubleArray(noObjectives)
        for(i in 0..noObjectives){
            minimumValue[i] = java.lang.Double.MAX_VALUE
        }
        for (aFront in front) {
            for (j in 0..aFront.size) {
                if (aFront[j] < minimumValue[j]){
                    minimumValue[j] = aFront[j];
                }
            }
        }
        return minimumValue;
    }

    public fun invertedFront(front: Array<DoubleArray>): Array<DoubleArray> {
        var invertedFront: Array<DoubleArray> = Array<DoubleArray>(front.size, { DoubleArray(0) })
        for(i in 0..front.size){
            invertedFront[i] = DoubleArray(front[i].size);
            for(j in 0..front.size){
                if (front[i][j] <= 1.0 && front[i][j] >= 0.0) {
                    invertedFront[i][j] = 1.0 - front[i][j];
                } else if (front[i][j] > 1.0) {
                    invertedFront[i][j] = 0.0;
                } else if (front[i][j] < 0.0) {
                    invertedFront[i][j] = 1.0;
                }
            }

        }
        return invertedFront;
    }

    public fun getNormalizedFront(front: Array<DoubleArray>, maximumValue: DoubleArray, minimumValue: DoubleArray): Array<DoubleArray> {
        var normalizedFront = Array<DoubleArray>(front.size, { DoubleArray(0) });
        for(i in 0..front.size){
            normalizedFront[i] = DoubleArray(front[i].size);
            for(j in 0..front[i].size){
                normalizedFront[i][j] = (front[i][j] - minimumValue[j]) / (maximumValue[j] - minimumValue[j]);
            }
        }
        return normalizedFront;
    }

}
