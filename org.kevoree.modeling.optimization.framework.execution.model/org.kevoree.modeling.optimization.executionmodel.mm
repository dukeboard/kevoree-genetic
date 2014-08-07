
class org.kevoree.modeling.optimization.executionmodel.ExecutionModel  {
    @contained
    runs : org.kevoree.modeling.optimization.executionmodel.Run[0,*]
    @contained
    fitness : org.kevoree.modeling.optimization.executionmodel.Fitness[0,*]
}

class org.kevoree.modeling.optimization.executionmodel.Run  {
    startTime : Long
    endTime : Long
    algName : String
    @contained
    steps : org.kevoree.modeling.optimization.executionmodel.Step[0,*]
}

class org.kevoree.modeling.optimization.executionmodel.Step  {
    generationNumber : Int
    endTime : Long
    startTime : Long
    @contained
    solutions : org.kevoree.modeling.optimization.executionmodel.Solution[0,*]
    @contained
    metrics : org.kevoree.modeling.optimization.executionmodel.Metric[0,*]
}

class org.kevoree.modeling.optimization.executionmodel.Metric  {
    value : Double
}

class org.kevoree.modeling.optimization.executionmodel.Score  {
    value : Double
    @id
    name : String
    fitness : org.kevoree.modeling.optimization.executionmodel.Fitness
}

class org.kevoree.modeling.optimization.executionmodel.Hypervolume : org.kevoree.modeling.optimization.executionmodel.Metric {
}

class org.kevoree.modeling.optimization.executionmodel.Min : org.kevoree.modeling.optimization.executionmodel.FitnessMetric {
}

class org.kevoree.modeling.optimization.executionmodel.Best : org.kevoree.modeling.optimization.executionmodel.FitnessMetric {
}

class org.kevoree.modeling.optimization.executionmodel.Max : org.kevoree.modeling.optimization.executionmodel.FitnessMetric {
}

class org.kevoree.modeling.optimization.executionmodel.Mean : org.kevoree.modeling.optimization.executionmodel.FitnessMetric {
    sum : Double
}

class org.kevoree.modeling.optimization.executionmodel.Solution  {
    @contained
    scores : org.kevoree.modeling.optimization.executionmodel.Score[0,*]
}

class org.kevoree.modeling.optimization.executionmodel.Fitness  {
    @id
    name : String
}

class org.kevoree.modeling.optimization.executionmodel.FitnessMetric : org.kevoree.modeling.optimization.executionmodel.Metric {
    fitness : org.kevoree.modeling.optimization.executionmodel.Fitness
}

class org.kevoree.modeling.optimization.executionmodel.ParetoMean : org.kevoree.modeling.optimization.executionmodel.Metric {
}

class org.kevoree.modeling.optimization.executionmodel.MaxMean : org.kevoree.modeling.optimization.executionmodel.Metric {
}

class org.kevoree.modeling.optimization.executionmodel.MinMean : org.kevoree.modeling.optimization.executionmodel.Metric {
}

class org.kevoree.modeling.optimization.executionmodel.Epsilon : org.kevoree.modeling.optimization.executionmodel.Metric {
}

class org.kevoree.modeling.optimization.executionmodel.GeneralizedSpread : org.kevoree.modeling.optimization.executionmodel.Metric {
}

class org.kevoree.modeling.optimization.executionmodel.GenerationalDistance : org.kevoree.modeling.optimization.executionmodel.Metric {
}

class org.kevoree.modeling.optimization.executionmodel.InvertedGenerationalDistance : org.kevoree.modeling.optimization.executionmodel.Metric {
}
