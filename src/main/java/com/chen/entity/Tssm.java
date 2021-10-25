package com.chen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

//@Alias("tssm")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tssm implements Serializable {
    private int id;
    private String name;
}
