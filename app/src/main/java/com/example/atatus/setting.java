package com.example.atatus;

public class setting {
    String addid;
    Double latiid;
    Double longiid;

    public setting(String addid, Double latiid, Double longiid) {
        this.addid = addid;
        this.latiid = latiid;
        this.longiid = longiid;
    }

    public String getAddid() {
        return addid;
    }

    public void setAddid(String addid) {
        this.addid = addid;
    }

    public Double getLatiid() {
        return latiid;
    }

    public void setLatiid(Double latiid) {
        this.latiid = latiid;
    }

    public Double getLongiid() {
        return longiid;
    }

    public void setLongiid(Double longiid) {
        this.longiid = longiid;
    }
}
