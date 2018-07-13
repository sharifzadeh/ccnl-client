package edu.dartmouth.ccnl.ridmp.dto;

/**
 *
 * Created by ccnl on 12/28/2014.
 */
public class ShapeTO extends RIDMPDataObject {
    private int shapeId;
    private int size;
    private double std;
    private double phase;
    private String frequencies;
    private String orientations;
    private String env;
    private String contrast;
    private double bgColor;

    public ShapeTO() {
    }

    public int getShapeId() {
        return shapeId;
    }

    public void setShapeId(int shapeId) {
        this.shapeId = shapeId;
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

    public String getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(String frequencies) {
        this.frequencies = frequencies;
    }

    public String getOrientations() {
        return orientations;
    }

    public void setOrientations(String orientations) {
        this.orientations = orientations;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getContrast() {
        return contrast;
    }

    public void setContrast(String contrast) {
        this.contrast = contrast;
    }

    public double getBgColor() {
        return bgColor;
    }

    public void setBgColor(double bgColor) {
        this.bgColor = bgColor;
    }
}
