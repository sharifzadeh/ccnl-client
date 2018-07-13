package edu.dartmouth.ccnl.ridmp.dto;

/**
 * Created by ccnl on 4/16/2015.
 */
public class RewardTO extends RIDMPDataObject {
    private Integer rewardId;
    private String rewardName;
    private String rewardType;

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }
}
