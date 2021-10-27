<template>

      <el-calendar v-model="value">
      </el-calendar>
</template>

<script>
export default {
  name: "Test",
  data(){
    return{
      value: new Date()
    }
  }
}
</script>

<style>

/deep/.el-calendar-table .el-calendar-day {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  padding: 8px;
  height: 30px!important;
}

/deep/.el-button--mini, .el-button--mini.is-round {
  padding: 0px 0px!important;
}
</style>
