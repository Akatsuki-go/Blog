package com.chen.utils;

public class TypeUtils {
    //Integer to long
    public static long IntegerToLong(Object input){
        Integer temp = (Integer)input;
        return temp.longValue();
    }
    public static Integer DoubleToInteger(Object input){
        Double temp= (Double) input;
        return temp.intValue();
    }
}
