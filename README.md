# 核算系统

## 工具脚本

```
initdb.sh    重建数据库以及所有表
createdb.sh  创建新的数据库(删除所有表)
genmapper.sh 重新生成所有mybatis的mapper dao entity
```


## API 自动化测试

1. 进入testing 目录, ``` npm install ``` 安装 [mocha]  等测试工具. [chai]为提供测试断言语法,  [mocha] 为提供运行测试的环境. [superagent] 为http发请求库.
2. 进入testing 目录, ``` npm install -g mocha``` 全局安装mocha.
3. 进入testing 目录, 运行 ``` mocha ./api/** ``` 或 ``` npm start ``` 运行测试. 也可以单独运行某个测试文件, 例如``` mocha ./api/order/orderYing.js ```
4. 与编辑器 [Webstorm] 集成, 可以单独在 Webstorm 中点击运行单个的测试用例


[mocha]: https://mochajs.org/
[chai]: http://chaijs.com/
[superagent]: https://github.com/visionmedia/superagent
[Webstorm]: https://www.jetbrains.com/help/webstorm/mocha.html




## 测试环境地址

1. testing 环境地址 [https://hs-test.yimei180.com/web/login](https://hs-test.yimei180.com/web/login) , 用户名 15093080576, 密码 123456
2. staging 环境地址 [https://hs-staging.yimei180.com/web/login](https://hs-staging.yimei180.com/web/login) , 用户名 15093080576, 密码 123456
2. 正式环境地址 [https://hs.yimei180.com/web/login ](https://hs.yimei180.com/web/login ) , 用户名 15093080576, 密码 123456
