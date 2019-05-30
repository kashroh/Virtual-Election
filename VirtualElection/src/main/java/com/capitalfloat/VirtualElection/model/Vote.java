package com.capitalfloat.VirtualElection.model;

public class Vote {

    // Model Class for vote table

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoterId() {
        return voterId;
    }

    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

    public int getPoliticalPartyId() {
        return politicalPartyId;
    }

    public void setPoliticalPartyId(int politicalPartyId) {
        this.politicalPartyId = politicalPartyId;
    }

    private int voterId;
    private int politicalPartyId;


}
