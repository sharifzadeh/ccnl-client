package edu.dartmouth.ccnl.ridmp.dto;

import java.util.Date;

/**
 * Created by ccnl on 1/1/2015.
 */
public class PersonTO extends RIDMPDataObject {

    private Integer perId;
    private String firstName;
    private String lastName;
    private String dId;
    private String email;
    private String ticket;

    private Integer shapeId;
    private ShapeTO shapeTO;

    private Integer probabilityId;
    private ProbabilityTO probabilityTO;

    private boolean authorized;

    private Date authorizedDate;

    private Integer numberOfChoice;

    private Integer rewardId;
    private RewardTO rewardTO;

    private Integer reversal;

    private Integer status;

    private String orientations;
    private String specialFrequencies;
    private String probabilities;

    public PersonTO() {
    }

    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getShapeId() {
        return shapeId;
    }

    public void setShapeId(Integer shapeId) {
        this.shapeId = shapeId;
    }

    public ShapeTO getShapeTO() {
        return shapeTO;
    }

    public void setShapeTO(ShapeTO shapeTO) {
        this.shapeTO = shapeTO;
    }

    public ProbabilityTO getProbabilityTO() {
        return probabilityTO;
    }

    public void setProbabilityTO(ProbabilityTO probabilityTO) {
        this.probabilityTO = probabilityTO;
    }

    public Integer getProbabilityId() {
        return probabilityId;
    }

    public void setProbabilityId(Integer probabilityId) {
        this.probabilityId = probabilityId;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public Date getAuthorizedDate() {
        return authorizedDate;
    }

    public void setAuthorizedDate(Date authorizedDate) {
        this.authorizedDate = authorizedDate;
    }

    public Integer getNumberOfChoice() {
        return numberOfChoice;
    }

    public void setNumberOfChoice(Integer numberOfChoice) {
        this.numberOfChoice = numberOfChoice;
    }

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
    }

    public RewardTO getRewardTO() {
        return rewardTO;
    }

    public void setRewardTO(RewardTO rewardTO) {
        this.rewardTO = rewardTO;
    }

    public Integer getReversal() {
        return reversal;
    }

    public void setReversal(Integer reversal) {
        this.reversal = reversal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrientations() {
        return orientations;
    }

    public void setOrientations(String orientations) {
        this.orientations = orientations;
    }

    public String getSpecialFrequencies() {
        return specialFrequencies;
    }

    public void setSpecialFrequencies(String specialFrequencies) {
        this.specialFrequencies = specialFrequencies;
    }

    public String getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(String probabilities) {
        this.probabilities = probabilities;
    }
}