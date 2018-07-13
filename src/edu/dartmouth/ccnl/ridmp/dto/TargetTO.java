package edu.dartmouth.ccnl.ridmp.dto;

import java.util.Date;

/**
 *
 * Created by ccnl on 1/2/2015.
 */
public class TargetTO extends RIDMPDataObject {

    private Integer targetId;
    private Integer reward;
    private Integer blockNumber;
    private Integer trialNumber;
    private Date targetedTime;
    private String assignment;

    private Integer perId;
    private PersonTO personTO;

    private Integer rowSelected;
    private Integer columnSelected;

    private String contrast;

    public TargetTO() {
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }

    public PersonTO getPersonTO() {
        return personTO;
    }

    public void setPersonTO(PersonTO personTO) {
        this.personTO = personTO;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
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

    public Date getTargetedTime() {
        return targetedTime;
    }

    public void setTargetedTime(Date targetedTime) {
        this.targetedTime = targetedTime;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getContrast() {
        return contrast;
    }

    public void setContrast(String contrast) {
        this.contrast = contrast;
    }

    public Integer getRowSelected() {
        return rowSelected;
    }

    public void setRowSelected(Integer rowSelected) {
        this.rowSelected = rowSelected;
    }
}