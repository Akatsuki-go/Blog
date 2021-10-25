package com.chen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Album implements Serializable {
    private int id;
    private int userId;
    private String groupName;
    private int photoNum;
    private Date createTime;
}
