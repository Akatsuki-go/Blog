<template>
<div id="Side">
  <el-container>
    <!--          主体-->
    <el-main>
      <!--            文章列表-->
      <!--            <ArticleList/>-->
      <keep-alive exclude="ArticleList">
      <router-view ></router-view>
      </keep-alive>
    </el-main>


    <el-aside width="30%">
<!--        <el-calendar v-model="value">-->
<!--        </el-calendar>-->

      <!--阅读排行榜-->
      <div class="SideDiv">
        <p v-if="isNowUserBlog()">阅读排行榜</p>
        <p v-else>阅读排行榜</p>
        <ol class="Component-side">
          <li v-for="item in readList" :key="item" @click="$router.push('/blogIndex/article/'+item.articleId)" onmousemove='this.className="amove"' id="SideLi">
            {{item.title}}
          </li>
        </ol>
      </div>
      <!--            最新评论-->
      <div class="SideDiv2">
        <p v-if="isNowUserBlog">文章最近评论</p>
        <p v-else>它的文章最近评论</p>
        <ol  class="Component-side">
          <li v-for="item in commentList" :key="item" id="SideLi2">
<!--            文章:{{item.title}}<br>-->
            {{item.userName}}: {{item.content| commentLimit}}<br>
            ---{{item.createTime| dateFormat }}<br>
          </li>
        </ol>
      </div>

    </el-aside>
  </el-container>
</div>
</template>

<script>
import Article from "../three/Article/Article";

export default {
  name: "side",
  components:{
    Article,
  },
  data(){
    return{
      readList: [],
      commentList: [],
      // value: new Date(),
    }
  },
  created() {
    //侧边栏
    var that=this
    var userId=parseInt(localStorage.getItem("nowUserId"));
    // console.log(localStorage.getItem("userName"))


    //侧栏
    this.$axios.post('/api/blog/side',{
      userId: userId,
      readStart: 0,
      readLen: 5,
      commentStart: 0,
      commentLen: 5,
    }).then(function (res){
      console.log(res.data)
      that.commentList=res.data.commentList
      that.readList=res.data.readTopList
      console.log(that.readList)
    })

  },
  methods: {
    NowUserName(){
      return localStorage.getItem("nowUserName")
    },
    isNowUserBlog(){
      // console.log("isMYblog")
      // console.log(localStorage.getItem("isMyBlog"))
      return localStorage.getItem("isMyBlog")==="true"
    },
  },
}
</script>

<style scoped>
@import '../../assets/css/overAll.css';
#home {
  margin: 0 auto;
  width: 60%;
  min-width: 950px;
  padding: 30px;
  margin-top: 50px;
  margin-bottom: 50px;
  background-color: rgba(255, 255, 255, 0.2);
  box-shadow: 0 2px 6px rgb(100 100 30);

}

#blogTitle {
  color: #515151;
  font-family: "Times New Roman", Times, serif;
}

#blogSignature {
  font-size: .6rem;
}


#navigator {
  font-size: 13px;
  border-bottom: 1px solid #ededed;
  border-top: 1px solid #ededed;
  height: 50px;
  clear: both;
  margin-top: 25px;
}

#userName {
  text-align: right;
}

/*#navList {*/
/*  list-style:none;*/
/*  float: left;*/
/*}*/
.navLi {
  list-style: none;
  float: left;
}

.navA {
  display: block;
  width: 5em;
  text-align: center;
  padding-top: 10px;
  padding-right: 5px;
  text-decoration: none;
}

.navSpan {
  margin: 5px;
  display: inline-block;
  margin-top: 20px;
}


/*element-UI布局样式*/
.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  background: #99a9bf;
}

.bg-purple {
  /*background: #d3dce6;*/
}

.bg-purple-light {
  background: #e5e9f2;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #f8f9fa;
  color: #333;
  text-align: center;
  line-height: 30px;
}

.el-main {
  background-color: #F08080;
  color: #333;
  /*text-align: center;*/
  font-family: "微软雅黑", "Helvetica Neue", Helvetica, Verdana, Arial, sans-serif;
  font-size: 18px;
  line-height: 1.7;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

/*通用*/
a {
  text-decoration: none;
}

.router-link-active {
  text-decoration: none;
}
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 480px;
}
.Component-side{
  height: 200px;
  text-align: left;
}

.el-calendar-day{
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  padding: 8px;
  height: 15px!important;
  background-color: #556789;
}
.SideDiv{
  background-color:white;
  border-radius: 25px;
  border:solid #f2e7c3;
  margin: 10px 10px 10px 10px;
}
.SideDiv2{
  background-color:white;
  border-radius: 25px;
  border:solid #f2e7c3;
  margin: 10px 10px 10px 10px;
  min-height: 730px;
}
#SideLi{
  margin-top: 10px;
}
#SideLi2{
  margin-top: 20px;
}
#Side{
  overflow-y:visible!important;
  height: 1000px;
}
</style>
