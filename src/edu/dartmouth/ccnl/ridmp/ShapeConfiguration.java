package edu.dartmouth.ccnl.ridmp;

import java.io.Serializable;

/**
 *
 * Created by ccnl on 12/26/2014.
 */
public class ShapeConfiguration implements Serializable{
    private int size;
    private double std;
    private double phase;
    private double[] frequencies;
    private double[] orientations;
    private String env;
    private Contrast contrast;
    private double bgColor;
    private String contrastStr;
    private String frequenciesStr;
    private String orientationsStr;

    public ShapeConfiguration(int size, double std, double phase, double[] frequencies, double[] orientations, String env, Contrast contrast, double bgColor) {
        this.size = size;
        this.std = std;
        this.phase = phase;
        this.frequencies = frequencies;
        this.orientations = orientations;
        this.env = env;
        this.contrast = contrast;
        this.bgColor = bgColor;
    }

    public ShapeConfiguration(int size, double std, double phase, String frequenciesStr, String orientationsStr, String env, String contrastStr, double bgColor) {
        this.size = size;
        this.std = std;
        this.phase = phase;
        this.frequenciesStr = frequenciesStr;
        this.orientationsStr = orientationsStr;
        this.env = env;
        this.contrastStr = contrastStr;
        this.bgColor = bgColor;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getStd() {
        return std;
    }

    public void setStd(double std) {
        this.std = std;
    }

    public double getPhase() {
        return phase;
    }

    public void setPhase(double phase) {
        this.phase = phase;
    }

    public double[] getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(double[] frequencies) {
        this.frequencies = frequencies;
    }

    public double[] getOrientations() {
        return orientations;
    }

    public void setOrientations(double[] orientations) {
        this.orientations = orientations;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public Contrast getContrast() {
        return contrast;
    }

    public void setContrast(Contrast contrast) {
        this.contrast = contrast;
    }

    public double getBgColor() {
        return bgColor;
    }

    public void setBgColor(double bgColor) {
        this.bgColor = bgColor;
    }

    public String getContrastStr() {
        return contrastStr;
    }

    public void setContrastStr(String contrastStr) {
        this.contrastStr = contrastStr;
    }

    public String getFrequenciesStr() {
        return frequenciesStr;
    }

    public void setFrequenciesStr(String frequenciesStr) {
        this.frequenciesStr = frequenciesStr;
    }

    public String getOrientationsStr() {
        return orientationsStr;
    }

    public void setOrientationsStr(String orientationsStr) {
        this.orientationsStr = orientationsStr;
    }
}
