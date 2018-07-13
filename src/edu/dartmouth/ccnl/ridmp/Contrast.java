package edu.dartmouth.ccnl.ridmp;

import edu.dartmouth.ccnl.ridmp.conf.Configuration;

/**
 *
 * Created by ccnl on 12/23/2014.
 */

public class Contrast {
    double[] color1;
    double[] color2;
    double rate;

    public Contrast(double c1, double c2) {
        double[] colors1 = new double[Configuration.length];
        for (int i = 0; i < Configuration.length; i++)
            colors1[i] = c1;
        this.color1 = colors1;


        double[] colors2 = new double[Configuration.length];
        for (int i = 0; i < Configuration.length; i++)
            colors2[i] = c2;
        this.color2 = colors2;
    }

    public Contrast(double[] color1, double[] color2, double rate) {
        this.color1 = color1;
        this.color2 = color2;
        this.rate = rate;
    }

    public double[] getColor1() {
        return color1;
    }

    public void setColor1(double[] color1) {
        this.color1 = color1;
    }

    public double[] getColor2() {
        return color2;
    }

    public void setColor2(double[] color2) {
        this.color2 = color2;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}