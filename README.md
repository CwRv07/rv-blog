# `RvBlog`个人博客

## 一、技术栈

### 运维框架

- **`SpringBoot`**2.6.7
- **`MybaitsPlus`**3.4.3
- **`Reids`**5.0.14
- **`MySQL`**8
- **`Tomcat`**9



### 主要工具包

- **`Lombok`**
- **`FastJSON`**
- **`JWT`**
- **`Jasypt`**



## 二、核心功能

### 2.1 游客模块

- 基础增、删、改、查（分页查询、条件查询）

### 2.2 图片模块

- 图片上传

- 图片获取

  ```markdown
  - 功能介绍：访问图片
  - 相关技术：
  	1. 静态路由映射：进行路由拦截映射到服务器本地图片存储地
  ```

### 2.3 文章模块

- 基础增、删、改、查（分页查询、条件查询、详情查询）

- 文章阅读量更新

  ```markdown
  - 功能介绍：在查询文章详情时让文章阅读量增加
  - 优化：
  	1. Redis缓存更新：原本只是读事务中添加写事务影响性能，暂时将文章阅读量缓存至Redis
  	
  	2. 读操作拦截并定时任务：保证Redis与数据库数据的一致性，服务器每晚凌晨4点将更新Redis数据至数据库中
  	
  	3.IP检测：以防用户多次查询文章详情恶意刷阅读量，将获取游客IP记录至Redis中，限制每日对同一篇文章仅可新增一次阅读量
  ```

### 2.4 类别模块、标签模块

- 部分增、删、改和基础查询

### 2.5 评论模块

- 基础增、删、改、查（多级查询）

  ```markdown
  - 功能介绍：评论存在多级结构，即评论回复功能
  - 优化：
  	1. 评论表逆范式：评论表中除了树状结构基础的父评论ID字段，新增祖父评论字段，虽然增大了冗余度，但可将评论查询从可能无限制的递归访问数据库，降低为一次数据库的访问，且易于评论构造树的处理
  ```

  

  ```markdown
  - 功能介绍：评论的新增和更新修改评论数
  - 优化:
  	1. 同上使用Redis与定时任务
  ```

  ```markdown
  - 功能介绍：评论内容支持Emjoy表情
  - 相关技术：
  	1. 修改数据库字符串格式：Emjoy使用的是Unicode码，需要四字节的储存空间，MySQL默认使用UTF-8（似乎是假的UTF-8,原UTF-8支持1-4字节，但MySQL最多为3字节），因此更换为utf8mb4编码，专门用于兼容Unicode
  ```

  

### 2.6 管理员模块

- 基础登录、注册

  ```markdown
  - 功能介绍：相关认证与权限管理功能
  - 相关技术:
  	1. 密码加密：博客采用前端混合盐进行SHA_512加密后，再由后端进行混合盐进行SHA_512的验证形式，保证安全性
  	
  	2. token：使用JWT工具类自动生成，采用混合盐的HS256加密签发算法，支持加解密；用于拦截部分请求时的身份验证（前后端对于请求均有进行验证）
  	
  	3. ThreadLocal：提供用于拦截器和请求服务事务处理时的参数传递功能，实现了每次请求的独立副本变量需求
  	
  	4. 动态路由拦截与处理：对于Vue的单页面应用开发，动态路由也能有效防止恶意访问管理权限页面，需登录后才动态增加路由供给访问
  ```



## 三、项目结构

