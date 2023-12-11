# agent-attach-java

## 说明

默认将 dd-java-agent.jar 动态插入正在运行的 java 服务中。 也可以使用别的 agent。

## 原理

Attach 根本原理是使用文件 Socket 通讯, 就是往一个文件中写入 `load instrument xxx.jar=params`, 再给 jvm 一个 SIGQUIT 信号,
之后 jvm 读取 socket 文件, 加载对应的 agent.

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

[-download]
    guance ddtrace: https://static.guance.com/dd-image/dd-java-agent.jar
example command line:
java -jar agent-attach-java.jar -options 'dd.service=test,dd.tag=v1'\
 -displayName tmall.jar \
 -agent-jar /usr/local/ddtrace/dd-java-agent.jar

```

参数说明：

- "-options" ddtrace 参数 ："dd.agent.host=localhost,dd.agent.port=9529,dd.service=mytest ... "
- "-agent-jar" agent 路径 默认为：`/usr/local/ddtrace/dd-java-agent.jar`
- "-pid" 进程 pid
- "-displayName" 进程名称 比如 `-displayName tmall.jar` 注意 pid 和 displayName 必须有一个非空。
- "-download" 观测云版本的 ddtrace 地址: `https://static.guance.com/dd-image/dd-java-agent.jar`
- "-h or -help" 帮助

## build

由于从 jdk9 开始就没有 tools.jar 文件。所以在项目目录下带上了tools文件： `lib/tools.jar` 是 jdk1.8 版本的。

## 建议本地编译：


JDK 版本 1.8 和 1.8 以上不可以交叉使用。

如使用发布版本 请使用相应的 [releases 版本](https://github.com/GuanceCloud/agent-attach-java/releases){:target="_blank"}

***建议下载源码*** 并编译：

```shell
git clone https://github.com/GuanceCloud/agent-attach-java
```

如果是 JDK 1.8 版本，修改配置文件 pom.xml:

```xml
<!--将版本修改为 1.8 -->
    <configuration>
      <source>1.8</source>
      <target>1.8</target>
    </configuration>
    
    <!--将下面的注释放开，并将 tools.jar 注释掉 !!!-->
    <dependency>
      <groupId>io.earcam.wrapped</groupId>
      <artifactId>com.sun.tools.attach</artifactId>
      <version>1.8.0_jdk8u131-b11</version>
      <scope>compile</scope>
      <type>jar</type>
    </dependency>
```

如果版本是 JDK 9、11、17 使用以下配置 pom.xml:

```xml
<!--将目标版本修改为指定的版本-->
    <configuration>
        <source>11</source>
        <target>11</target>
    </configuration>

<!--    <dependency>
      <groupId>io.earcam.wrapped</groupId>
      <artifactId>com.sun.tools.attach</artifactId>
      <version>1.8.0_jdk8u131-b11</version>
      <scope>compile</scope>
      <type>jar</type>
    </dependency>-->
    <dependency>
      <groupId>com.sun</groupId>
      <artifactId>tools</artifactId>
      <version>1.8.0</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/lib/tools.jar</systemPath>
    </dependency>
```

```shell
mvn package
# 使用 target/agent-attach-java-jar-with-dependencies.jar
rm -f target/agent-attach-java.jar
mv target/agent-attach-java-jar-with-dependencies.jar agent-attach-java.jar
```

## 文档

文档位置: [观测云docs-attach-agent](https://docs.guance.com/developers/ddtrace-attach/)