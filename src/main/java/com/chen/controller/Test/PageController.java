package com.chen.controller.Test;//package com.chen.controller;
//
//import com.chen.entity.Article;
//import com.chen.entity.Comment;
//import com.chen.entity.Page;
//import com.chen.service.serviceInterface.PageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class PageController {
//    @Autowired
//    PageService pageService;
//
//    @GetMapping("/articlePage/{userId}/{PageNo}/{PageSize}")
//    public Page<Article> articlePage(@PathVariable("userId") int userId,@PathVariable("PageNo") int PageNo,@PathVariable("PageSize") int PageSize){
//        return pageService.getPage("com.chen.entity.Article", PageNo, PageSize,userId);
//    }
//
//
//    @GetMapping("/articleCommentPage/{articleId}/{PageNo}/{PageSize}")
//    public Page<Comment>  articleCommentPage(@PathVariable("articleId") int articleId, @PathVariable("PageNo") int PageNo,
//                                             @PathVariable("PageSize") int PageSize){
//        System.out.println(articleId+" "+PageNo+" "+PageSize);
//
//        Page page = pageService.getPage("com.chen.entity.Comment", PageNo, PageSize,articleId);
//        System.out.println(page);
//        return page;
//    }
//}
