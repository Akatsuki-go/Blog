<template>
  <div>
    <!--    <h1>具体图片</h1>-->
    <div class="demo-ima  ge__placeholder">
      <!--      <div class="block">-->
      <!--        <span class="demonstration">默认</span>-->
      <!--        <el-image :src="src"></el-image>-->
      <!--      </div>-->
      <div class="block" v-for="item in photoList" :key="item">
        <el-image v-bind:src="item.src">

          <div slot="placeholder" class="image-slot">
            加载中<span class="dot">...</span>
          </div>
        </el-image>

        <el-row :gutter="20">
          <el-col :span="22">
            <div>
              <el-button type="text" style="font-size: 20px" @click="open(nowPicId)" class="picTitle" v-if="isNowUserBlog()">
                图片名：{{ item.picture.name }}
              </el-button>
              <el-button type="text" style="font-size: 20px" class="picTitle" v-else>
                图片名：{{ item.picture.name }}
              </el-button>
              <!--        删除-->
              <el-button type="text" @click="del(item.picture.id)" class="delBtn" v-if="isNowUserBlog()">删除</el-button>
            </div>
          </el-col>
          <el-col :span="2">
          </el-col>
        </el-row>
      </div>
    </div>

    <div v-if="isNowUserBlog()">
      <!-- 添加相册按钮 -->
      <el-button type="text" @click="dialogFormVisible = true">添加图片</el-button>
      <el-dialog title="添加图片" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="图片名称" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <!--图片上传-->
          <p>图片上传</p>
          <input type="file" ref='ipt'>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="newPic">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <!--    页码-->
    <div class="block centerDiv">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        layout="prev, pager, next, jumper"
        :total="Count">
      </el-pagination>
    </div>

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
    <el-card class="box-card" v-for="(item,index) in CommentList" :key="item">
      <div slot="header" class="clearfix">
        <div class="text">{{ item.userName }}：{{ item.content }}</div>
      </div>
      <div>
        <i :class="['iconfont', 'icon-dianzan',goodList[index]?'dianzan':'']" ref="dianzan"
           @click="goodBtn(item.id,index)"></i>
        <div class="goodNum">{{goodNumList[index]}}</div>
      </div>

      <div class="clearfix showData">
        <span class="showData">---{{ item.createTime| dateFormat }}</span>
      </div>
    </el-card>

    <!--    评论分页-->
    <!--    页码-->
    <div class="block centerDiv">
      <el-pagination
        @size-change="comHandleSizeChange"
        @current-change="comHandleCurrentChange"
        :current-page.sync="comCurrentPage"
        :page-size="comPageSize"
        layout="prev, pager, next, jumper"
        :total="comCount">
      </el-pagination>
    </div>


  </div>
</template>

