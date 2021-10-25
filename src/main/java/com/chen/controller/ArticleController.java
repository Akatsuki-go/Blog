package com.chen.controller;

import com.chen.entity.*;
import com.chen.service.RedisServiceImpl;
import com.chen.service.serviceInterface.ArticleCommentService;
import com.chen.service.serviceInterface.ArticleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleCommentService articleCommentService;
    @Autowired
    RedisServiceImpl redisService;

    @GetMapping("/article/all")
    public List<Article> queryAllArticle(){
        return articleService.listArticleAll();
    }

    @PostMapping("/article/add")
    public String addArticle(@RequestBody Article article){
//        System.out.println(article);
        if (articleService.saveArticle(article)>0)
            return "200";
        else
            return "503";
//        return "200";
    }


    @GetMapping("/article/{userId}/{articleId}")
    public Map<String,Object> queryArticle (@PathVariable String userId,@PathVariable String articleId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("article",articleService.getArticleById(articleId,userId));

        return map;
    }
    @GetMapping("/article/top")
    public List<Article> top(){
        return null;
    }
    //文章分页
    @GetMapping("/article/page/{userId}/{PageNo}/{PageSize}")
    public ArticlePage articlePage(@PathVariable("userId") int userId, @PathVariable("PageNo") int PageNo,
                                   @PathVariable("PageSize") int PageSize){
        return articleService.listArticlePage(PageNo, PageSize,userId);
    }
    @PostMapping("/article/recom")
    public List<Map<String, Object>> queryRecommendArticle(@RequestBody HashMap<String,Object> map){
//        System.out.println("map");
//        System.out.println(map);
        return articleService.getRecommendArticle(map);
    }

    @GetMapping("/article/recomSize")
    public Integer getRecommendSize (){
        return redisService.getRecomArtSzie().intValue();
    }
    @PostMapping("/article/del")
    public ArticlePage delArticle(@RequestBody HashMap<String, Object> map){
        ArticlePage articlePage = articleService.removeArticleForId(map);
        System.out.println(articlePage);
        return articlePage;
    }

/*========================================文章评论===============================*/
    /*
    文章评论
     */
    @PostMapping("/articleC/add")
    public String addArticleComment(@RequestBody Comment articleComment){
        System.out.println(articleComment);
//        return "200";
        if (articleCommentService.addArticleComment(articleComment)>0){
            return "200";
        }else{
            return "503";
        }
    }

//    @PostMapping("/articleC/lately")
//    public List<Comment> lately(@RequestBody HashMap<String,Object> map){
//        return articleCommentService.listArticleCommentsLately(map);
//    }

    //文章评论分页
    @GetMapping("/articleC/page/{userId}/{articleId}/{PageNo}/{PageSize}")
    public CommentPage articleCommentPage(@PathVariable("userId") String userId,@PathVariable("articleId") int articleId, @PathVariable("PageNo") int PageNo,
                                             @PathVariable("PageSize") int PageSize){
//        System.out.println("parms:"+articleId+" "+PageNo+" "+PageSize);

        CommentPage page = articleCommentService.listArticleCommentPage(userId,articleId, PageNo,PageSize);
//        System.out.println(page+"===");
        return page;
    }
    
    //用户的最近评论
    @GetMapping("/articleC/lately")
    public Page queryACUserLaterly(@Param("userId") int userId, @Param("pageNo") int pageNo, @Param("pageSize") Integer pageSize){
        return articleCommentService.listArticleCommentsTopOrderByNewTimeLimit(pageNo, pageSize, String.valueOf(userId));
    }
    @PostMapping("/articleC/upGood")
    public Map<String, Object> moityArticelComGood (@RequestBody HashMap<String, String> map){
//        System.out.println(map);
        return redisService.upComGood(map,"artCom");
    }
}
