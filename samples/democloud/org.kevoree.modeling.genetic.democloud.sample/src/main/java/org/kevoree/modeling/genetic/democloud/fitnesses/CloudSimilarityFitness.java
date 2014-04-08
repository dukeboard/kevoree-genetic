package org.kevoree.modeling.genetic.democloud.fitnesses;

import org.cloud.*;
import org.cloud.VirtualNode;
import org.cloud.Cloud;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 10/10/13
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class CloudSimilarityFitness extends FitnessFunction<Cloud> {


    @Override
    public double evaluate(Cloud model, GenerationContext<Cloud> cloudGenerationContext) {
        double similaritymeasure =0;
        double maxsoftwaresize=0;
        for(VirtualNode node : model.getNodes()){
            double similarity =0;
            double typesimilarity =0;

            List<Software> softwareList =node.getSoftwares();

            /*checker la similarité de deux noeuds par rapport aux types et size de software qu'ils contiennent*/

            for(VirtualNode node1 : model.getNodes())

            {
                /*chercher le noeud qui contient plus de software, ceci est utilisé comme valeur de normlisation*/
                List<Software> softwareList1 =node1.getSoftwares();
                if (maxsoftwaresize <= softwareList1.size())
                    maxsoftwaresize   =softwareList1.size();


                /*ajouter un plus 1 si 2 noeuds contiennet un soft en commun*/

                for(Software software : softwareList1)

                {
                    if  (softwareList.contains(software))

                        typesimilarity=typesimilarity+1;
                }

               /* ajouter à cette valeur la similarité en se bsant sur le nombre de sofware */

                 if (softwareList1.size()!=0 && maxsoftwaresize !=0 )
                {
                    typesimilarity  =  typesimilarity /  softwareList1.size();
                    similarity  = similarity + (typesimilarity+ (Math.abs(softwareList.size() - softwareList1.size())))/model.getNodes().size();
                }



            }

                similaritymeasure   =    similaritymeasure + similarity;

        }

        return (similaritymeasure/ (model.getNodes().size()));
    }


    @Override
    public double min() {

        return 0.0;
    }
    public double max() {

        return 10.0;
    }
}