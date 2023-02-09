# BigLearn

## 四川省青年大学习辅助工具

### 如何使用

> + 克隆后通过编辑器启动项目
> + 在微信上访问https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7e7fdbc0cc711044&redirect_uri=https://dxx.scyol.com/v_prod6.0/&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1  (授权后该页面为404页面)
> + 复制URL，注:此URL参数必须含有code参数,例如:https://dxx.scyol.com/v_prod6.0/?code=091xxxxxx&state=123
> + 通过post请求访问http://127.0.0.1:8080/getLearningToken ,此访问请求至少要包含两个请求头键值对如下:

```json
{
    "code":"就是你刚刚复制的URL链接",
    "配置文件中的inhead的值":"ID,此ID值为你自己指定"
}
```

> + 以上操作只需要在用户第一次访问时做或者在接口提示token过期的时候做



每周学习的时候只需要通过GET请求访问http://127.0.0.1:8080/learning ,请求头至少包含下列元素:

```json
{
    "配置文件中的inhead的值":"ID,此ID值为你自己指定"
}
```

成功的响应里面有成功信息和学习第几期的说明

### 配置文件以及说明

+ inhead:用于在request中取唯一标识的标识名

+ 其他说明:

  + 使用了jsoup和fastjson2作为请求和解析的框架,SpringBoot版本是2.7.0,JDK版本至少为11(可以更改)

  + 主要代码是LearnService.java，包含了请求接口等
  + code中传的参数可以不仅仅是URL，只要包含网址以及参数就好了
