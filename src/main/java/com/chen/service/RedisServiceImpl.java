package com.chen.service;

import com.chen.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RedisServiceImpl {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    //阅读量
    final private String ReadKey = "articleReadNum";
    //文章评论的点赞数
    final private String AComGoodKey = "goodNumArticleComment";
    //图片评论的点赞数
    final private String PComGoodKey = "goodNumPictureComment";
    /*======用户===============================================*/
    //用户是否阅读过该文章
    final private String uLatelyReadKey = "uLately";
    //用户是否对该文章点过赞
    final private String uArtComGoodKey = "userArticComGood";
    //用户是否对该图片点过赞
    final private String uPicComGoodKey = "userPicComGood";
    //排行榜
    final private String uReCommArticleKey= "reCommArticleUser";
    final private String uLastlyCommentKey= "lastlyCommentUser";



    /*================================文章===================================================*/

    /**
     * 文章阅读量增加
     *
     * @param userId    用户Id
     * @param articleId 文章id
     * @param num       阅读量增加的量
     */
    public void articleReadNumInrc(String userId, String articleId, int num) {
        String Key=uLatelyReadKey + articleId;
        if (!redisUtil.sHasKey(Key, userId)) {
            long l = redisUtil.sSet(Key, userId);
//            redisUtil.hincr(ReadKey, articleId, num);
            //            改用sortSet
            redisUtil.zAdd(ReadKey, articleId, num);
            /*文章排行榜阅读量增加*/
            String Key2=uReCommArticleKey+userId;
            Long rank = redisUtil.zRank(Key2, articleId);
            if (rank==null){
                redisUtil.zAdd(Key2, articleId, 1);
            }else if (rank.intValue()>=0){
                redisUtil.zIncrementScore(Key2, articleId, 1);
            }
        }
    }

    /**
     * 文章删除时，文章阅读量删除
     *
     * @param articleId 文章Id
     */
    public void delArticleReadNum(String articleId,String userId) {
        redisUtil.del(uLatelyReadKey + articleId);
        redisUtil.zRemove(ReadKey, articleId);
        redisUtil.zRemove(uReCommArticleKey+userId, articleId);
    }

    /**
     * 根据文章id获取文章的阅读量
     *
     * @param articleId 文章Id
     */
    public Integer getArticleReadNum(String articleId) {
//        return (Integer) redisUtil.hget(ReadKey, articleId);
        Double doubleDouble = redisUtil.zScore(ReadKey, articleId);
        Double aDouble = doubleDouble==null?0:doubleDouble;
//        System.out.println(aDouble);
        return aDouble.intValue();
    }


    public List<Integer> getPageReadNum(List<String> articleIdList) {
        List<Integer> reList = new ArrayList<>();
        for (String id : articleIdList) {
//            Integer n = (Integer) redisTemplate.opsForHash().get(ReadKey, id);
            Integer n=this.getArticleReadNum(id);
            if (n == null)
                n = 0;
            reList.add(n);
//            System.out.println("文章" + id + "阅读数: " + n);
        }
        return reList;
    }

   //一页的点赞List
    public List<Boolean> getComGoodPage(List<String> List, String userId,String key) {
        if (key.equals("artCom")){
            key=uArtComGoodKey;
        }
        if (key.equals("picCom")){
            key=uPicComGoodKey;
        }

//        redisTemplate.setEnableTransactionSupport(true);
//        redisTemplate.multi();
        List<Boolean> reList = new ArrayList<>();
        for (String id : List) {
//            System.out.println(id+" "+uId);
            //用户是否为文章点过赞
            Boolean member = redisTemplate.opsForSet().isMember(key + id, userId);
            if (member==null||member.equals(false)){
                reList.add(false);
            }else if (member.equals(true)){
                reList.add(true);
            }
        }
//        List<Object> exec = redisTemplate.exec();
//        redisTemplate.setEnableTransactionSupport(false);
//        System.out.println("reList: "+reList);
        return reList;
    }

    /*================================文章评论点赞操作===================================================*/
    /*do
     * user to ACGood
     * id ACGoodNum
     *
     *return
     *one AC（goodNUm，isGood）
     * */
    public Map<String, Object> upComGood(HashMap<String, String> map,String key) {
        String uKey="";
        String goodKey="";
        if (key.equals("artCom")){
            uKey=uArtComGoodKey;
            goodKey=AComGoodKey;
        }
        if (key.equals("picCom")){
            uKey=uPicComGoodKey;
            goodKey=PComGoodKey;
        }
        String userId = map.get("userId");
        String articleComId = map.get("ComId");
        String index=map.get("index");
        System.out.println(userId);
        System.out.println(articleComId);
        System.out.println(index);

        boolean type = Boolean.parseBoolean(map.get("type"));
        boolean b = redisUtil.sHasKey(uKey + articleComId, userId);

        Map<String, Object> reMap = new HashMap<>();
        reMap.put("index", Integer.valueOf(index));
        if (type) {
            if (!b) {
                System.out.println("点赞");
                System.out.println("ComGoodKey" + articleComId + ": " + userId);
                redisUtil.sSet(uKey + articleComId, userId);
                reMap.put("goodNum", redisUtil.hincr(goodKey, articleComId, 1));
            }
            reMap.put("isGood", true);
        } else {
            if (b) {
                System.out.println("取消点赞");
                System.out.println("ComGoodKey" + articleComId + ": " + userId);

                redisUtil.setRemove(uKey + articleComId, userId);
                reMap.put("goodNum", redisUtil.hincr(goodKey, articleComId, -1));
            }
            reMap.put("isGood", false);
        }
        return reMap;
    }

    /*================================获取文章评论点赞数==============================================*/
    public List<Integer> getPageComGoodNum(List<String> list,String key) {
        if (key.equals("artCom")) {
            key = AComGoodKey;
        }
        if (key.equals("picCom")){
            key=PComGoodKey;
        }
        List<Integer> reList = new ArrayList<>();
        for (String id : list) {
            Integer ACGoodNum = (Integer) redisUtil.hget(key, id);
            if (ACGoodNum == null) {
                ACGoodNum = 0;
            }
            reList.add(ACGoodNum);
        }
        return reList;
    }
    /*================================图片评论点赞===================================================*/

    /*=========所有文章的阅读量的sortSet===========================*/
    public List<Map<String, Object>> getRecommendArticle(long start,long end ){
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisUtil.zReverseRangeWithScores(ReadKey, start, end);
        List<Map<String, Object>> reList = new ArrayList<>();
        for (ZSetOperations.TypedTuple<Object> o : typedTuples) {
            Map<String, Object> map=new HashMap<>();
            map.put("id", o.getValue());
            map.put("readNum", o.getScore());
            reList.add(map);
//            System.out.println("getScore:"+o.getScore());
//            System.out.println("getValue:"+o.getValue());
        }
        return reList;
    }
    public Long getRecomArtSzie(){
        return redisUtil.zSize(ReadKey);
    }

    /*====================================sortSet==========================================*/
    public List<Map<String, Object>> getTopReCommArticle(String userId,long start,long end){
        List<Map<String, Object>> list = new ArrayList<>();
        String Key=uReCommArticleKey+userId;
        Set<ZSetOperations.TypedTuple<Object>> set = redisUtil.zReverseRangeWithScores(Key, start, end);
        for (ZSetOperations.TypedTuple<Object> o : set) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("articleId", o.getValue());
            map.put("readNum", o.getScore());
            list.add(map);
        }

        return list;
    }

    public List<Map<String, Object>> getTopLastlyComment(String userId,long start,long end) throws ParseException {
        List<Map<String, Object>> list = new ArrayList<>();
        String Key=uLastlyCommentKey+userId;
        Set<ZSetOperations.TypedTuple<Object>> set = redisUtil.zReverseRangeWithScores(Key, start, end);
        for (ZSetOperations.TypedTuple<Object> o : set) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("aCommentId", o.getValue());
            long time =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(o.getScore())).getTime();;
            map.put("time", time);
            list.add(map);
        }

        return list;
    }

    public void addTopLastlyComment(String userId,String commentId){
        String Key=uLastlyCommentKey+userId;
        Long rank = redisUtil.zRank(Key, commentId);
        if (rank==null){
            redisUtil.zAdd(Key, commentId, 0);
        }else if (rank.intValue()>=0){
            redisUtil.zIncrementScore(Key, commentId, 1);
        }
    }
    public void delTopLastlyComment(String userId,String commentId){
        redisUtil.zRemove(uLastlyCommentKey, commentId);
    }
}
