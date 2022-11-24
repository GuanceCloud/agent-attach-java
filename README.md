# agent-attach-java

## 原理
Attach 根本原理是使用文件 Socket 通讯, 就是往一个文件中写入 `load instrument xxx.jar=params`, 再给 jvm 一个 SIGQUIT 信号, 之后 jvm 读取 socket 文件, 加载对应的 agent. 

文档位置: [观测云docs-attach-agent](https://docs.guance.com/developers/ddtrace-attach/)