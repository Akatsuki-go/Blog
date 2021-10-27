<template>
  <!--    最近阅读量多文章-->
  <div class="infinite-list-wrapper" id="main">
    <!--    <ul-->
    <!--      class="list">-->
    <!--      <li v-for="i in count" class="list-item">{{ i }}</li>-->
    <!--    </ul>-->
    <div class="reComm-title">★推荐文章★</div>
    <div
      class="list"
      v-infinite-scroll="load"
      infinite-scroll-disabled="disabled">
      <el-card class="box-card" v-for="(item,index) in recomList" :key="item" style="width: 95%;margin-bottom: 10px">
        <div slot="header" class="clearfix">
          <!--          <router-link v-bind:to="`/article/${item.articleId}`" @click="changeAccessUser(item.userId)">-->
          <div @click="changeAccessUser(item.userId,item.user,item.articleId)" onmousemove='this.className="amove"'>
            {{ item.title }}
          </div>
        </div>
        <div class="text" v-html="item.introduction"></div>
        <div class="clearfix">
          <span>posted @ {{ item.createTime | dateFormat }} {{ item.user }} 阅读({{ item.readNum }}) 评论({{
              item.comment
            }})</span>
        </div>
      </el-card>
    </div>
    <p v-if="loading">加载中...</p>
    <p v-if="noMore">没有更多了</p>
  </div>

</template>

<script>

export default {
  name: "Index",
  data() {
    return {
      count: 0,
      //文章推荐
      start: 0,
      end: 4,
      loading: false,
      recArtCount: 0,
      recomList: [],
      recomSize: 4,
      pageSize: 0,
    }
  },
  computed: {
    //推荐文章
    noMore() {
      return this.recArtCount >= this.recomSizerecomSize
    },
    disabled() {
      return this.loading || this.noMore
    }
  },
  methods: {
    //访问模式：设置nowUserId，nowUserName
    changeAccessUser(userId,userName,articleId) {
      localStorage.setItem("nowUserId",userId)
      // localStorage.setItem("nowUserName",userName)
      console.log("====setNowUserID")
      this.$router.push({
        path: `/blogIndex/article/${articleId}`,
      })
    },
    //文章推荐
    load() {
      var that = this
      this.loading = true
      // if (this.start!==0){
      //   this.start+=this.pageSize;
      //   this.end+=this.pageSize;
      // }
      // console.log("start")
      // console.log(this.start)
      // console.log("end")
      // console.log(this.end)
      // setTimeout(() => {
      //   this.loading = false
      // }, 2000)
      this.$axios.post('/api/article/recom', {
        start: that.start,
        end: that.end,
      }).then(function (res) {
        // console.log("recomArticle")
        // console.log(res.data)
        that.recomList.push(...res.data)
        that.recomSize = res.data.recomSize
        that.recArtCount += res.data.length;
        that.pageSize = res.data.length;
      })
      this.$axios.get('/api/article/recomSize').then(function (res) {
        that.recomSize = res.data
        that.start = that.start + that.pageSize;
        that.end = that.end + that.pageSize;
        // console.log(res.data)
        // that.$set(that,"recomSize",res.data)
        // console.log("that.recomSize")
        // console.log(that.recomSize)
        // console.log("that.recArtCount")
        // console.log(that.recArtCount)
      })
      this.loading = false
    }
  },
  created() {

  }
}
</script>

<style scoped>
.box-card {
  width: 480px;
  border-radius: 10px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.indexDiv {
  height: 100px;
  width: 100%;
}

.aaa {
  height: 200px;
  width: 100%;
  overflow: scroll;
  background-color: gold;
}

.text {
  font-size: 14px;
}

a {
  text-decoration: none;
}

.amove {
  cursor: pointer;
}
#main{
  overflow:auto;
  height: 1000px;
}
.reComm-title{
  text-align: center;
  margin-bottom: 2%;
  /*border: 10px #0dcaf0;*/
}
</style>
