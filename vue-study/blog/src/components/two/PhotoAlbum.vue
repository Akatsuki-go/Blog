<template>
  <div>
    <!--            图片相册-->
    <el-row :gutter="20">
<!--      title-->
      <el-row :gutter="20">
        <el-col :span="10"><div>
          <!-- 添加相册按钮 -->
          <el-button type="text" @click="dialogFormVisible = true" v-if="isNowUserBlog()">新建相册</el-button>
          <el-dialog title="新建相册" :visible.sync="dialogFormVisible">
            <el-form :model="form">
              <el-form-item label="相册名称" :label-width="formLabelWidth">
                <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="newAlbum">确 定</el-button>
            </div>
          </el-dialog>
        </div></el-col>
        <el-col :span="6"><div style="font-size: 20px">相册</div></el-col>
      </el-row>


      <el-col :span="8" v-for="item in albumList" :key="item">
        <div class="grid-content bg-purple">
          <div class="albumDiv">
            <!--    相册组件-->
            <div class="album">
              <!--      封面图片展示-->
              <router-link v-bind:to="'/blogIndex/picturePage/photo/'+item.id">
                <div class="photo">
                  <!-- <img src="http://127.0.0.1:8081/static/picture/test.jpg"/>-->
                  <img v-bind:src=item.src />
                </div>
              </router-link>

              <div class="albumName">
                <!--      相册名字-->
                <!--              <div >{{item.groupName}}</div>-->
                <el-button type="text" @click="open(item.id)" class="albumName2" v-if="isNowUserBlog()">{{ item.groupName }}</el-button>
                <el-button type="text"  class="albumName2" v-else>{{ item.groupName }}</el-button>
                <!--        删除-->
                <el-button type="text" @click="del(item.id)" class="delBtn" v-if="isNowUserBlog()">删除</el-button>

              </div>

            </div>
          </div>
        </div>


      </el-col>

    </el-row>

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

  </div>
</template>


<script>
export default {
  name: "PhotoAlbum",
  data() {
    return {
      currentPage: 1,
      pageSize: 9,
      Count: 0,
      albumList: [],
      //删除
      //添加相册
      dialogTableVisible: false,
      dialogFormVisible: false,
      form: {
        name: '',
      },
      formLabelWidth: '120px'
      //
    }
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
    // 页码
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
    },

    // 点击或跳转事件
    handleCurrentChange(val) {
      var that = this
      var userId = localStorage.getItem("nowUserId");

      // console.log(`当前页: ${val}`);
      this.$axios.get('/api/album/page/' + userId + '/' + val + '/9').then(
        function (res) {
          // console.log(res.data)

          that.albumList = res.data.items
          that.Count = res.data.recordCount;

          that.currentPage = res.data.pageNo
          that.Count = res.data.recordCount
          that.pageSize = res.data.pageSize
        }
      )
    },

    //相册名字
    open(id) {
      var that = this

      this.$prompt('请输入相册名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        //提交
        // this.$message({
        //   type: 'success',
        //   message: '你的邮箱是: ' + value
        // });
        this.$axios.post('/api/album/updateAlbName', {
          albumId: id,
          albumName: value,
        }).then(function () {
          {
            var currentPage = that.currentPage
            var userId = localStorage.getItem("userId")

            that.$axios.get('/api/album/page/' + userId + '/' + currentPage + '/9').then(
              function (res) {
                // console.log(userId)
                console.log(res.data)

                that.albumList = res.data.items

                that.Count = res.data.recordCount;
                that.currentPage = res.data.pageNo
              }
            )
          }
        })

      }).catch(() => {
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
        this.$axios.get('/api/album/delete/'+id).then(function (){
          var userId = localStorage.getItem("userId");
          var curPage=that.currentPage
          // console.log(that.currentPage)
          console.log("userId"  )
          console.log(userId)
          that.$axios.get('/api/album/page/' + userId + '/'+ curPage + '/9').then(
            function (res) {
              // console.log(userId)
              // console.log(res.data)

              that.albumList = res.data.items

              that.Count = res.data.recordCount;
              that.currentPage = res.data.pageNo

              console.log("ok")
            }
          )
          //end
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //新建相册
    newAlbum(){
      this.dialogFormVisible = false;
      console.log("newAlbum")
      var that=this
      var name=that.form.name
      var userId=localStorage.getItem("userId")

      this.$axios.post('/api/album/add',{
        groupName: name,
        userId: userId,
      }).then(function (){
        var userId = localStorage.getItem("userId");

        that.form.name=''

        // console.log(userId)
        this.$axios.get('/api/album/page/' + userId + '/1/9').then(
          function (res) {
            that.albumList = res.data.items
            that.Count = res.data.recordCount;
            that.currentPage = res.data.pageNo
          }
        )
      })

    },

  },

  created() {
    var that = this
    var userId = localStorage.getItem("nowUserId");
    // console.log(userId)
    this.$axios.get('/api/album/page/' + userId + '/1/9').then(
      function (res) {
        // console.log(userId)
        console.log(res.data)

        that.albumList = res.data.items

        that.Count = res.data.recordCount;
        that.currentPage = res.data.pageNo

        console.log("nowcurpage"+that.currentPage)
        // that.pageSize=res.data.pageSize
        // console.log("Count: "+that.Count)
        // console.log(that.albumList)
      }
    )
  },

}
</script>

<style scoped>


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
  font-size: 14px;
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

.el-row {
  margin-bottom: 20px;
}

。el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  background: #99a9bf;
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

.Component-side {
  height: 200px;
  text-align: left;
}

/*自定义 相册样式*/
.album {
  height: 220px;
  width: 100%;
  margin-bottom: 20px;
}

.photo {
  height: 180px;
  width: 200px;
  /*padding-right: 0px;*/
  padding-left: 40px;

}

img {
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
}

.albumName {
  width: 100%;
  position: relative;
  text-align: center;
}

.albumName2 {

}

.delBtn {
  position: absolute;
  right: 0;
}
.albumDiv{
  background-color: #f2e7c3;
}
.newAlbumt{
  width: 100%;
  height: 50px;
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
