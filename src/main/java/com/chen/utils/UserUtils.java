package com.chen.utils;

public class UserUtils {
    public static boolean isNowUser(String userId,String accessUserId){
        if (userId.equals(accessUserId)){
            return true;
        }else{
            return false;
        }
    }
}
