# snakerflow-spring-boot-starter
>  本项目是基于开源SnakerFlow工作流引擎，将其和springboot进行整合成为一个能够快速工程应用的轻量级工作流引擎组件，本着轻量、简单、灵巧理念设计，定位于简单集成，多环境支持。

>  使用定位：适用于常见的业务审批流程和状态机流转，状态机流转相对复杂、审批涉及多角色、流程模板需要灵活定义。

>  本项目也是本着为大家提供一个可以一起讨论和改进SnakerFlow的平台，不会应用于任何商业项目和商业盈利, 交流群QQ：979147234

## 核心支持
- snakerflow和springboot无缝整合
- 支持starter方式一键引入
- 支持一键初始化依赖SQL表
- ORM支持mybatis和mybatis-plus
- 兼容redis cacheManager冲突
- 目前支持H2、Mysql示例

## start
0. 快速体验
- git clone git@github.com:snakerflow-starter/snakerflow-spring-boot-starter.git
- 运行 snakerflow-spring-boot-starter-example 中的Main
- 访问 http://localhost:8080/getProcessList 初始化流程，返回process
- 访问 http://localhost:8080/start 初始化流程实例，返回order

1. 快速使用，首先引入starter依赖
```xml
<dependency>
  <groupId>com.github.snakerflow-starter</groupId>
  <artifactId>snakerflow-spring-boot-starter</artifactId>
  <version>1.0.3</version>
</dependency>
```
2. 初始化依赖数据库表
- [schema-mysql.sql](https://github.com/snakerflow-starter/snakerflow-spring-boot-starter/blob/master/snakerflow-spring-boot-starter-example/src/main/resources/db/schema-mysql.sql)
- [schema-H2.sql](https://github.com/snakerflow-starter/snakerflow-spring-boot-starter/blob/master/snakerflow-spring-boot-starter-example/src/main/resources/db/schema-h2.sql)

## 版本更新

- 2019.12.05 新增controller用于调试
- 2020.1.4 完成starter封装
- 2020.3.8 完成starter包的完全抽取，并增加测试工程，一目了然
- 2020.3.16 发布第一版starter 1.0.0到maven中央仓库
- 2020.3.16 发布1.0.1 兼容Redis cacheManager 冲突
- 2020.05.31 去掉无用依赖，快速使用体验

## 使用示例
- 可以使用参考项目实例中的 SnakerEngineFacets
- 初始化流程 SnakerEngineFacets.initFlows（已经预设了个请假流程）
- 发起一个测试流程 
```java
public Order startInstanceById(String processId, String operator, Map<String, Object> args) {
    return engine.startInstanceById(processId, operator, args);
}
```
- 流程推进
```java
public List<Task> execute(String taskId, String operator, Map<String, Object> args) {
    return engine.executeTask(taskId, operator, args);
}
```
## SnakerFlow流程引擎
> Snaker是一个基于Java的轻量级工作流引擎，适用于企业应用中常见的业务流程。本着轻量、简单、灵巧理念设计，定位于简单集成，多环境支持。

> 轻量:
snaker-core.jar大小208K，代码行数约7000行，强大的扩展支持，不依赖于具体的ORM框架

> 简单: 表设计简单，七张核心表

## 支持图形化流程设计

![njZEtI.png](https://s2.ax1x.com/2019/09/20/njZEtI.png)
![8RzktO.png](https://s1.ax1x.com/2020/03/21/8RzktO.png)

## 支持编写流程.snaker文件
```xml
<process displayName="请假流程测试" instanceUrl="/snaker/flow/all" name="leave">
<start displayName="start1" layout="24,124,-1,-1" name="start1">
<transition g="" name="transition1" offset="0,0" to="apply"/>
</start>
<end displayName="end1" layout="570,124,-1,-1" name="end1"/>
<transition g="" name="transition2" offset="0,0" to="approveDept"/>
</task>
<task assignee="approveDept.operator" displayName="部门经理审批" form="/flow/leave/approveDept" layout="272,122,-1,-1" name="approveDept" performType="ANY">
<transition g="" name="transition3" offset="0,0" to="decision1"/>
</task>
<decision displayName="decision1" expr="#day &gt; 2 ? 'transition5' : 'transition4'" layout="426,124,-1,-1" name="decision1">
<transition displayName="&lt;=2天" g="" name="transition4" offset="0,0" to="end1"/>
<transition displayName="&gt;2天" g="" name="transition5" offset="0,0" to="approveBoss"/>
</decision>
<task assignee="approveBoss.operator" displayName="总经理审批" form="/flow/leave/approveBoss" layout="404,231,-1,-1" name="approveBoss" performType="ANY">
<transition g="" name="transition6" offset="0,0" to="end1"/>
</task>
</process>
```
- snaker的流程设计可以使用snaker-web或者使用eclipse插件设计，插件在群聊里面。

## 数据库ER模型
![njAfBj.png](https://s2.ax1x.com/2019/09/20/njAfBj.png)

## 核心流程
- 发起一个流程
![ueisG6.png](https://s2.ax1x.com/2019/09/25/ueisG6.png)
- 流转动作
![ueiUr4.png](https://s2.ax1x.com/2019/09/25/ueiUr4.png)


## 更多了解
* [常用工作流对比](https://github.com/snakerflow-starter/snakerflow-spring-boot-starter/wiki/%E5%AE%A1%E6%89%B9%E6%B5%81%E8%B0%83%E7%A0%94)
* [SnakerFlow流程引擎](https://github.com/snakerflow-starter/snakerflow-spring-boot-starter/wiki/SnakerFlow%E6%B5%81%E7%A8%8B%E5%BC%95%E6%93%8E)
* [springboot整合实现](https://github.com/snakerflow-starter/snakerflow-spring-boot-starter/wiki/SnakerFlow%E5%92%8Cspringboot%E6%95%B4%E5%90%88)

## Snaker Document
 https://yunmel.gitbooks.io/snakerflow/

## 联系方式

QQ群：979147234

邮箱：zhaoguoqing360@126.com
## 点个赞

<img src="https://s1.ax1x.com/2020/06/15/NpzCRO.png"  style="zoom: 50%;" />
