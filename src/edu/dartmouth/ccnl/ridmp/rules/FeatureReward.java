package edu.dartmouth.ccnl.ridmp.rules;

/**
 *
 * Created by ccnl on 12/24/2014.
 */

public class FeatureReward implements Reward {
    private String feature;
    private double[] rewardProbabilities;

    public FeatureReward(String feature, double[] rewardProbabilities) {
        this.feature = feature;
        this.rewardProbabilities = rewardProbabilities;
    }

    public FeatureReward() {
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public double[] getRewardProbabilities() {
        return rewardProbabilities;
    }

    public void setRewardProbabilities(double[] rewardProbabilities) {
        this.rewardProbabilities = rewardProbabilities;
    }
}

