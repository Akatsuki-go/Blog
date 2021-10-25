1.文章阅读量
key-map
文章id

2.点赞
已经点赞的用户集合


redis表
用户已阅读表set
key-uLately+'articleId'
value-userId

文章阅读表
key-articleReadNum
map: key-articleId
     value-readNUm

用户有无点赞--文章评论 set
key-userArticComGood+'articleComId'
value: userId

用户有无点赞--图片评论 set
key-userToArticComGood
alue: userId


文章评论点赞数表
key-articleGood
map: key-articleCommentId
     value-readNUm

图片评论点赞数表
key-articleGood
map: key-pictureCommentId
     value-readNUm

userId articleId,pirtureId 
isGood=

sorted set
用户阅读排行榜
最近评论

