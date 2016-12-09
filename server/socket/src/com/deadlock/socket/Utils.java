package com.deadlock.socket;
/**
 * Write a description of class Utils here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import org.json.* ;

public class Utils
{
    public static final String SERVICE_URL  = "http://169.44.10.31:8080";
    public static boolean isJSONValid(String jsonInString ) {
        try {
           JSONObject respObject = new JSONObject(jsonInString);
           return true;
        } catch (Exception e) {
           return false;
        }
    }


}
