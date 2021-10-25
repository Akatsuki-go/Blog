只有涉及到修改redis和mysql的修改数据操作，才需要注意mysql和redis数据一致性问题
1.删除文章
删除redis的阅读量 uLately+'articleId'
删除mysql文章 article
失败时返回

2.排行榜
zset        reCommArticle+'userId'
key:articleId S:readNum

zset        lastlyCommentUser+'userId'
key:commentId S:time
