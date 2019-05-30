package com.capitalfloat.VirtualElection.controller;

import com.capitalfloat.VirtualElection.model.Vote;
import com.capitalfloat.VirtualElection.model.Voter;
import com.capitalfloat.VirtualElection.model.VoterResponse;
import com.capitalfloat.VirtualElection.repository.ElectionCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ElectionCommissionService electionCommissionService;


    @PostMapping(value = "/registervoter")
    public String registerVoter(@RequestBody Voter voter)
    {//Register Voter Api
        return electionCommissionService.registerVoter(voter);
    }

    @GetMapping(value = "/allpartysymbol/{voterId}")
    public String getAllPartySymbol(@PathVariable Integer voterId) {
        // Return All party symbol to Voter
       return electionCommissionService.getAllPartySymbol(voterId);
    }

    @PostMapping(value = "/castvote")
    public String castVote(@RequestBody VoterResponse voterResponse){
        // Return All party Symbol to voter
        return  electionCommissionService.castVote(voterResponse.getVoterId(),voterResponse.getSymbol());
    }

    @GetMapping(value = "/votedetail/{voteId}")
    public Vote getVoteDetail(@PathVariable Integer voteId) throws  Exception
    {
        // Return vote detail
        return electionCommissionService.getVoteDetail(voteId);
    }

    @GetMapping(value = "/winningparty")
    public String getWinningParty()
    {//Return Winning Party

        return electionCommissionService.winningParty();
    }

}

