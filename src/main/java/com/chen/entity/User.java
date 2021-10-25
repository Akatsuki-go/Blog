package com.chen.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Alias("user")
@Data
@ToString
public class User implements Serializable {
    private int userId;
    private String userName;
    private String accountName;
    private String password;
    private Date newDate;
    private Date bir;
    private String qq;
    public User(String userName,String accountName,String password,Date bir,String qq){
        this.userId=0;
        this.userName=userName;
        this.accountName=accountName;
        this.password=password;
        this.newDate=new Date();
        this.bir=bir;
        this.qq=qq;
    }
    public User(int UserId,String userName,String accountName,String password,Date newDate,Date bir,String qq){
        this.userId=UserId;
        this.userName=userName;
        this.accountName=accountName;
        this.password=password;
        this.newDate=newDate;
        this.bir=bir;
        this.qq=qq;
    }
    public User(){
    }
    public User(String accountName,String password){
        this.accountName=accountName;
        this.password=password;
    }
}
