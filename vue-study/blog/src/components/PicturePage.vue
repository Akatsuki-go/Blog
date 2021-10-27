<template>
  <div>
<!--    <h1>Album</h1>-->

    <el-container>
      <!--          主体-->
      <el-main>
        <keep-alive exclude="PhotoAlbum">
          <router-view/>
        </keep-alive>
      </el-main>

<!--      <el-aside width="30%">-->

<!--        &lt;!&ndash;     图片点赞数top排行榜&ndash;&gt;-->
<!--        <div>-->
<!--          <p>阅读排行榜</p>-->
<!--          <ol class="Component-side">-->
<!--            <li v-for="item in goodList" :key="item">-->
<!--              <router-link v-bind:to="'/article/'+item.pictureId" v-html=""></router-link>-->
<!--            </li>-->
<!--          </ol>-->
<!--        </div>-->
<!--        &lt;!&ndash;    最新评论&ndash;&gt;-->
<!--        <div>-->

<!--        </div>-->

<!--      </el-aside>-->
    </el-container>
  </div>
</template>

<script>

export default {
  name: "PicturePage",
  data() {
    return{
      // goodList:[],
    }
  },

  created() {

  }
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
  height: 200px;
  width: 200px;
  margin-bottom: 50px;
}

.photo {
  height: 150px;
  width: 180px;
}

img {
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
}
</style>
