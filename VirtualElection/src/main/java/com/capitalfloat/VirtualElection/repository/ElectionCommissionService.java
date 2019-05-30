package com.capitalfloat.VirtualElection.repository;

import com.capitalfloat.VirtualElection.model.Vote;
import com.capitalfloat.VirtualElection.model.Voter;

public interface ElectionCommissionService {
    // Interface for election services
    String registerVoter(Voter voter) ;
    String getAllPartySymbol(Integer voterId);
    String castVote(Integer voterId, String partySymbol);
    Vote getVoteDetail(Integer voteId) throws Exception;
    String winningParty();
}
