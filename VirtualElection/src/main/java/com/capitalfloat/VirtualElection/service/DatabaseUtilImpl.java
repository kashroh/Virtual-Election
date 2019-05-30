package com.capitalfloat.VirtualElection.service;

import com.capitalfloat.VirtualElection.repository.DatabaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DatabaseUtilImpl implements DatabaseUtil {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public  <E> boolean isKeyExist(E id, String idName, String table )
    {
        //Generic function to see , if id exist or not in table works for all types.
        String sql = "select " + idName + " from " + table +  " where " +  idName + " = ? ";
        List<Map<String,Object>> list =  jdbcTemplate.
                queryForList(sql, new Object[]{id});
        if(list == null || list.isEmpty())
            return false;
        return true;



    }
    public  <E> Integer getAttribute(E givenAttribute, String givenAttributeKey, String returnAttributeKey, String table)
    {
        //Generic Function to return attribute with givenAttributeKey is attribute column name
        //for given key while returnAttributeKey - column name of return value in table
        // table - table
        String sql = "select " +  returnAttributeKey + " from " + table +
                " where " + givenAttributeKey + " = ?";
        List<Map<String,Object>> list =  jdbcTemplate.
                queryForList(sql, new Object[]{givenAttribute});
        return (Integer)list.get(0).get(returnAttributeKey);
    }
}
