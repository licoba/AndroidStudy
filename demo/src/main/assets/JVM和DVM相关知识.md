# JVM和DVM相关知识
>author：licoba
>
>cerate time：2020/03/21
---
## 1、JVM和DVM介绍

JVM是 `Java Virtual Machine` 的简称，翻译成中文是Java虚拟机。

JVM是用来做什么的呢？众所周知JVM是一个用来运行java应用程序的一个平台，本质上来说还是一个软件，正是JVM支持起了java跨平台的特性，所有的java程序都是运行在jvm上的，jvm是运行在系统上的。


这里可以补充说一下C语言的编译运行步骤：C语言编译之后，会直接产生汇编语言，汇编语言是可以直接在硬件上跑的


但是Java编译后生成的是.class字节码文件，字节码文件没有办法想汇编语言那样，直接就在硬件上跑，class字节码是在jvm上跑的，需要jvm把.class字节码翻译成机器指令，机器指令运行在硬件上，这样java程序才能跑起来

前面说了C和java编译和运行之间的差别，总结起来也就发现java运行的时候会多一个步骤：

1、C语言->汇编语言->在硬件上运行<br>
2、Java语言->.class字节码文件->jvm翻译为机器指令->在硬件上运行<br>


所以，JVM的作用是：**将平台无关的.class字节码翻译成平台相关的机器码**，来实现跨平台

## 总结Q&A
1、什么是JVM？

Java Virtual Machine，Java虚拟机，是一个运行在系统层上的虚拟计算机，负责翻译Java编译好的.class字节码文件，Java程序都是运行在JVM上的

2、JVM的作用？

jvm把.class字节码翻译成机器指令，硬件可以识别机器指令，这样java程序才能跑起来；正是有了JVM，才有了java的跨平台特性。

3、JVM的类加载过程？


4、什么是DVM？


5、DVM有什么作用？


6、DVM的类加载？


7、JVM和DVM的区别？