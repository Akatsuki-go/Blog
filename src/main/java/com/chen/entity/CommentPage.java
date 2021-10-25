package com.chen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentPage implements Serializable {
    //每页规定的记录数 default=4
    public  Integer PAGE_SIZE=4;
    //当前页码 default=1
    private Integer pageNo;

    //当前页的记录数   (end-start+PAGE_SIZE)%PAGE_SIZE
    private Integer pageSize;
    //页码总数      count+PAGE_SIZE-1/PAGE_SIZE
    private Integer pageTotal;
    //总记录数      count
    private Integer recordCount;
    //页内数据
    private List<Map<String, Object>> items;
    /*新添加的：评论页每条评论是否被该用户点赞*/
    private List<Boolean> goodList;
    /*新添加的：每条评论点赞的个数*/
    private List<Integer> goodNumList;

    //先设置页码总数
    public void setPageNo(Integer pageNo) {
        if(pageNo<1)
            this.pageNo=1;
        else if (pageNo>pageTotal){
            this.pageNo=pageTotal;
//            this.pageNo = pageNo;
        }else
            this.pageNo = pageNo;
    }

    public void setPageTotal(int count,int pageSize) {
        this.pageTotal=(count+pageSize-1)/pageSize;
    }

    /*计算出其他属性*/
    public CommentPage(int PageSize,int count,int start,int end,List<Map<String, Object>> list,int pageNo,
                       List<Boolean> goodList,List<Integer>goodNumList){
        this.PAGE_SIZE=PageSize;
        if (PageSize!=1)
            this.pageSize=end-start;
        else
            this.pageSize=1;

        this.pageTotal=(count+PAGE_SIZE-1)/PAGE_SIZE;
        this.recordCount=count;
        this.items=list;
        this.setPageNo(pageNo);
//        this.pageNo=pageNo;
        this.goodList=goodList;
        this.goodNumList=goodNumList;
    }
}
