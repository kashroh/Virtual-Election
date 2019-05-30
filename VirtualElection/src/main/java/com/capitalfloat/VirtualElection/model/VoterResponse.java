package com.capitalfloat.VirtualElection.model;

public class VoterResponse {
    // Model Class for voteresponse request body
   private Integer voterId;
   private String symbol;

    public Integer getVoterId() {
        return voterId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setVoterId(Integer voterId) {
        this.voterId = voterId;
    }
}
