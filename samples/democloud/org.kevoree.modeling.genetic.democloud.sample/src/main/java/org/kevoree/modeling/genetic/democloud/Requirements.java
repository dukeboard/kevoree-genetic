package org.kevoree.modeling.genetic.democloud;

import org.cloud.Cloud;
import org.cloud.Redunduncy;
import org.cloud.SLARequirement;
import org.cloud.impl.DefaultCloudFactory;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/3/13
 * Time: 12:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class Requirements {

    private DefaultCloudFactory cloudfactory = new DefaultCloudFactory();


    public double Redunduncy(Cloud parent)
    {

        Redunduncy sla    =    cloudfactory.createRedunduncy();
        sla.setDuplicataPerComponent(3.0);
        return sla.getDuplicataPerComponent();

}
}