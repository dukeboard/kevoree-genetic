package org.kevoree.modeling.genetic.democloud;

import democloud.factory.DefaultDemocloudFactory;
import democloud.factory.DemocloudFactory;
import org.cloud.Cloud;
import org.cloud.RedunduncyRequirement;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/3/13
 * Time: 12:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class Requirements {

    private DemocloudFactory cloudfactory = new DefaultDemocloudFactory();


    public double Redunduncy(Cloud parent) {

        RedunduncyRequirement sla = cloudfactory.createRedunduncyRequirement();
        sla.setDuplicataPerComponent(3.0);
        return sla.getDuplicataPerComponent();

    }
}