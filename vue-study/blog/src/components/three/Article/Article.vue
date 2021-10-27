<template>
  <div>
    <!--  标题-->
    <div v-html="title"></div>
    <!--  简介-->
    <p>简介</p>
    <div v-html="introduction"></div>
    <!--  内容-->
    <p>内容</p>
    <div v-html="content"></div>
    <div><p>posted @ {{ createTime| dateFormat }} {{ author }} 阅读({{ readNum }}) 评论({{ comment }})</p></div>

    <!--    添加评论-->
    <el-input
      type="textarea"
      :autosize="{ minRows: 2, maxRows: 4}"
      placeholder="请输入评论"
      v-model="AddComment">
    </el-input>
    <el-button v-on:click="commentSub">添加评论</el-button>

    <div style="height:5%"></div>

    <div style="height: 500px;">
      <!--    展示评论-->
      <el-card class="box-card" v-for="(item,index) in articleCommentList" :key="item">
        <div slot="header" class="clearfix">
          <div class="text">{{ item.userName }}：{{ item.content }}</div>
        </div>
        <div>
          <i :class="['iconfont', 'icon-dianzan',goodList[index]?'dianzan':'']" ref="dianzan"
             @click="goodBtn(item.id,index)"></i>
          <div class="goodNum">{{ goodNumList[index] }}</div>
        </div>

        <div class="clearfix showData">
          <span class="showData">---{{ item.createTime| dateFormat }}</span>
        </div>
      </el-card>

      <!--    没有评论的时候-->
      <el-card class="box-card" v-if="articleCommentList==null">
        <div slot="header" class="clearfix">
          <div class="text">暂时没有评论</div>
        </div>
        <div class="clearfix">
          <span></span>
        </div>
      </el-card>

      <!--    页码-->
      <div class="block">
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
  </div>
</template>

<script>

export default {
  //自动获取路由参数
  props: {
    articleId: {
      required: 'required',
      type: String
    }
  },
  name: "Article",
  data() {
    return {
      title: '',
      introduction: '',
      content: '',
      author: '',
      readNum: '',
      comment: '',
      createTime: '',
      AddComment: '',
      // 页码
      currentPage: 1,
      Count: 0,
      articleCommentList: [],
      //点赞
      goodList: [],
      goodNumList: [],
    }
  },
  //路由改变后的
  watch: {
    articleId(val, oldval) {
      //点击标题---具体文章
      console.log("this.$route.params.articleId")
      console.log(this.$route.params.articleId)
      var that = this
      var articleId = val;
      var userId = localStorage.getItem("userId")
      this.$axios.get('/api/article/' + userId + '/' + articleId).then(function (res) {
        // console.log(res.data)

        console.log(res.data)

        that.title = res.data.article.title
        that.introduction = res.data.article.introduction
        that.content = res.data.article.content
        that.author = res.data.article.userName
        that.readNum = res.data.article.readNum
        that.createTime = res.data.article.createTime
      })

      //文章的评论
      this.$axios.get('/api/articleC/page/' + userId + '/' + articleId + '/1/4').then(function (res) {
        // console.log(res.data)

        that.articleCommentList = res.data.items
        that.Count = res.data.recordCount
        that.currentPage = res.data.pageNo

      })
    }
  },
  created() {
    //点击标题---具体文章
    // console.log(this.$route.params.articleId)
    var that = this
    var articleId = this.articleId;
    var userId = localStorage.getItem("userId")

    console.log("articleId")
    console.log(articleId)
    this.$axios.get('/api/article/' + userId + '/' + articleId).then(function (res) {
      console.log(res.data)

      that.title = res.data.article.title
      that.introduction = res.data.article.introduction
      that.content = res.data.article.content
      that.author = res.data.article.userName
      that.readNum = res.data.article.readNum
      that.createTime = res.data.article.createTime

    })

    //文章的评论
    this.$axios.get('/api/articleC/page/' + userId + '/' + articleId + '/1/4').then(function (res) {
      console.log("AC page")
      console.log(res.data)

      that.articleCommentList = res.data.items
      that.Count = res.data.recordCount
      that.currentPage = res.data.pageNo
      that.goodList = res.data.goodList;
      that.goodNumList = res.data.goodNumList
      // console.log(that.goodList)
    })

  },
  methods: {
    goodBtn(id, index) {
      var userId = localStorage.getItem("userId");
      var that = this
      this.$set(this.goodList, index, !this.goodList[index])
      this.$axios.post('/api/articleC/upGood', {
        userId: userId,
        ComId: id,
        type: this.goodList[index],
        index: index,
      }).then(function (res) {
        console.log("goodBtn res")
        console.log(res);
        that.$set(that.goodList, res.data.index, res.data.isGood);
        that.$set(that.goodNumList, res.data.index, res.data.goodNum);
      })
    },

    commentSub() {
      let that = this;
      let articleId = this.articleId;

      this.$axios.post('/api/articleC/add',
        {
          content: that.AddComment,
          userId: localStorage.getItem("userId"),
          forId: articleId,
        }).then(function (res) {
        that.AddComment = ''
        that.artCommentPage(1);
      })
    },

    /*文章评论分页*/
    // 页码
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
    },
    // 点击或跳转事件
    handleCurrentChange(val) {
      this.artCommentPage(val)
    },
    //请求文章评论分页
    artCommentPage(pageNo) {
      var that = this
      var articleId = this.articleId;
      var userId = localStorage.getItem("userId")

      // console.log(`当前页: ${val}`);
      this.$axios.get('/api/articleC/page/' + userId + '/' + articleId + '/' + pageNo + '/4').then(
        function (res) {
          // console.log(res.data)

          that.articleCommentList = res.data.items
          that.Count = res.data.recordCount
          that.currentPage = res.data.pageNo
          that.goodList = res.data.goodList;
          that.goodNumList = res.data.goodNumList
          console.log("跳转 goodList")
          console.log(that.goodList)
        }
      )
    },
  },
}
</script>

<style scoped>
.dianzan {
  color: #ffc107;
}

.box-card {
  width: 90%;
  margin-top: 5%;
}

.text {
  font-size: 20px;
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

.showData {
  text-align: right;
  font-size: 20px;
}

.goodNum {
  font-size: 16px;
  float: right;
  margin-right: 92%;
  margin-top: 2%;
}
</style>
