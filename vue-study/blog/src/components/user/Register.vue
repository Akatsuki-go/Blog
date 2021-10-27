<template>
  <div>
    <el-row>
      <el-col :span="8">
        <div class="grid-content"></div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content">
          <el-form ref="form" :model="form" status-icon :rules="rules" label-width="80px">

            <el-form-item label="手机号码">
              <el-input v-model="form.tel" @change="tel"></el-input>
            </el-form-item>
            <el-form-item label="用户名">
              <el-input v-model="form.userName"></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="pass">
              <el-input type="password" v-model="form.pass" autocomplete="off"  @change="pass"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass">
              <el-input type="password" v-model="form.checkPass" autocomplete="off"></el-input>
            </el-form-item>


            <el-form-item label="QQ" prop="QQ">
              <el-input type="text" v-model="form.QQ" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="出生日期" prop="birDate">
              <div class="block">
                <el-date-picker
                  v-model="form.birDate"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>
              </div>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="onSubmit('form')">立即创建</el-button>
              <el-button>取消</el-button>
            </el-form-item>
          </el-form>

        </div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "register",
  data() {
    // 密码和输入密码的校验
    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.form.checkPass !== '') {
          this.$refs.form.validateField('checkPass');
        }
        callback();
      }
    };
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return {
      form: {
        tel: '',
        userName: '',
        pass: '',
        checkPass: '',
        QQ: '',
        birDate: '',
      },
      rules: {
        pass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
      },


    }
  },
  methods: {
    tel: function () {
      if (!/^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16[6-7])|(17[1-8])|(18[0-9])|(19[1|3])|(19[5|6])|(19[8|9]))\d{8}$/.test(this.form.tel)) {
        alert("请输入正确的手机号");
        this.form.tel = "";
        // return;
      }
    },
    pass: function (){
      if (!/^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{6,20}$/.test(this.form.pass)){
        alert("密码必须包括大小写字母数字");
        this.form.pass = "";
      }
    },
    onSubmit(formName) {
      const that = this
      // 密码与重复密码比较
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 提交数据
          this.$axios.post('/api/user/register',
            {
              accountName: this.form.tel,
              userName: this.form.userName,
              password: this.form.pass,
              qq: this.form.QQ,
              bir: this.form.birDate,
            }
          ).then(function (res) {
            console.log(res)
            if (res.data == "200") {
              that.$router.push({
                path: `/login`,
              })
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
      // console.log(this.form.tel)
      // console.log(this.form.userName)
      // console.log(this.form.pass)
      // console.log(this.form.QQ)
      // console.log(this.form.birDate)

    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
.el-row {
  height: 100%;
  /*外边距*/
  margin-top: 5%;
}

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
</style>
