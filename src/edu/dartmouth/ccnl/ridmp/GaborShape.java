package edu.dartmouth.ccnl.ridmp;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 *
 * Created by ccnl on 12/24/2014.
 */

public class GaborShape extends WritableImage {

    private boolean reward;

    private Double specialFrequency;
    private Double orientation;
    private String contrast;

    private String probabilities;


    public GaborShape(int i, int i1) {
        super(i, i1);
    }

    public GaborShape(PixelReader pixelReader, int i, int i1) {
        super(pixelReader, i, i1);
    }

    public GaborShape(PixelReader pixelReader, int i, int i1, int i2, int i3) {
        super(pixelReader, i, i1, i2, i3);
    }

    public boolean isReward() {
        return reward;
    }

    public void setReward(boolean reward) {
        this.reward = reward;
    }

    public Double getSpecialFrequency() {
        return specialFrequency;
    }

    public void setSpecialFrequency(Double specialFrequency) {
        this.specialFrequency = specialFrequency;
    }

    public Double getOrientation() {
        return orientation;
    }

    public void setOrientation(Double orientation) {
        this.orientation = orientation;
    }

    public String getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(String probabilities) {
        this.probabilities = probabilities;
    }

    public String getContrast() {
        return contrast;
    }

    public void setContrast(String contrast) {
        this.contrast = contrast;
    }
}
