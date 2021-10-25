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
public class Picture implements Serializable {
    private int id;
    private int userId;
    private int groupId;
    private String suffix;
    private Date createTime;
    private String name;
    private int good;

    private String realName;
}
