原来的
.babelrc
```text
{
"presets": [
["env", {
"modules": false,
"targets": {
"browsers": ["> 1%", "last 2 versions", "not ie <= 8"]
}
}],
"stage-2"
],
"plugins": ["transform-vue-jsx", "transform-runtime"]
}
```

后端接口地址:
http://localhost:8080/api/
'http://localhost:8080/api/user/login'  映射  'http://localhost:8081/user/login'

页面路由
首页（导航栏+侧面）--添加新文章（主体）
