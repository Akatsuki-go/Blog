import Vue from 'vue'
import Router from 'vue-router'
import Main from "../views/Main";
import Login from "../components/user/Login";
import Profile from "../views/user/Profile";
import List from "../views/user/List";
import NotFound from "../views/NotFound";
import BlogIndex from "../components/BlogIndex";
import Register from "../components/user/Register";
import Test from "../components/two/Test";
import ArticleList from "../components/three/Article/ArticleList";
import AddArticlePage from "../components/three/Article/AddArticlePage";
import Article from "../components/three/Article/Article";
import Side from "../components/two/Side";
import PicturePage from "../components/PicturePage";
import MySpace from "../components/two/MySpace";
import PhotoAlbum from "../components/two/PhotoAlbum";
import Photo from "../components/two/Photo";
import Index from "../components/Index"
import axios from "axios";

Vue.use(Router)
var url = 'http://localhost:8090/api/'

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

var router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '*',
      component: NotFound
    },
    {
      path: "/login", components: () => import('../components/user/Login')
    },
    // 博客组件
    {
      /* 一级:
      导航栏+其他组件
       */
      path: "/blogIndex", component: BlogIndex,
      /*
      二级
      */
      children: [

        //   侧边栏+主体：其他组件
        {
          path: "", component: Side,
          children: [
            {
              path: "" , component: Index,
            },
            {
              path: "articleList", component: ArticleList,name: ArticleList,
            },
            {
              path: "addArticle", component: AddArticlePage,
            },
            {
              //开启自动获取路由参数
              props: true,
              // name: 'Article',
              path: "article/:articleId",component: Article,
              // component: () => import("../components/three/Article/Article"),
            },
          ]
        },
        //某相册的图片页
        {
          path: "picturePage", component: PicturePage,
          children: [
            {
              path: "", component: PhotoAlbum,
            },
            //具体图片页
            {
              path: "photo/:gourpId", component: Photo,
            },
          ]
        },

        //个人空间
        {
          path: "mySpace", component: MySpace,
        },


      ]
    },
    {
      path: "/register", component: Register
    },


    {
      path: "/test", component: Test
    },

  ],

  //
})

router.beforeEach((to, from, next) => {
  /**
   * 未登录则跳转到登录页
   * 未登录跳转到登录页,也可以通过axios的响应拦截器去实现,但是会先在当前页面渲染一下,再跳转到登录页,会有个闪动的现象
   * 这里通过路由守卫的方式,不会在当前页闪现一下,但是需要在每个路由组件添加一个是否需要登录的标识位,如本项目中的requireAuth字段
   */
  let userId = localStorage.getItem("userId");
  let userName = localStorage.getItem("userName");
  let nowUserId = localStorage.getItem("nowUserId");
  let nowUserName = localStorage.getItem("nowUserName");
  let user = JSON.parse(localStorage.getItem("user"))

  if (to.path === '/login') {
    next();
  } else {
    if (userId && userName) {
      axios({
        url: `http://localhost:8090/api/user/checkToken/${nowUserId}`,
        method: 'get',
        headers: {
          token: user.token
        }
      }).then(function (res) {

        // console.log(res.data)
        console.log("===========================================")
        console.log(localStorage.getItem("nowUserId"))
        console.log(localStorage.getItem("nowUserName"))
        console.log(localStorage.getItem("isMyBlog"))
        console.log("==========================================")
        localStorage.setItem("nowUserId",res.data.nowUserId)
        localStorage.setItem("nowUserName",res.data.nowUserName)
        localStorage.setItem("isMyBlog",res.data.isMyBlog)
        //token过期
        if (!res.data.check) {
          window.alert("用户信息过期，请重新登录");
          next({
            path: "/login"
          });
        } else {
          next()
        }
      })
    } else {
      next({
        path: "/login"
      });
    }
  }


})


export default router
