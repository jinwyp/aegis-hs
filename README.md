# 核算系统

## 工具脚本

```
initdb.sh    重建数据库以及所有表
createdb.sh  创建新的数据库(删除所有表)
genmapper.sh 重新生成所有mybatis的mapper dao entity
```


## API 自动化测试

1. 进入testing 目录, ``` npm install ``` 安装 [mocha]  等测试工具. [chai]为提供测试断言语法,  [mocha] 为提供运行测试的环境. [superagent] 为http发请求库.
2. 进入testing 目录, ``` npm start ``` 运行测试.



[mocha]: https://mochajs.org/
[chai]: http://chaijs.com/
[superagent]: https://github.com/visionmedia/superagent



## 测试环境环境地址

1. testing 环境地址 https://hs-test.yimei180.com/web/login
2. staging 环境地址 https://hs-staging.yimei180.com/web/login

