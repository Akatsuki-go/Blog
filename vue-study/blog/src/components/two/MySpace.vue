<template>
  <div>
    <div class="spaceSoild"></div>
    <div class="centerDiv"><h2 class="title">个人空间</h2></div>
    <!--  个人信息 修改资料-->
    <div class="myspace-div1">
      <!--        <el-col :span="3">-->
      <!--          <div class="grid-content">-->
      <!--          </div>-->
      <!--        </el-col>-->
      <div class="grid-content userMessage">
        用户名：
        <div class="text">{{ userMessage.userName }}</div>
      </div>
      <div class="grid-content userMessage">
        生日：{{ userMessage.bir | dateFormat2 }}
      </div>
      <div class="grid-content userMessage">
        QQ:{{ userMessage.qq }}
      </div>
      <!--        <el-col :span="6">-->
      <!--          <div class="grid-content bg-purple"></div>-->
      <!--        </el-col>-->
    </div>
    <!-- 用户的评论-->
    <div class="myspace-div" style="background-color: salmon">
      <div class="centerDiv"><h3 class="title">用户最近对文章的评论</h3></div>
      <div class="outCenterDiv1">
        <div class="outCenterDiv2">
          <!--    展示评论-->
          <el-card class="box-card" v-for="item in ACommentList" :key="item">
            <div slot="header" class="clearfix">
              <div class="text">对【{{item.title}}】的评论: {{ item.content }}</div>
            </div>
            <div class="clearfix">
              <span class="showData">---{{ item.createTime| dateFormat }}</span>
            </div>
          </el-card>
        </div>
      </div>

      <!--    页码-->
      <div class="centerDiv">
        <div class="block">
          <el-pagination
            @size-change="AcomHandleSizeChange"
            @current-change="AcomHandleCurrentChange"
            :current-page.sync="AcomCurrentPage"
            :page-size="AcomPageSize"
            layout="prev, pager, next, jumper"
            :total="AcomCount">
          </el-pagination>
        </div>
      </div>
    </div>
    <!--    没有评论的时候-->
    <!--      <el-card class="box-card" v-if="ACommentList">-->
    <!--        <div slot="header" class="clearfix">-->
    <!--          <div class="text">暂时没有评论</div>-->
    <!--        </div>-->
    <!--        <div class="clearfix">-->
    <!--          <span></span>-->
    <!--        </div>-->
    <!--      </el-card>-->


    <!--  最近评论      评论列表(删除)      具体页面-->
    <div class="myspace-div" style="background-color: darkcyan">
      <div class="centerDiv"><h3 class="title">用户最近对图片的评论</h3></div>

      <div class="outCenterDiv1">
        <div class="outCenterDiv2">
          <!--    展示评论-->
          <el-card class="box-card" v-for="item in PCommentList" :key="item">
            <div slot="header" class="clearfix">
              <div class="text">对【{{item.name}}】的评论:{{ item.content }}</div>
            </div>
            <div class="clearfix">
              <span class="showData">---{{ item.createTime| dateFormat }}</span>
            </div>
          </el-card>
          <!--    没有评论的时候-->
          <!--      <el-card class="box-card" v-if="PCommentList">-->
          <!--        <div slot="header" class="clearfix">-->
          <!--          <div class="text">暂时没有评论</div>-->
          <!--        </div>-->
          <!--        <div class="clearfix">-->
          <!--          <span></span>-->
          <!--        </div>-->
          <!--      </el-card>-->
          <!--    页码-->
          <div class="block centerDiv">
            <el-pagination
              @size-change="PcomHandleSizeChange"
              @current-change="PcomHandleCurrentChange"
              :current-page.sync="PcomCurrentPage"
              :page-size="PcomPageSize"
              layout="prev, pager, next, jumper"
              :total="PcomCount">
            </el-pagination>
          </div>
        </div>
      </div>


    </div>
    <!--    &lt;!&ndash;  图片      最近图片      图片列表(删除，相册，图片重命名)&ndash;&gt;-->
    <!--    <div class="myspace-div" style="background-color: darkkhaki"></div>-->

  </div>
</template>

<script>
export default {
  name: "MySpace",
  data() {
    return {
      userMessage: {},
      ACommentList: [],
      PCommentList: [],
      //文章评论
      AcomCurrentPage: 1,
      AcomPageSize: 3,
      AcomCount: 0,
      //图片评论
      PcomCurrentPage: 1,
      PcomPageSize: 3,
      PcomCount: 0,
    }
  },
  created() {
    var that = this
    var userId = localStorage.getItem("userId")
    this.$axios.get('/api/user/userMessage/' + userId).then(function (res) {
      // console.log(res.data)
      that.userMessage = res.data
    })
    //用户最近的文章
    this.getArtComLastlyPage(1)
    //用户的图片对图片的评论
    this.$axios('/api/picCom/lately/' + userId + "/1/3").then(function (res) {
      console.log(res.data)
      that.PCommentList = res.data.items
      that.PcomCount = res.data.recordCount
      that.PcomCurrentPage = res.data.pageNo
    })
  },
  methods: {
    //请求用户最近评论页
    getArtComLastlyPage(pageNo){
      var that = this
      var userId = localStorage.getItem("userId")
      this.$axios({
        methods: "get",
        url: "/api/articleC/lately",
        params:{
          userId: userId,
          pageNo: pageNo,
          pageSize: 3,
        }
      }).then(function (res) {
        that.ACommentList = res.data.items
        that.AcomCount = res.data.recordCount
        that.AcomCurrentPage = res.data.pageNo
      })
    },
    //文章评论
    AcomHandleSizeChange(val) {
    },
    AcomHandleCurrentChange(val) {
      this.getArtComLastlyPage(val)
    },
    //图片评论
    PcomHandleSizeChange(val) {
    },
    PcomHandleCurrentChange(val) {
      var that = this
      var curPage = that.PcomCurrentPage
      var userId = localStorage.getItem("userId")
      this.$axios.get('/api/picCom/lately/' + userId + '/' + curPage + '/3').then(function (res) {
        console.log(res.data)
        that.PCommentList = res.data.items
        that.PcomCount = res.data.recordCount
        that.PcomCurrentPage = res.data.pageNo
      })
    },
  },
}
</script>

<style scoped>
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
  background: #0dcaf0;
}

.bg-purple {
  background: #d3dce6;
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

.box-card {
  margin-bottom: 50px;
}

.userMessage {
  background-color: #fff;
  flex: 1;
  width: 150px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: nowrap;
  margin: 0 20px;
}

.myspace-div1 {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 100px;
  background-color: #0dcaf0;
  margin-bottom: 2%;
  border-radius: 10px;
}

.myspace-div {
  margin-bottom: 2%;
  height: 100%;
  width: 100%;
  border-radius: 10px;
}

.centerDiv {
  /*弹性布局*/
  display: flex;
  /*垂直居中*/
  justify-content: center;
  width: 100%;
}

.outCenterDiv1 {
  display: flex;
  justify-content: center;

}

.outCenterDiv2 {
  width: 80%;
}

.spaceSoild {
  border-top: 1px solid #f2e7c3;
}
</style>
