package com.chen.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class Article implements Serializable {
    private int articleId;
    private String title;
    private String introduction;
    private String content;
    private int readNum;
    private int comment;
    private int userId;
    private Date createTime;

    public Article(String title,String introduction,String content,int userId){
        this.title=title;
        this.introduction=introduction;
        this.content=content;
        this.userId=userId;
        this.readNum=0;
        this.comment=0;
        this.createTime=new Date();
    }
    public Article(String title,String introduction,String content,int readNum,int comment,int userId){
        this.title=title;
        this.introduction=introduction;
        this.content=content;
        this.userId=userId;
        this.readNum=readNum;
        this.comment=comment;
        this.createTime=new Date();
    }
    public Article(){
    }
    public Article(int articleId,String title,String introduction,String content,int readNum,int comment,int userId){
        this.articleId=articleId;
        this.title=title;
        this.introduction=introduction;
        this.content=content;
        this.readNum=readNum;
        this.comment=comment;
        this.userId=userId;
        this.createTime=new Date();
    }
}
