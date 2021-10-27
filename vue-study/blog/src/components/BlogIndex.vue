<template>
  <div>
    <!--    主体----页样式-->
    <div id="home">
      <!--      头部-->
      <div id="head">
        <!--        博客标题-->
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="grid-content bg-purple"><h1 id="blogTitle" v-html="NowUserName()"></h1></div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content bg-purple"></div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content bg-purple"></div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content bg-purple">
              <div id="userName">
                用户:{{userName}}
              </div>
              <div v-if="userName" v-on:click="logout" style="text-align: right">退出</div>
              <div v-if="!isNowUserBlog()" v-on:click="returnMyBlog()" style="text-align: right">回到我的博客</div>
            </div>
          </el-col>
        </el-row>
        <div id="blogSignature"><h2>心之所向，无所不成。</h2></div>

        <el-row :gutter="20">
          <el-col :span="16">
            <div class="grid-content bg-purple Nav">
              <li class="navLi">
                <router-link to="/blogIndex" class="navA">
                  推荐文章
                </router-link>
              </li>
              <li class="navLi">
                <router-link to="/blogIndex/articleList" class="navA">
                  <div v-if="isNowUserBlog()">我的文章</div>
                  <div v-else>他的文章</div>
                </router-link>
              </li>
              <li class="navLi" v-if="isNowUserBlog()">
                <router-link to="/blogIndex/addArticle" class="navA">新文章</router-link>
              </li>
              <li class="navLi">
                <router-link to="/blogIndex/picturePage" class="navA">
                  <div v-if="isNowUserBlog()">我的图片</div>
                  <div v-else>他的图片</div>
                </router-link>
              </li>
              <li class="navLi" v-if="isNowUserBlog()">
                <router-link to="/blogIndex/mySpace" class="navA">个人空间</router-link>
              </li>
              <li class="navLi"><a href="" class="navA"></a></li>
              <li class="navLi"><a href="" class="navA"></a></li>
            </div>
          </el-col>

          <el-col :span="8">
            <div class="grid-content bg-purple">
              <span class="navSpan">阅读-50</span>
              <span class="navSpan">评论-150</span>
              <span class="navSpan">文章-150</span>
            </div>
          </el-col>
        </el-row>
<keep-alive exclude="PicturePage">
  <router-view/>
</keep-alive>

      </div>


    </div>
  </div>


</template>

<script>
import ArticleList from "./three/Article/ArticleList"
import axios from "axios";
import eleCalendar from 'ele-calendar'
import 'ele-calendar/dist/vue-calendar.css'
import Side from "./two/Side";
import router from "../router";

export default {
  name: "BlogIndex",
  data() {
    return {
      userName: '',

      // calendar: new Date(),
      // // 日历
      // datedef: [
      //   {"date": "2020-03-30", "content": null, "cid": null},
      //   {"date": "2020-03-26", "content": null, "cid": null},
      // ],
      // prop: 'date', //对应日期字段名

    }
  },
  components: {
    Side,
    eleCalendar,
  },
  beforeCreate() {

  },
  created() {
    var that = this
    this.userName = localStorage.getItem("userName")
    console.log("isMyBlog")
    console.log(localStorage.getItem("isMyBlog"))
  },
  beforeDestroy() {

  },
  methods: {
    returnMyBlog(){
      // console.log(1111111)
      localStorage.setItem("nowUserId",localStorage.getItem("userId"))
      localStorage.setItem("nowUserName",localStorage.getItem("userName"))
      this.$router.push({
        path: '/blogIndex/articleList'
      })
      // location.reload();
    },
    NowUserName(){
      return localStorage.getItem("nowUserName")
    },
    isNowUserBlog(){
      // console.log("isMYblog")
      // console.log(localStorage.getItem("isMyBlog"))
      return localStorage.getItem("isMyBlog")==="true"
    },

    logout(){
      var that=this

      // console.log()
      localStorage.removeItem("userName");
      localStorage.removeItem("userId");
      that.$router.push({
        path: `/login`,
      })
    }
  }
}
</script>

<style scoped>
#home {
  margin: 0 auto;
  width: 60%;
  min-width: 950px;
  /*height: 1300px;*/
  /*height: 1200px;*/
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
/deep/ .el-aside{
  overflow: visible!important;
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
  padding-top: 25px;
  padding-right: 5px;
  text-decoration: none;
}

.navSpan {
  background-color:white;
  border-radius: 25px;
  border:solid #0dcaf0;
  padding: 10px;
  margin-left: 30px;
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
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 30px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  /*text-align: center;*/
  font-family: "微软雅黑", "Helvetica Neue", Helvetica, Verdana, Arial, sans-serif;
  font-size: 20px;
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
.Nav{
  /*border-bottom: 1px solid #ededed;*/
  border-top: 1px solid #ededed;
}
</style>
