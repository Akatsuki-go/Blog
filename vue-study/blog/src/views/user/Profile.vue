<template>
<div>
  个人信息
  {{id}}
</div>
</template>

<script>
export default {
  name: "Profile",
  //第二种取值方式
  // props:['id'],
  //钩子函数 过滤器
  beforeRouteEnter: (to, from, next) => {
    //加载数据
    console.log("进入路由之前")
    next(vm => {
      //进入路由之前执行getData方法
      vm.getData()
    });
  },
  beforeRouteLeave: (to, from, next) => {
    console.log("离开路由之前")
    next();
  },
  //axios
  methods: {
    getData: function () {
      this.axios({
        method: 'get',
        url: 'http://localhost:8090/static/mock/data.json'
      }).then(function (response) {
        console.log(response)
      })
    }
  }
}
</script>

<style scoped>

</style>
