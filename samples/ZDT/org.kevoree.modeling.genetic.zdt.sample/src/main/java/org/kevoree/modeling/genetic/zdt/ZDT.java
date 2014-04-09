package org.kevoree.modeling.genetic.zdt;

import org.zdt.Dlist;

/**
 * Created by assaad.moawad on 4/9/2014.
 */
public class ZDT {
    public static double calculate (Dlist model, int zdtNum, int funcNum){
        if(funcNum==0){
            if(zdtNum<=4){
                return model.getValues().get(0).getDoubleValue(); //return x[0]
            }
            else if(zdtNum==5){
                //Not implemented

            }
            else if(zdtNum==6){
                double f = 1.0 - Math.exp(-4.0 * model.getValues().get(0).getDoubleValue())
                        * Math.pow(Math.sin(6.0 * Math.PI * model.getValues().get(0).getDoubleValue()), 6.0);

                return f;
            }

        }
        else if(funcNum==1) {
            switch (zdtNum) {
                case 1: {
                    double g = 0.0;
                    for (int i = 1; i < model.getValues().size(); i++) {
                        g += model.getValues().get(i).getDoubleValue();
                    }
                    g = (9.0 / (model.getValues().size() - 1)) * g + 1.0;

                    double h = 1.0 - Math.sqrt(model.getValues().get(0).getDoubleValue() / g);
                    return g * h;
                }
                case 2: {
                    double g = 0.0;
                    for (int i = 1; i < model.getValues().size(); i++) {
                        g += model.getValues().get(i).getDoubleValue();
                    }
                    g = (9.0 / (model.getValues().size() - 1)) * g + 1.0;

                    double h = 1.0 - Math.pow(model.getValues().get(0).getDoubleValue() / g, 2.0);
                    return g * h;
                }
                case 3: {
                    double g = 0.0;
                    for (int i = 1; i < model.getValues().size(); i++) {
                        g += model.getValues().get(i).getDoubleValue();
                    }
                    g = (9.0 / (model.getValues().size() - 1)) * g + 1.0;

                    double h = 1.0 - Math.sqrt(model.getValues().get(0).getDoubleValue() / g) - (model.getValues().get(0).getDoubleValue() / g)
                            * Math.sin(10.0 * Math.PI * model.getValues().get(0).getDoubleValue());
                    return g * h;
                }
                case 4: {
                    double g = 0.0;
                    for (int i = 1; i < model.getValues().size(); i++) {
                        g += Math.pow(model.getValues().get(i).getDoubleValue(), 2.0) - 10.0 * Math.cos(4.0 * Math.PI * model.getValues().get(i).getDoubleValue());
                    }
                    g += 1.0 + 10.0 * (model.getValues().size() - 1);

                    double h = 1.0 - Math.sqrt(model.getValues().get(0).getDoubleValue() / g);
                    return g * h;
                }
                case 5: {
                    //Not implemented
                    break;
                }
                case 6: {
                    double f = 1.0 - Math.exp(-4.0 * model.getValues().get(0).getDoubleValue())
                            * Math.pow(Math.sin(6.0 * Math.PI * model.getValues().get(0).getDoubleValue()), 6.0);

                    double g = 0.0;
                    for (int i = 1; i < model.getValues().size(); i++) {
                        g += model.getValues().get(i).getDoubleValue();
                    }
                    g = 1.0 + 9.0 * Math.pow(g / (model.getValues().size() - 1), 0.25);

                    double h = 1.0 - Math.pow(f / g, 2.0);
                    return g * h;
                }
            }
        }
        return 0;
    }
}