<script>
export default {
  name: "Photo",
  data() {
    return {
      photoList: [],
      nowPicId: 0,
      // 页码
      currentPage: 1,
      pageSize: 1,
      Count: 0,
      //添加评论
      AddComment: '',
      //评论
      CommentList: [],
      //评论分页
      comCurrentPage: 1,
      comPageSize: 4,
      comCount: 0,
      //添加
      dialogFormVisible: false,
      form: {
        name: '',
      },
      //点赞
      goodList: [],
      goodNumList: [],
    }
  },
  created() {
    var gourpId = this.$route.params.gourpId;
    var that = this
    this.getPicPage(gourpId,1,this.getPicComPage,1)
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

    async getPicPage(gourpId,val,fn,fnAgr){

     var res = await this.$axios.get(`/api/picture/page/${gourpId}/${val}/1`)
      // console.log("getPicPage")
      // console.log(res)
      this.photoList = res.data.items
      this.nowPicId = res.data.items[0].picture.id

      this.Count = res.data.recordCount;
      this.currentPage = res.data.pageNo
      this.pageSize = res.data.pageSize
      fn(fnAgr)
    },
    getPicComPage(val){
      var that=this
      var userId=localStorage.getItem("userId");
      this.$axios.get(`/api/picCom/page/${userId}/${this.nowPicId}/${val}/4`).then(function (res){
        console.log("getPicComPage")
        console.log(res)

        that.CommentList = res.data.items
        that.comCount = res.data.recordCount
        that.comCurrentPage = res.data.pageNo
        that.goodList=res.data.goodList
        that.goodNumList=res.data.goodNumList
        // console.log(that.goodNumList)
        // console.log(that.CommentList)
      })
    },

    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
    },
    // 图片 点击或跳转事件
    handleCurrentChange(val) {
      var that = this
      var gourpId = this.$route.params.gourpId;
      this.getPicPage(gourpId,val,this.getPicComPage,1)
    },
    //评论分页
    comHandleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
    },
    // 点击或跳转事件
    comHandleCurrentChange(val) {
      this.getPicComPage(val)
    },

    //评论提交
    commentSub() {
      var that = this;

      this.$axios.post('/api/picCom/add',
        {
          content: that.AddComment,
          userId: localStorage.getItem("userId"),
          forId: this.nowPicId,
        }).then(function (res) {
        that.getPicComPage(1)
      })
    },
    //点赞
    goodBtn(id, index) {
      var userId = localStorage.getItem("userId");
      var that=this
      this.$set(this.goodList, index, !this.goodList[index])
      this.$axios.post('/api/picCom/upGood', {
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
    //图片名字修改
    open(id) {
      var that = this
      var gourpId = this.$route.params.gourpId;


      console.log("id")
      console.log(id)

      this.$prompt('请输入图片名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {

        console.log(id)

        this.$axios.post('/api/picture/updatePicName', {
          picId: id,
          name: value,
        })
          .then(function () {
            var curPage = that.currentPage

            this.$axios.get('/api/picture/page/' + gourpId + '/' + curPage + '/1').then(function (res) {
              // console.log("返回")
              // console.log(res)

              that.photoList = res.data.items
              that.nowPicId = res.data.items[0].picture.id
              // console.log(that.nowPicId)

              that.Count = res.data.recordCount;
              // console.log("1"+that.currentPage)
              // console.log("2"+res.data.pageNo)
              that.currentPage = res.data.pageNo
              that.pageSize = res.data.pageSize

            })
          })
      })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });
        });

    },
    //删除按钮
    del(id) {
      this.$confirm('此操作将永久删除该相册所有图片, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var that = this
        this.$axios.get('/api/picture/delete/' + id).then(function () {
          var gourpId = that.$route.params.gourpId;
          var curPage = that.currentPage - 1
          if (curPage < 1)
            curPage = 1

          that.$axios.get('/api/picture/page/' + gourpId + '/' + curPage + '/1').then(function (res) {
            console.log(res)

            that.photoList = res.data.items
            that.nowPicId = res.data.items[0].picture.id
            // console.log("nowPicID")
            // console.log(that.nowPicId)
            that.Count = res.data.recordCount;
            that.currentPage = res.data.pageNo
            that.pageSize = res.data.pageSize

            console.log("ok")
          })
          //end
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //添加图片
    newPic() {
      this.dialogFormVisible=false

      var that=this
      // console.log(this.$refs.ipt.files[0]);

      var file=this.$refs.ipt.files[0];
      var userId=localStorage.getItem("userId")
      var gourpId = that.$route.params.gourpId;
      var formdata = new FormData();
      formdata.append('file', file);
      formdata.append('name', that.form.name);
      formdata.append('userId', userId);
      formdata.append('groupId', gourpId);
      this.$axios.post('/api/picture/up',formdata).then(function (){
        var curpage=that.currentPage
        // console.log(gourpId)

        //图片分页
        this.$axios.get('/api/picture/page/' + gourpId + "/"+curpage+"/1").then(function (res) {
          console.log(res.data)

          that.photoList = res.data.items
          that.nowPicId = res.data.items[0].picture.id
          // console.log("nowPicID")
          // console.log(that.nowPicId)

          that.Count = res.data.recordCount;
          that.currentPage = res.data.pageNo
          that.pageSize = res.data.pageSize

        })
        //评论
        this.$axios.get('/api/picCom/page/' + that.nowPicId + "/"+curpage+"/4").then(function (res) {
          // console.log(res.data)
          that.CommentList = res.data.items
          that.comCount = res.data.recordCount
          that.comCurrentPage = res.data.pageNo
          // console.log(that.CommentList)
        })
      })
    },

  },

}
</script>

<style scoped>
.picTitle {
  /*text-align: center;*/
  font-weight: bold;
  font-size: 20px;
  /*padding-left: 40%;*/
}

.showData {
  text-align: right;
}

/*上传图片*/
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.goodNum{
  font-size: 16px;
  float: right;
  margin-right: 96%;
  margin-top: 1%;
}
.dianzan {
  color: #ffc107;
}
.centerDiv {
  margin-top: 10px;
  /*弹性布局*/
  display: flex;
  /*垂直居中*/
  justify-content: center;
  width: 100%;
}
</style>
