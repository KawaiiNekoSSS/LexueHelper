## 需求

1. 乐学部分
   1. 登录
   2. 获取课程列表
   3. 获取编程题
   4. 获取用例
   5. 提交
2. 编程部分
   1. 编译运行 `g++/gcc`
   2. 比对 `fc`
   3. 对拍功能 
   4. 支持其他语言 `cyaron` 
3. 日历同步
4. GUI

## GUI设计

- 主界面
- 本地测试
- 远程测试 - 登录
- 对拍

## Module/Package

- Lexue
  - bit.lexue.network.* // 用来连接
  - bit.lexue.loader.* // 用来加载数据
  - bit.lexue.commiter.* // 用来提交数据
  - bit.lexue.handler.* // 用来组合远程数据和本地测试工具
  - bit.lexue.tester.* // JUnit5单元测试
- Local
  - bit.local.tools.* // 比对工具、对拍工具
  - bit.local.compiler.* // 编译、运行
  - bit.local.tester.* // JUnit5单元测试
- GUI
  - bit.GUI.mainscene.*
  - bit.GUI.localscene.*
  - bit.GUI.remotescene.*
  - bit.GUI.duipai.*

## 版本管理

IntelliJ 2020.2.4

JDK版本 15.0.1

javaFX版本 15.0.1

GitHub

## 分锅

- dsy GUI
- hhm other

timeline

周二 开始其他部分

周六 开始GUI

下周五 迭代第一个版本

下周六 迭代第二个版本

下周日 写文档，交

网络卡顿  下周日晚上23：50分之前

