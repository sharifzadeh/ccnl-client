package edu.dartmouth.ccnl.ridmp.rules;

/**
 *
 * Created by ccnl on 12/24/2014.
 */
public class ProbabilityReward implements Reward {

    private String probability;

    private double[] rewardOrientationProbabilities;
    private double[] rewardOrientationSpecialFrequencies;

    public ProbabilityReward(double[] rewardOrientationProbabilities, double[] rewardOrientationSpecialFrequencies) {
        this.rewardOrientationProbabilities = rewardOrientationProbabilities;
        this.rewardOrientationSpecialFrequencies = rewardOrientationSpecialFrequencies;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public double[] getRewardOrientationProbabilities() {
        return rewardOrientationProbabilities;
    }

    public void setRewardOrientationProbabilities(double[] rewardOrientationProbabilities) {
        this.rewardOrientationProbabilities = rewardOrientationProbabilities;
    }

    public double[] getRewardOrientationSpecialFrequencies() {
        return rewardOrientationSpecialFrequencies;
    }

    public void setRewardOrientationSpecialFrequencies(double[] rewardOrientationSpecialFrequencies) {
        this.rewardOrientationSpecialFrequencies = rewardOrientationSpecialFrequencies;
    }
}