package edu.dartmouth.ccnl.ridmp.dto;

/**
 *
 * Created by ccnl on 1/4/2015.
 */
public class ScheduleTO extends RIDMPDataObject {
    private Integer scheduleId;
    private Integer blockNumber;
    private Integer trialNumber;
    private Long trialSchedule;
    private Long shapeSchedule;
    private Integer reversal;

    public ScheduleTO() {
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Integer getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(Integer trialNumber) {
        this.trialNumber = trialNumber;
    }

    public Long getTrialSchedule() {
        return trialSchedule;
    }

    public void setTrialSchedule(Long trialSchedule) {
        this.trialSchedule = trialSchedule;
    }

    public Long getShapeSchedule() {
        return shapeSchedule;
    }

    public void setShapeSchedule(Long shapeSchedule) {
        this.shapeSchedule = shapeSchedule;
    }

    public Integer getReversal() {
        return reversal;
    }

    public void setReversal(Integer reversal) {
        this.reversal = reversal;
    }
}