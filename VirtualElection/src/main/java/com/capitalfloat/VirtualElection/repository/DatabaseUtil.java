package com.capitalfloat.VirtualElection.repository;



public interface DatabaseUtil {
    //Interface for Database Utility
    <E> boolean isKeyExist(E id, String idName, String table );
    <E> Integer getAttribute(E givenAttribute, String givenAttributeKey, String returnAttributeKey, String table);

}
