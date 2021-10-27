<template>
  <div>
    <el-input
      type="textarea"
      autosize
      placeholder="请输入标题"
      v-model="title">
    </el-input>
    <div style="margin: 20px 0;"></div>
    <el-input
      type="textarea"
      :autosize="{ minRows: 2, maxRows: 4}"
      placeholder="请输入简介"
      v-model="introduction">
    </el-input>
    <div style="margin: 20px 0;"></div>
    <!--    富文本框-->
    <div class="edit_container edit">
      <quill-editor
        v-model="content"
        ref="myQuillEditor"
        :options="editorOption"
        @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
        @change="onEditorChange($event)">
      </quill-editor>
      <el-row>
        <el-button v-on:click="articleSub">添加文章</el-button>
<!--        <el-button type="primary">蓝色按钮</el-button>-->
<!--        <el-button type="success">绿色按钮</el-button>-->
      </el-row>
    </div>

  </div>
</template>

<script>
import {quillEditor} from "vue-quill-editor"; //调用编辑器
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

export default {
  name: "ArticleAddPage",
  components: {
    quillEditor
  },
  data() {
    return {
      title: '',
      introduction: '',
      content: '',
      editorOption: {},

    }
  },
  methods: {
    onEditorReady(editor) { // 准备编辑器

    },
    // 添加文章点击事件
    articleSub(){
      var that=this
      // console.log(this.content);
      var content = this.content.toString();
      this.$axios.post('/api/article/add',
      {
        title: this.title,
        introduction: this.introduction,
        content: this.content,
        userId: localStorage.getItem("userId")


      }).then(function (res){
        if (res.data=="200"){
          // console.log(res.data)
          that.$router.push({
            path: `/blogIndex`,
          })
        }
      })
    },
    onEditorBlur() {
    }, // 失去焦点事件
    onEditorFocus() {
    }, // 获得焦点事件
    onEditorChange() {
    }, // 内容改变事件
    // 转码
    escapeStringHTML(str) {
      str = str.replace(/&lt;/g, '<');
      str = str.replace(/&gt;/g, '>');
      return str;
    }
  },
  computed: {
    editor() {
      return this.$refs.myQuillEditor.quill;
    },
  },
  mounted() {
    let content = ''; // 请求后台返回的内容字符串
    this.str = this.escapeStringHTML(content);
  },
}

</script>

<style scoped>
.edit{
  /*overflow: scroll;*/
  height: 400px;
}
</style>
