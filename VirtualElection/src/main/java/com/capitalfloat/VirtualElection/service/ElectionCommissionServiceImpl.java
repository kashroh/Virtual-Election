package com.capitalfloat.VirtualElection.service;

import com.capitalfloat.VirtualElection.model.Vote;
import com.capitalfloat.VirtualElection.model.Voter;
import com.capitalfloat.VirtualElection.repository.DatabaseUtil;
import com.capitalfloat.VirtualElection.repository.ElectionCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Repository
public class ElectionCommissionServiceImpl implements ElectionCommissionService {

    // Class to implement service under service interface

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DatabaseUtil databaseUtil;


    @Override
    public String registerVoter(Voter voter) {
        if(voter.getName()== null)
            return "Error : Voter name is null";
        if(voter.getUniqueId() == null)
            return "Error : Unique id is null";
        if(voter.getConstituencyId() == null)
            return "Error : Constituency id is null";
        if(databaseUtil.isKeyExist(voter.getUniqueId(),"uniqueId", "voter"))
            return "Error : Unique Identification is used before, Either Voter is already registered or uniqueId is wrong";

        if(!databaseUtil.isKeyExist(voter.getConstituencyId(),"constituencyId","voter"))
            return "Error : Constituency id doesn't exist. ";

        String sql = "insert into voter(name,uniqueId,constituencyId) values (?,?,?)";

        KeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, voter.getName());
                    statement.setString(2, voter.getUniqueId());
                    statement.setInt(3, voter.getConstituencyId());
                    return statement;

                }
            }, holder);

        Integer generatedVoterId = holder.getKey().intValue();
        return "Success : Voter has registered with voter id " + generatedVoterId.toString();
        }
        catch(Exception ex)
        {
            return ex.getMessage();
        }
    }

    @Override
    public String getAllPartySymbol(Integer voterId)
    {
        if(voterId== null)
            return "Error : Voter id is empty.";
        if(!databaseUtil.isKeyExist(voterId,"id","voter"))
            return "Error : Voter id does not exist.";
        String sql = "select id, name, symbol from politicalparty";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        Map<String,String> votingApiDict = new Hashtable<>();
        for(Map<String, Object> iterator : list) {
            String politicalPartySymbol = iterator.get("symbol").toString();
            String partyName = iterator.get("name").toString();
            String votingApi =  politicalPartySymbol;
            votingApiDict.put(partyName,votingApi);

        }
        String baseIntroduction = "Success : Use following party symbol to cast vote for your favourite political party. \n ";
        return baseIntroduction + " \n "+ votingApiDict.toString();
    }


    @Override
    public String castVote(Integer voterId, String partySymbol)
    {
        if(voterId== null)
            return "Error : Voter id is empty.";
        if(partySymbol == null)
            return "Error : No party symbol given in request.";
        if(!databaseUtil.isKeyExist(voterId,"id","voter"))
            return "Error : Voter id does not exist. ";
        if(!databaseUtil.isKeyExist(partySymbol,"symbol","politicalparty"))
            return "Error : No political party associated with this symbol.";
        if(databaseUtil.isKeyExist(voterId,"voterId","vote"))
            return "Error : Voter is already done with voting.";
        Integer politicalPartyId  = databaseUtil.getAttribute(partySymbol,"symbol","id","politicalparty");
        Integer constituencyId = databaseUtil.getAttribute(voterId,"id","constituencyId","voter");


        String sql= "insert into vote (voterId,politicalPartyId,constituencyId) value (?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1, voterId);
                    statement.setInt(2, politicalPartyId);
                    statement.setInt(3, constituencyId);
                    return statement;

                }
            }, holder);
            Integer generatedVoterId = holder.getKey().intValue();
            return "Success : Voter has given vote with vote id " + generatedVoterId.toString();
        }
        catch(Exception ex)
        {
            return ex.getMessage();
        }

    }

    @Override
    public Vote getVoteDetail(Integer voteId) throws  Exception
    {
        if(voteId== null)
            throw new Exception("Error : Voter id is empty.");
        if(!databaseUtil.isKeyExist(voteId,"id", "vote"))
            throw new Exception("No vote casted with given vote id");
        String sql = "select * from vote where id = ?";
        Vote vote  = jdbcTemplate.
                queryForObject(sql, new Object[]{voteId} ,new BeanPropertyRowMapper<>(Vote.class));
        return vote;
    }

    @Override
    public String winningParty()
    {
        String sql = "select politicalPartyId, voteCnt from ( select politicalPartyId, count(*)  voteCnt" +
                " from (select politicalPartyId , constituencyId from (" +
                " select politicalPartyId , constituencyId, votes , row_number() over " +
                "(partition by constituencyId order by votes desc ) as partyRank from " +
                "(select politicalPartyId , constituencyId , count(*) as votes from vote group by politicalPartyId , constituencyId) vtable1 )" +
                "  vtable2 where partyRank=1 ) vtable3 group by politicalPartyId order by voteCnt desc) vtable4 limit 1;";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if(list ==null || list.isEmpty())
            return "No vote has casted yet.";
        Integer partyId = (Integer)list.get(0).get("politicalPartyId");
        Long seats = (long)list.get(0).get("voteCnt");
        sql = "select name from politicalparty where id = ?";
        list =  jdbcTemplate.
                queryForList(sql, new Object[]{partyId});
        String partyName =  list.get(0).get("name").toString();
        return partyName + " won the election with " +  seats.toString() + " seats";




    }
}
