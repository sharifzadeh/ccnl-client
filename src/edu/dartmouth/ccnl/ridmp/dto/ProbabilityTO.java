package edu.dartmouth.ccnl.ridmp.dto;

/**
 *
 * Created by ccnl on 1/2/2015.
 */
public class ProbabilityTO extends RIDMPDataObject {
    private Integer probabilityId;
    private String probabilityRewardOrientation;
    private String probabilityRewardSF;

    public ProbabilityTO() {
    }

    public Integer getProbabilityId() {
        return probabilityId;
    }

    public void setProbabilityId(Integer probabilityId) {
        this.probabilityId = probabilityId;
    }

    public String getProbabilityRewardOrientation() {
        return probabilityRewardOrientation;
    }

    public void setProbabilityRewardOrientation(String probabilityRewardOrientation) {
        this.probabilityRewardOrientation = probabilityRewardOrientation;
    }

    public String getProbabilityRewardSF() {
        return probabilityRewardSF;
    }

    public void setProbabilityRewardSF(String probabilityRewardSF) {
        this.probabilityRewardSF = probabilityRewardSF;
    }
}
