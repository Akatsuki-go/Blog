package com.chen.entity;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Data
@ToString
public class Comment implements Serializable {
    private BigInteger id;
    private String content;
    private Date createTime;
    private int good;
    private int bad;
    private int forId;
    private int userId;

    /*新评论 构造*/
    public Comment(String content, Date createTime, int forId, int userId){
        this.content=content;
        this.createTime=createTime;
        this.bad=0;
        this.good=0;
        this.forId=forId;
        this.userId=userId;
    }
    public Comment(){
    }

}
