<template>

  <div>
    <!--    添加评论-->
    <el-input
      type="textarea"
      :autosize="{ minRows: 2, maxRows: 4}"
      placeholder="请输入评论"
      v-model="AddComment">
    </el-input>
    <el-button v-on:click="commentSub">添加评论</el-button>

    <div style="height:5%"></div>

    <!--    展示评论-->
    <el-card class="box-card" v-for="item in CommentList" :key="item">
      <div slot="header" class="clearfix">
        <div class="text">{{ item.userName }}：{{ item.content }}</div>
      </div>
      <div class="clearfix">
        <span class="showData">---{{ item.createTime| dateFormat }}</span>
      </div>
    </el-card>

    <!--    没有评论的时候-->
    <el-card class="box-card" v-if="CommentList==null">
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
        :page-size="pageSize"
        layout="prev, pager, next, jumper"
        :total="Count">
      </el-pagination>
    </div>

  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "Comment",
  data() {
    return {
      CommentList: [],
      currentPage: 1,
      Count: 0,
      pageSize: 4,
    }
  },
  created() {
    this.pageSize=this.$route.params.pageSize
    var url=this.$route.params.url

    axios.get('http://localhost:8080/api'+url+'articleC/page/' + articleId + '/1/4').then(function (res) {
      // console.log(res.data)

      that.CommentList = res.data.items
      that.Count = res.data.recordCount
      that.currentPage = res.data.pageNo
    })
  },
  methods: {
    // 页码
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
    },
    // 点击或跳转事件
    handleCurrentChange(val) {
      var that = this
      let articleId = this.$route.params.articleId;

      // console.log(`当前页: ${val}`);
      axios.get('http://localhost:8080/api/articleCommentPage/'+articleId+"/"+val+'/4').then(
        function (res){
          // console.log(res.data)

          that.articleCommentList=res.data.items
          that.Count=res.data.recordCount
          that.currentPage=res.data.pageNo
        }
      )
    },
  },
}
</script>

<style scoped>

</style>
