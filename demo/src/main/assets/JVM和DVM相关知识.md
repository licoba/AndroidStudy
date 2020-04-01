# JVM和DVM相关知识
>author：licoba
>
>cerate time：2020/03/21

## 1、JVM和DVM介绍

### JVM
JVM是 `Java Virtual Machine` 的简称，翻译成中文是Java虚拟机。

JVM是用来做什么的呢？众所周知JVM是一个用来运行java应用程序的一个平台，本质上来说还是一个软件，正是JVM支持起了java跨平台的特性，所有的java程序都是运行在jvm上的，jvm是运行在系统上的。


这里可以补充说一下C语言的编译运行步骤：C语言编译之后，会直接产生汇编语言，汇编语言是可以直接在硬件上跑的


但是Java编译后生成的是.class字节码文件，字节码文件没有办法想汇编语言那样，直接就在硬件上跑，class字节码是在jvm上跑的，需要jvm把.class字节码翻译成机器指令，机器指令运行在硬件上，这样java程序才能跑起来

前面说了C和java编译和运行之间的差别，总结起来也就发现java运行的时候会多一个步骤：

1、C语言->汇编语言->在硬件上运行<br>
2、Java语言->.class字节码文件->jvm翻译为机器指令->在硬件上运行<br>


所以，JVM的作用是：**将平台无关的.class字节码翻译成平台相关的机器码**，来实现跨平台

JVM基于栈来设计的，这里的栈，指的是虚拟机栈，是JVM内存中的一块区域


> JVM 中的内存划分

JVM分为五个区域（主要记住两个栈和一个堆），虚拟机栈、本地方法栈、程序计数器、方法区和堆

其中前面三个是线程私有的，左边两个是线程共享的

### DVM 
DVM是`Dalvik Virtual Machine`的简称，dalvik是一种虚拟技术，翻译过来就是Android虚拟机，Android上的所有程序都是运行在DVM上的，每个Android应用进程对应着一个独立的Dalvik虚拟机实例并在其解释下执行。

每一个VM实例在linux中又是一个单独的进程，所以可以认为是同一个概念。运行在自己的DVM进程之中，不同的app不会相互干扰，且不会因为一个DVM的崩溃导致所有的app进程都崩溃。

DVM不是执行.class字节码文件，而是执行.dex文件，.dex（Dalvik Executable），也就是Dalvik可执行文件，可以在DVM上跑的一种格式

.class文件存在很多的冗余信息，DVM里面有一块区域（dex工具）专门负责.class文件的冗余东西，并将所有.class文件打包成dex文件，用与在DVM上执行
对比起java的执行步骤：
1、Java语言->.class字节码文件->jvm翻译为机器指令->在硬件上运行<br>
2、Java语言->.class字节码文件->将class文件打包.dex文件->DVM执行dex文件<br>

## 总结Q&A
#### 1、什么是JVM？

Java Virtual Machine，Java虚拟机，是一个运行在系统层上的虚拟计算机，负责翻译Java编译好的.class字节码文件，Java程序都是运行在JVM上的

#### 2、JVM的作用？

jvm把.class字节码翻译成机器指令，硬件可以识别机器指令，这样java程序才能跑起来；正是有了JVM，才有了java的跨平台特性。

#### 3、JVM的类加载过程？


#### 4、什么是DVM？
DVM是`Dalvik Virtual Machine`的简称，dalvik是一种虚拟技术，翻译过来就是Android虚拟机，Android上的所有程序都是运行在DVM上的，DVM运行的是.dex文件

#### 5、DVM有什么作用？
DVM执行.class文件打包而成的.dex文件

#### 6、DVM的类加载？


#### 7、JVM和DVM的区别？
- JVM是基于栈的虚拟机(Stack-based)，DVM是基于寄存器架构，寄存器是一块内存中的独立区域，这样DVM的速度就会领先于JVM，更适合移动设备   
- JVM机运行java字节码(.class文件)，DVM运行的是其专有的文件格式.Dex文件



#### 8、为什么JVM设计基于栈架构，DVM基于寄存器架构？

> 为什么JVM设计基于栈架构

a.基于栈架构的指令集更容易生成<br>
b.可移植性。考虑到JVM使用的场合大多是pc和服务器，栈架构可以自由分配实际的寄存器，这样的可移植性比较高，也符合java的设计理念(一次编写，处处运行)。

> DVM为什么基于寄存器：

**a.移动端的处理器绝大部分都是基于寄存器架构的。**<br>
b.栈架构中有更多的指令分派和访问内存，这些比较耗时，相对来认为dvm的执行效率更高一些。<br>
c.DVM就是为android运行而设计的，无需考虑其他平台的通用。<br>

----
【参考文章】

https://www.jianshu.com/p/5601678b7a2e