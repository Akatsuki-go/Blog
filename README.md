#文件解释
后来上传的:vue-study为Vue的前端文件，其他还是SpringBoot的文件

# 工程简介
SpringBoot的Blog项目,主要用的技术有Redis,MySQL,Jwt。
有登录注册及验证模块，文章模块，图片模块，评论模块，点赞模块，用户模块，排行榜模块，推荐模块。

# 主要模块功能
一、登录模块: 使用了JWT生成token以及token的验证。
*登录成功后生成Toekn
jWT写入userId和userName的信息
SH256
二、Redis的使用
1.Redis缓存---分布式缓存，存在阿里云服务器上 
具体来说:Redis重写实现MyBatis的二级缓存(SqlSessionFactory级别缓存，所有会话SqlSession共享)接口 org.apache.ibatis.cache。
原来的实现类是PerpetualCache，用HashMap存储，现在我用Redis来存储

实现:
<!-- 自定义mybatis二级缓存-->
<cache type="com.chen.cache.RedisCache"/>
RedisCache自定义类实现org.apache.ibatis.cache接口
· putObject(Object Key,Object value)方法


2.用到Redis的数据结构 
zset 阅读排行榜和推荐文章
set 存储用户有没有点赞
map 点赞
