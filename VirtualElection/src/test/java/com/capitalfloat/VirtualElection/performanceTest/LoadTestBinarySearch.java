package com.capitalfloat.VirtualElection.performanceTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import java.net.HttpURLConnection;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class LoadTestBinarySearch {

    //Class to test concurrent load request threshold
    //**** SET API URL in application.properties ****

        public static  Integer countThread ;
        public static  boolean hasException ;
        public static String error ;
        public long timeDelay;


        class ManagedProcessor implements Callable {


            private  String GET_URL="http://localhost:7879/winningparty";

            public void postMessage() throws  Exception {
                //Test execute single call for REST web service
                System.out.println("URL is " +  GET_URL);
                    sendGET();
            }


            public  void sendGET() throws  Exception{
                URL obj = new URL(GET_URL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", USER_AGENT);
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                } else {
                    throw new Exception("Response code is " + responseCode);
                }

            }

            @Override
            public Object call() throws Exception {

                try {
                    postMessage();
                }catch (Exception er)
                {
                    hasException = true;
                    error = er.toString();

                }
                return null;
            }

        }

        public Boolean loadTestImpl(Integer size)
        {
            // Return true if exception occurs of size number of request
            // else return false
            //Setting All Environment variable
            countThread =0 ;
            hasException = false;
            error = "";
            timeDelay=0;
            ExecutorService loadExecutor = Executors.newFixedThreadPool(size);
            LoadTestBinarySearch threadRunner = new LoadTestBinarySearch();
            List jobs = new ArrayList();
            for(int i=0;i<size;i++){
                ManagedProcessor exec = threadRunner.new ManagedProcessor() ;
                jobs.add(exec);
            }
            try {
                loadExecutor.invokeAll(jobs);
            } catch (InterruptedException e) {
                System.out.println("error");
                e.printStackTrace();
            }
            loadExecutor.shutdown();
            return hasException;
        }


        public Integer findFirstError()
        {
            // Test for 1 Request
            if (loadTestImpl(1) == true)
                return 1;

            // Find 'high' for binary search
            // by repeated doubling
            Integer iterator = 1;
            while (loadTestImpl(iterator) == false) {
                System.out.println(iterator);
                iterator = iterator * 2;
            }

            // Call binary search

            return binarySearch(iterator / 2, iterator);
        }

        public Integer binarySearch(Integer low, Integer high )
        {

            if (high >= low)
            {

                Integer mid = low + (high - low)/2;

                //Check for Request reponse for mid number of thread

                if (loadTestImpl(mid) == true && (mid == low || loadTestImpl(mid-1)== false))
                    return mid;


                if (loadTestImpl(mid) == false)
                    return binarySearch((mid + 1), high);
                else
                    return binarySearch(low, (mid -1));
            }
                return -1;
        }



        public static void main(String[] args) {

            //Size parameter is  number of concuurent request.
            //Currently this function return whether size(concurrent request) are handle by server or not.

            LoadTestBinarySearch loadTestBinarySearch = new LoadTestBinarySearch();
            Integer size  = 10;
            System.out.println("Please check if sever running before initialing load testing");
            if (loadTestBinarySearch.loadTestImpl(size)){
                    System.out.println("Service is not able to handle " + size.toString() +  " request "
                    + ", exiting out with  " +  error );}
            else{
                System.out.println("Service is able to handle " + size.toString() +  " request ");
            }


        }
    }




