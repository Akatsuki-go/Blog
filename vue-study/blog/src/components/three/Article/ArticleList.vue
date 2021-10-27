<template>
  <div>
    <!--文章卡片-循环-->
    <el-card class="box-card" v-for="(item,index) in articleList" :key="item">
      <div slot="header" class="clearfix">
        <router-link v-bind:to="'/blogIndex/article/'+item.articleId" v-html="item.title">
        </router-link>
      </div>
      <div class="text" v-html="item.introduction"></div>
      <div class="clearfix">
        <span>posted @ {{ item.createTime | dateFormat }} {{item.user}} 阅读({{ readNumList[index] }}) 评论({{ item.comment }})
         <el-button type="danger" icon="el-icon-delete" circle class="artDelBtn" @click="delArt(item.articleId)"
                    v-if="isNowUserBlog()"></el-button>
        </span>
      </div>
    </el-card>

    <!--    页码-->
    <div class="block centerDiv">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="4"
        layout="prev, pager, next, jumper"
        :total="Count">
      </el-pagination>
    </div>

  </div>
</template>

<script>
export default {
  name: "ArticleList",
  data() {
    return {
      currentPage: 1,
      Count: 0,
      articleList: [],
      readNumList: [],
    }
  },
  computed:{
    utilsFun(){
      return this.isNowUserBlog()
    }
  },
  methods: {
    NowUserName(){
      return localStorage.getItem("nowUserName")
    },
    isNowUserBlog(){
      console.log("isMYblog")
      console.log(localStorage.getItem("isMyBlog"))
      return localStorage.getItem("isMyBlog")==="true"
    },
    // 页码
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
    },

    // 点击或跳转事件
    handleCurrentChange(val) {
      var that = this
      var userId = localStorage.getItem("nowUserId");

      // console.log(`当前页: ${val}`);
      this.$axios.get('/api/article/page/' + userId + '/' + val + '/4').then(
        function (res) {
          // console.log(res.data)

          that.articleList = res.data.items
          that.readNumList=res.data.readNumList
          that.Count = res.data.recordCount;
          that.currentPage = res.data.pageNo
        }
      )
    },

    //删除按钮
    delArt(articleId){
      var that=this
      // console.log()
      this.$axios.post(`/api/article/del`,{
        articleId: articleId,
        userId: localStorage.getItem("userId"),
        pageNo: this.currentPage,
        pageSize: 4,
      }).then(function (res){
        if (res.data.state){
          that.articleList = res.data.items
          that.readNumList=res.data.readNumList
          that.Count = res.data.recordCount;
          that.currentPage = res.data.pageNo
        }else {
          alert("删除文章失败");
        }

      })
    },
  },

  created() {
    console.log(this.isNowUserBlog())
    var that = this
    var userId = localStorage.getItem("nowUserId");

    this.$axios.get('/api/article/page/' + userId + '/1/4').then(
      function (res) {
        console.log('http://localhost:8090/api/article/page/' + userId + '/1/4')
        console.log(res.data)

        that.articleList = res.data.items
        that.readNumList=res.data.readNumList
        that.Count = res.data.recordCount
        that.currentPage = res.data.pageNo

      }
    )

  },


}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 90%;
  margin-top: 5%;
}

/*通用*/
a {
  text-decoration: none;
}

.router-link-active {
  text-decoration: none;
}
.artDelBtn{
  margin-left: 10%;
}
.centerDiv {
  margin-top: 25px;
  /*弹性布局*/
  display: flex;
  /*垂直居中*/
  justify-content: center;
  width: 100%;
}
</style>
