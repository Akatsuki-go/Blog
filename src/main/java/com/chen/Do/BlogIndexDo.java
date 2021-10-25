package com.chen.Do;

import com.chen.entity.Article;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString

public class BlogIndexDo implements Serializable {
    /*首页文章list*/
    private List<Article> alist;
    /*阅读排行榜*/
    private List<String> topalist;
}
