package org.kevoree.genetic;

import org.kevoree.genetic.framework.KevoreeSolution;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 15/03/13
 * Time: 17:39
 */
public interface KevoreeEngineIntrument {

    public void processResult(List<KevoreeSolution> solutions);

}
