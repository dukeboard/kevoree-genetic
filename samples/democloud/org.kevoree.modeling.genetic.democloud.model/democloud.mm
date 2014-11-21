
class org.cloud.Cloud  {
    @contained
    nodes : org.cloud.VirtualNode[0,*]
    @contained
    requirements : org.cloud.SLARequirement
}

class org.cloud.VirtualNode  {
    @id
    id : String
    pricePerHour : Double
    @contained
    softwares : org.cloud.Software[0,*]
}

class org.cloud.Software  {
    @id
    name : String
    latency : Double
}

class org.cloud.Rackspace : org.cloud.VirtualNode {
}

class org.cloud.Amazon : org.cloud.VirtualNode {
}

class org.cloud.LatencyRequirement : org.cloud.SLARequirement {
    softwareName : String
    requiredLatency : Double
}

class org.cloud.SLARequirement  {
}

class org.cloud.CostRequirement : org.cloud.SLARequirement {
    maxPricePerHour : Double
}

class org.cloud.RedunduncyRequirement : org.cloud.SLARequirement {
    duplicataPerComponent : Double
}
