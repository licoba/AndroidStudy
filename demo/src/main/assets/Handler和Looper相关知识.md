# Handler和Looper相关知识
> author：licoba
>
> create time：2020/04/02

## Handler的工作原理
Handler提供了一种机制，这种机制可以让我们在子线程中处理耗时的任务，在主线程中来更新UI


Handler 工作流程包括 Handler、Looper、Message、MessageQueue 四个部分

1. Message：Message是在线程之间传递的消息，它可以在内部携带少量的数据，用于线程之间交换数据
2. Handler：它主要用于发送和处理消息的发送消息一般使用sendMessage()方法
3. MessageQueue：消息队列，它主要用于存放所有通过Handler发送的消息，这部分的消息会一直存在于消息队列中，等待被处理
4. Looper：每个线程通过Handler发送的消息都保存在MessageQueue中，每当Looper发现Message Queue中存在一条消息，就会调用looper()方法将它取出，并传递到Handler的handleMessage()方法中。



## 一个线程可以有多少个handler，多少个looper？
- 一个线程只能有一个looper与之绑定，looper持有messagequeue，所以也只能有一个messagequeue
- 一个线程可以创建多个handler

## 怎么在子线程创建handler？
可以在子线程创建handler的。

1. 首先需要在子线程里先调用Looper.prepare()
2. new一个Handler
3. 在最后调用Looper.loop()方法

另外，在主线程可以直接使用Handler是因为ActivityThread帮我们自动初始化了looper，并在最后调用了Looper.loop方法

## 既然一个looper可以对应多个handler，那么looper是怎么进行区分不同的handler的？

handler在sendmessage的时候，会指定message31的target，也就是将当前的handler赋值给message对象，这样在handlermessage的时候，就可以根据不同的target来区分不同的handler。+