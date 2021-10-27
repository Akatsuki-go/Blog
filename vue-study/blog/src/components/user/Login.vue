<template>

  <div class="text-center">

    <main class="form-signin">
      <form method="post">
        <img class="mb-4 loginPic" src="@/assets/img/Login.png" alt="404">
        <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

        <div class="form-floating">
          <input v-model="accountName" placeholder="accountName" class="form-control" @change="tel"
                 id="floatingInput" name="accountName">
        </div>
        <div class="form-floating">
          <input v-model="password" placeholder="Password" type="password" class="form-control" id="floatingPassword"
                 name="password">
        </div>

<!--        <div class="checkbox mb-3">-->
<!--          <label>-->
<!--            <input type="checkbox" value="remember-me"> Remember me-->
<!--          </label>-->
<!--        </div>-->

        <div class="checkbox mb-3">
          <label class="register">
            <router-link v-bind:to = "{ path: '/register'}" event = "click">register</router-link>
          </label>
        </div>
        <div class="w-100 btn btn-lg btn-primary" type="submit" v-on:click="doLogin">Sign in</div>
<!--        <p class="mt-5 mb-3 text-muted">&copy; 2021–2021</p>-->
      </form>
    </main>


  </div>
</template>

<script>

export default {
  data() {
    return {
      accountName: '',
      password: '',
    }
  },
  methods: {
    tel: function() {
      if (!/^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16[6-7])|(17[1-8])|(18[0-9])|(19[1|3])|(19[5|6])|(19[8|9]))\d{8}$/.test(this.accountName)) {
        alert("请输入正确的手机号");
        this.accountName = "";
        // return;
      }
    },
    doLogin() {
      const that = this

      this.$axios.post('/api/user/login',
        {
          accountName: this.accountName,
          password: this.password,
        }
      ).then(function (res) {
        let data=res.data
        if (data.userId!=null) {
          console.log(res.data)
          localStorage.setItem("userName",data.userName)
          localStorage.setItem("nowUserName",data.userName)
          localStorage.setItem("userId",data.userId)
          localStorage.setItem("nowUserId",data.userId)
          localStorage.setItem("user",JSON.stringify(res.data))
          that.$router.push({
            path: `/blogIndex`,
          })
        }else {
          alert('用户名或者密码错误')
          // that.accountName=''
          that.password=''
        }
      })
    }
  }
}
</script>

<style scoped>
@import '../../assets/css/bootstrap.min.css';
@import '../../assets/css/signin.css';

.bd-placeholder-img {
  font-size: 1.125rem;
  text-anchor: middle;
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
}

@media (min-width: 768px) {
  .bd-placeholder-img-lg {
    font-size: 3.5rem;
  }
}
.loginPic{
  height: 100px;
  width: 100px;
}
.register{
  margin-right: 250px;
}
</style>
