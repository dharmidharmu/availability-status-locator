package com.example.atatus;

public class userss {
    String userid;
    String emailid;
    String passid;
    String nameid;
    String ageid;
    String mobileid;
    String profid;
    String offid;
    String profpassid;

    public  userss(){}

    public userss(String userid, String emailid, String passid, String nameid, String ageid, String mobileid, String profid, String offid, String profpassid) {
        this.userid = userid;
        this.emailid = emailid;
        this.passid = passid;
        this.nameid = nameid;
        this.ageid = ageid;
        this.mobileid = mobileid;
        this.profid = profid;
        this.offid = offid;
        this.profpassid = profpassid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassid() {
        return passid;
    }

    public void setPassid(String passid) {
        this.passid = passid;
    }

    public String getNameid() {
        return nameid;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }

    public String getAgeid() {
        return ageid;
    }

    public void setAgeid(String ageid) {
        this.ageid = ageid;
    }

    public String getMobileid() {
        return mobileid;
    }

    public void setMobileid(String mobileid) {
        this.mobileid = mobileid;
    }

    public String getProfid() {
        return profid;
    }

    public void setProfid(String profid) {
        this.profid = profid;
    }

    public String getOffid() {
        return offid;
    }

    public void setOffid(String offid) {
        this.offid = offid;
    }

    public String getProfpassid() {
        return profpassid;
    }

    public void setProfpassid(String profpassid) {
        this.profpassid = profpassid;
    }
}
