# Android 四大组件
> Create Time : 2020/04/03
>
> Author：licoba

## 一、Activity 
Activity在Android中可以用来显示用户界面，监听用户的点击、滑动或者输入事件，并作出响应


## 二、Service
Servic提供在后台长时间运行的服务，没有用户界面，可以用来播放音乐、下载、进行复杂的计算等

#### Service的启动方式

启动方式有两种：**startService**和**bindService**

- startService：通过该方法启动service，创建者与service之间没有关联，即使创建者退出了，service也依然可以运行，创建者和service之间无法进行直接的数据交换
- bindService：通过该方法启动service，创建者和service绑定在一起，创建者一旦退出，service也就没有了，创建者和service之间可以进行数据交换

创建一个Service的步骤和创建Activity类似，首先需要创建一个MyService的类继承自Service，然后需要在Android.manifest里面注册这个服务；如果要停止service的话用stopService就好了


使用startService启动的时候，启动的过程和startActivity相似，也是用过intent来启动的。通常只需要在onCreate方法和onStartCommand方法中定义相关的业务代码就可以了。但是因为通过这个方法启动的service，创建者和service之间没有太多的关联，因此service和访问者之间也无法进行通信、交换数据 


如果需要和service交换数据，就需要使用bindService：bindService需要三个参数，其中的第二个参数是一个ServiceConnection对象，需要在Activity里面创建；销毁的时候调用unbind(conn)就可以了。另外如果多次调用bindService，也只会走一遍onBind方法的

对于Service的onBind方法，可以被当成是该service组件所返回的代理兑现，service允许客户端通过该IBinder对象来访问Service内部的数据，**也就是通过这个IBinder对象来实现与Service之间的通信的**

#### Service和IntentService的区别

- IntentService会起一个单独的worker线程，来处理所有的intent请求
- IntentService会起一个单独的worker线程来处理onHandleIntent()方法实现的代码

所以在Service里面如果直接执行耗时任务的话，可能会造成ANR，但是IntentService就不会


## 三、ContentProvider
ContentProvider用来在不同应用间进行数据交互和共享，也就是跨进程通信

## 四、BroadcastReceiver
BroadcastReceiver可以监听或接收应用或系统发出的广播消息，并做出响应，在线程和进程之间都可以进行通信，也用于在App和系统之间进行通信

**应用场景**
1. 同一 App 内部的同一组件内的消息通信（单个或多个线程之间）
2. 同一 App 内部的不同组件之间的消息通信（单个进程）;
3. 同一 App 具有多个进程的不同组件之间的消息通信;
4. 不同 App 之间的组件之间消息通信;
5. Android系统在特定情况下与App之间的消息通信，如：网络变化、电池电量、屏幕开关等。