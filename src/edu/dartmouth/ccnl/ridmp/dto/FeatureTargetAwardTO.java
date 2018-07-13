package edu.dartmouth.ccnl.ridmp.dto;

import java.util.Date;

/**
 *
 * Created by ccnl on 1/3/2015.
 */
public class FeatureTargetAwardTO extends RIDMPDataObject{
    private Integer awardId;

    private Integer award;

    private Integer personAttemptedId;

    private Integer perId;
    private PersonTO personTO;

    private Integer rowSelected;
    private Integer columnSelected;

    private Integer blockNumber;
    private Integer trialNumber;

    private Date targetedTime;

    private Double specialFrequency;
    private Double orientation;

    private double probability;

    private String contrast;

    private String featureType;

    public FeatureTargetAwardTO() {
    }

    public Integer getAwardId() {
        return awardId;
    }

    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
    }

    public Integer getAward() {
        return award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }

    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }

    public Integer getPersonAttemptedId() {
        return personAttemptedId;
    }

    public void setPersonAttemptedId(Integer personAttemptedId) {
        this.personAttemptedId = personAttemptedId;
    }

    public PersonTO getPersonTO() {
        return personTO;
    }

    public void setPersonTO(PersonTO personTO) {
        this.personTO = personTO;
    }

    public Integer getRowSelected() {
        return rowSelected;
    }

    public void setRowSelected(Integer rowSelected) {
        this.rowSelected = rowSelected;
    }

    public Integer getColumnSelected() {
        return columnSelected;
    }

    public void setColumnSelected(Integer columnSelected) {
        this.columnSelected = columnSelected;
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

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Date getTargetedTime() {
        return targetedTime;
    }

    public void setTargetedTime(Date targetedTime) {
        this.targetedTime = targetedTime;
    }

    public Integer getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(Integer trialNumber) {
        this.trialNumber = trialNumber;
    }

    public String getContrast() {
        return contrast;
    }

    public void setContrast(String contrast) {
        this.contrast = contrast;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }
}