```tex
│  pom.xml
│  rv-blog.iml
│  
├─.idea
│          
└─blog-api
    │  blog-api.iml
    │  pom.xml
    │  
    ├─src
    │  ├─main
    │  │  ├─java
    │  │  │  └─me
    │  │  │      └─rvj
    │  │  │          └─blog
    │  │  │              │  BlogApp.java
    │  │  │              │  
    │  │  │              ├─config 								#配置文件
    │  │  │              │      MybatisPlusConfig.java				#MybatisPlus（分页拦截）
    │  │  │              │      ThreadPoolConfig.java				#线程池配置
    │  │  │              │      WebMVCConfig.java
    │  │  │              │      
    │  │  │              ├─controller							#业务层（暴露接口、检测参数）
    │  │  │              │      ArticleController.java			
    │  │  │              │      CategoryController.java
    │  │  │              │      CommentController.java
    │  │  │              │      ImageController.java
    │  │  │              │      LoginController.java
    │  │  │              │      RegisterController.java
    │  │  │              │      TagController.java
    │  │  │              │      UserController.java
    │  │  │              │      
    │  │  │              ├─entity								#实体类
    │  │  │              │      Article.java
    │  │  │              │      ArticleBody.java
    │  │  │              │      ArticleTag.java
    │  │  │              │      Category.java
    │  │  │              │      Comment.java
    │  │  │              │      SysUser.java
    │  │  │              │      Tag.java
    │  │  │              │      User.java
    │  │  │              │      
    │  │  │              ├─handle								#拦截处理
    │  │  │              │      AllExceptionHandler.java			#全局报错拦截
    │  │  │              │      IPInterceptor.java					#IP检测拦截
    │  │  │              │      LoginInterceptor.java				#权限验证拦截
    │  │  │              │      
    │  │  │              ├─mapper								#数据持久层
    │  │  │              │      ArticleBodyMapper.java
    │  │  │              │      ArticleMapper.java
    │  │  │              │      ArticleTagMapper.java
    │  │  │              │      CategoryMapper.java
    │  │  │              │      CommentMapper.java
    │  │  │              │      SysUserMapper.java
    │  │  │              │      TagMapper.java
    │  │  │              │      UserMapper.java
    │  │  │              │      
    │  │  │              ├─service								#服务层
    │  │  │              │  │  ArticleBodyService.java
    │  │  │              │  │  ArticleService.java
    │  │  │              │  │  CategoryService.java
    │  │  │              │  │  CommentService.java
    │  │  │              │  │  ImageService.java
    │  │  │              │  │  LoginService.java
    │  │  │              │  │  SetTimeOutService.java				#定时任务服务
    │  │  │              │  │  SysUserService.java
    │  │  │              │  │  TagService.java
    │  │  │              │  │  ThreadService.java					#异步线程服务
    │  │  │              │  │  UserService.java
    │  │  │              │  │  
    │  │  │              │  └─impl								#服务层实现类
    │  │  │              │          ArticleServiceImpl.java
    │  │  │              │          CategoryServiceImpl.java
    │  │  │              │          CommentServiceImpl.java
    │  │  │              │          LoginServiceImpl.java
    │  │  │              │          SysUserServiceImpl.java
    │  │  │              │          TagServiceImpl.java
    │  │  │              │          UserServiceImpl.java
    │  │  │              │          
    │  │  │              ├─util									#工具类
    │  │  │              │      JWTUtils.java						#JWT工具类
    │  │  │              │      UserThreadLocal.java				#TheadLocal服务
    │  │  │              │      
    │  │  │              └─vo									#视图层
    │  │  │                  │  ArticleVo.java
    │  │  │                  │  CategoryVo.java
    │  │  │                  │  CommentVo.java
    │  │  │                  │  ErrorCode.java
    │  │  │                  │  Result.java
    │  │  │                  │  TagVo.java
    │  │  │                  │  UserVo.java
    │  │  │                  │  
    │  │  │                  └─params								#接口参数类
    │  │  │                          ArticleParams.java
    │  │  │                          CommentParams.java
    │  │  │                          PageParams.java
    │  │  │                          SysUserParams.java
    │  │  │                          TagParams.java
    │  │  │                          UserParams.java
    │  │  │                          
    │  │  └─resources
    │  │      │  application.yml
    │  │      │  
    │  │      ├─me
    │  │      │  └─rvj
    │  │      │      └─blog
    │  │      │          └─mapper
    │  │      │                  ArticleMapper.xml
    │  │      │                  TagMapper.xml
    │  │      │                  
    │  │      └─META-INF
    │  └─test
    │      └─java
    └─target
```

