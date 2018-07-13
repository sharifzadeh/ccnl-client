package edu.dartmouth.ccnl.ridmp.dto.entity;

import java.net.URLEncoder;

import java.util.HashMap;
import java.util.List;

public class WordFields {

    String preparationTime;
    String flowType;
    String keyword;
    String trackingNumber;
    String gender;

    String content;
    String sender;
    String senderDep;
    String registerDate;
    String regSerial;
    String receivers;
    String receiverDeps;
    String CCReceivers;
    String CCReceiverDeps;
    String receiverFaxes;
    String priority;
    String docType;
    String transferType;
    String classification;
    String subject;
    String hasAppendix;
    String appendix;
    String version;
    String relatedLetters;
    String signatures;
    String letterNumber;
    String personId;
    String checkSignFlag;
    String ccReceivers;
    String ccTitle;
    
    String lNumber;
    String lDate;
    String lAppendix;
    
    String[] receiverNames;
    String[] receiverTitles;
    
    Integer[] selectionCCId;
    Integer[] selectionTickedId;
    HashMap<Integer, String> hashCC;

    public WordFields() {
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getPreparationTime() {
        if (preparationTime == null)
            return " ";

        return preparationTime;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getFlowType() {
        if (flowType == null)
            return " ";

        return flowType;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        if (keyword == null)
            return " ";
            
        return keyword;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingNumber() {
        if (trackingNumber == null)
            return " ";
            
        return trackingNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        if (gender == null)
            return " ";
            
        return gender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
//        try {
//            if (content != null)
//                return URLEncoder.encode(content, "UTF-8");
//        } catch (Exception ex) {
//
//        }
         if (content == null)
             return " ";
     
        return content;

    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        if (sender == null)
            return " ";

        return sender;
    }

    public void setSenderDep(String senderDep) {
        this.senderDep = senderDep;
    }

    public String getSenderDep() {
        if (senderDep == null)
            return " ";

        return senderDep;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterDate() {
        if (registerDate == null)
            return " ";

        return registerDate;
    }

    public void setRegSerial(String regSerial) {
        this.regSerial = regSerial;
    }

    public String getRegSerial() {
        if (regSerial == null)
            return " ";

        return regSerial;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public String getReceivers() {
        if (receivers == null)
            return " ";

        return receivers;
    }

    public void setReceiverDeps(String receiverDeps) {
        this.receiverDeps = receiverDeps;
    }

    public String getReceiverDeps() {
        if (receiverDeps == null)
            return " ";

        return receiverDeps;
    }

    public void setCCReceivers(String cCReceivers) {
        this.CCReceivers = cCReceivers;
    }

    public String getCCReceivers() {
        if (CCReceivers == null)
            return " ";
            
        return CCReceivers;
    }

    public void setCCReceiverDeps(String cCReceiverDeps) {
        this.CCReceiverDeps = cCReceiverDeps;
    }

    public String getCCReceiverDeps() {
        if (CCReceiverDeps == null)
            return " ";
            
        return CCReceiverDeps;
    }

    public void setReceiverFaxes(String receiverFaxes) {
        this.receiverFaxes = receiverFaxes;
    }

    public String getReceiverFaxes() {
        if (receiverFaxes == null)
            return " ";
            
        return receiverFaxes;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        if (priority == null)
            return " ";
            
        return priority;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocType() {
        if (docType == null)
            return " ";

        return docType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferType() {
        if (transferType == null)
            return " ";

        return transferType;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getClassification() {
        if (classification == null)
            return " ";

        return classification;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        if (subject == null)
            return " ";

        return subject;
    }

    public void setHasAppendix(String hasAppendix) {
        this.hasAppendix = hasAppendix;
    }

    public String getHasAppendix() {
        if (hasAppendix == null)
            return " ";
            
        return hasAppendix;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public String getAppendix() {
        if (appendix == null)
            return " ";

        return appendix;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        if (version == null)
            return " ";
            
        return version;
    }

    public void setRelatedLetters(String relatedLetters) {
        this.relatedLetters = relatedLetters;
    }

    public String getRelatedLetters() {
        if (relatedLetters == null)
            return " ";
            
        return relatedLetters;
    }

    public void setSignatures(String signatures) {
        this.signatures = signatures;
    }

    public String getSignatures() {
        if (signatures == null)
            return " ";
            
        return signatures;
    }

    public void setLetterNumber(String letterNumber) {
        this.letterNumber = letterNumber;
    }

    public String getLetterNumber() {
        if (letterNumber == null)
            return " ";
            
        return letterNumber;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {           
        return personId;
    }

    public void setCheckSignFlag(String checkSignFlag) {
        this.checkSignFlag = checkSignFlag;
    }

    public String getCheckSignFlag() {
        if (checkSignFlag == null)
            return " ";
            
        return checkSignFlag;
    }

    public void setCcReceivers(String ccReceivers) {
        this.ccReceivers = ccReceivers;
    }

    public String getCcReceivers() {
        if (ccReceivers == null)
            return "";
            
        return ccReceivers;
    }

    public void setCcTitle(String ccTitle) {
        this.ccTitle = ccTitle;
    }

    public String getCcTitle() {
        if (ccTitle == null)
            return "";
            
        return ccTitle;
    }

    public void setLNumber(String lNumber) {
        this.lNumber = lNumber;
    }

    public String getLNumber() {
        return lNumber;
    }

    public void setLDate(String lDate) {
        this.lDate = lDate;
    }

    public String getLDate() {
        return lDate;
    }

    public void setLAppendix(String lAppendix) {
        this.lAppendix = lAppendix;
    }

    public String getLAppendix() {
        return lAppendix;
    }

    public void setReceiverNames(List<String> receiverNames) {
        String[] names = null;
        if (receiverNames.size() > 0) {
            names = new String[receiverNames.size()];
            receiverNames.toArray(names);            
        }
        this.receiverNames = names;
    }

    public String[] getReceiverNames() {
        return receiverNames;
    }

    public void setReceiverTitles(List<String> receiverTitles) {
        String[] titles = null;
        if (receiverTitles.size() > 0) {
            titles = new String[receiverTitles.size()];
            receiverTitles.toArray(titles);
        }        
        this.receiverTitles = titles;
    }

    public String[] getReceiverTitles() {
        return receiverTitles;
    }

    public void setSelectionTickedId(Integer[] selectionTickedId) {
        this.selectionTickedId = selectionTickedId;
    }

    public Integer[] getSelectionTickedId() {
        return selectionTickedId;
    }

    public void setHashCC(HashMap<Integer, String> hashCC) {
        this.hashCC = hashCC;
    }

    public HashMap<Integer, String> getHashCC() {
        return hashCC;
    }

    public void setSelectionCCId(Integer[] selectionCCId) {
        this.selectionCCId = selectionCCId;
    }

    public Integer[] getSelectionCCId() {
        return selectionCCId;
    }
}
