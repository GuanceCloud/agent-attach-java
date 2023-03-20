# agent-attach-java

## 说明
默认将 dd-java-agent.jar 动态插入正在运行的 java 服务中。 也可以使用别的 agent。

## 原理
Attach 根本原理是使用文件 Socket 通讯, 就是往一个文件中写入 `load instrument xxx.jar=params`, 再给 jvm 一个 SIGQUIT 信号, 之后 jvm 读取 socket 文件, 加载对应的 agent. 

## how to use
使用 -h 查看：
```txt
root@q-PC:agent-attach-java$ java -jar target/agent-attach-java-jar-with-dependencies.jar -h
java -jar agent-attach-java.jar [-options <dd options>]
                                [-agent-jar <agent filepath>]
                                [-pid <pid>]
                                [-displayName <service name/displayName>]
                                [-h]
                                [-help]
[-options]:
   this is dd-java-agnet.jar env, example:
       dd.agent.port=9529,dd.agent.host=localhost,dd.service=serviceName,...
[-agent-jar]:
   default is: /usr/local/ddtrace/dd-java-agent.jar
[-pid]:
   service PID String
[-displayName]:
   service name
Note: -pid or -displayName must have a non empty !!!

example command line:
java -jar agent-attach-java.jar -options 'dd.service=test,dd.tag=v1'\
 -displayName tmall.jar \
 -agent-jar /usr/local/ddtrace/dd-java-agent.jar

```

参数说明：
- "-options" ddtrace 参数 ："dd.agent.host=localhost,dd.agent.port=9529,dd.service=mytest ... "
- "-agent-jar" agent 路径 默认为：`/usr/local/ddtrace/dd-java-agent.jar`
- "-pid" 进程 pid
- "-displayName" 进程名称 比如 -displayName tmall.jar 注意 pid 和 displayName 必须有一个非空。
- "-h or -help" 帮助

## build
由于从 jdk9 开始就没有 tools.jar 文件。所以在项目目录下带上了tools文件： `lib/tools.jar` 是 jdk1.8 版本的。

本地编译：
```shell
git clone https://github.com/GuanceCloud/agent-attach-java.git
cd agent-attach-java
mvn package
```

使用其他版本 tools，请修改 pom.xml 。


## 文档
文档位置: [观测云docs-attach-agent](https://docs.guance.com/developers/ddtrace-attach/)