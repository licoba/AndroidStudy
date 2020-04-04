# Android的事件分发

废话不多说先上图

![](https://upload-images.jianshu.io/upload_images/966283-b9cb65aceea9219b.png?imageMogr2/auto-orient/strip|imageView2/2/w/885/format/webp)

![](https://upload-images.jianshu.io/upload_images/966283-d01a5845f7426097.png?imageMogr2/auto-orient/strip|imageView2/2/w/874/format/webp)
## 认识三个最主要的方法
- public boolean dispatchTouchEvent(MotionEvent ev) 
- public boolean onTouchEvent(MotionEvent event) 
- public boolean onInterceptTouchEvent(MotionEvent event) 

三个方法分别用于：**事件分发**、**事件处理**、**事件拦截**

其中前两个是View和ViewGroup都有的方法，第三个方法是ViewGroup独有的方法，在View是没有这个方法的。


**这三个方法的返回值都是boolean**

在Android中，所有的事件都是从开始经过传递到完成事件的消费，这些方法的返回值就决定了某一事件是否是继续往下传，还是被拦截了，或是被消费了。

>- dispatchTouchEvent方法用于事件的分发，Android中所有的事件都必须经过这个方法的分发，然后决定是自身消费当前事件还是继续往下分发给子控件处理。返回true表示不继续分发。返回false则继续往下分发，如果是ViewGroup则分发给onInterceptTouchEvent进行判断是否拦截该事件。
>- onTouchEvent方法用于事件的处理，返回true表示消费处理当前事件，返回false则不处理，交给子控件进行继续分发。
> - onInterceptTouchEvent是ViewGroup中才有的方法，View中没有，它的作用是负责事件的拦截，返回true的时候表示拦截当前事件，不继续往下分发，交给自身的onTouchEvent进行处理。返回false则不拦截，继续往下传。这是ViewGroup特有的方法，因为ViewGroup中可能还有子View，而在Android中View中是不能再包含子View的（iOS可以）。


## Demo程序1

这里的Demo主要用来测试单布局的事件传递，也就是从Activity传递到Button

首先在MainActivity里面重写前两个方法，然后新建一个MyButton的类继承自button，并重写这两个方法打印log，将mybutton放到activity_main中，最后在MainActivity里面给myButton设置onClick和onTouch的监听

点击按钮可以看到如下的事件分发和处理过程
![](https://pic.downk.cc/item/5e8878b0504f4bcb04eb879e.jpg)


这里需要提到的是，myButton的`onTouchListener`是在`super.dispatchTouchEvent(ev);`里面执行的，`onClickListener`是在`super.onTouchEvent(event);`里面执行的

整个事件的传递流程如下：

![](https://pic.downk.cc/item/5e888257504f4bcb04f53350.png)

---


## Demo程序2
新建一个MyLayout继承自LinearLayout，重写文章开头所提到的三个方法，然后在xml中在MyButton的外层使用MyLayout包裹住。


然后点击button，可以看到事件是从Activity->MyLayout->MyButton这样传递的

而`onInterceptTouchEvent`是在`super.dispatchTouchEvent(ev);`方法里面调用的，`onInterceptTouchEvent`默认直接返回了false，也就是不进行拦截，将事件传递给MyButton

## Case分类

- 如果在Activity的dispatchTouchEvent方法里面直接返回了true或者false，而不调用super的方法，那么事件就被直接消费掉了，不会向下传递，任何其它方法也都不会调用

- Activity在分发事件的时候正常调用super方法，接着事件会被传递到ViewGroup。这时候有3个情况：一、如果是直接返回true
，代表事件被ViewGroup消耗掉了；**二、如果是返回false，会走到上一个View的onTouchEvent方法，也就是Activity的onTouchEvent方法**，三、如果调用super方法，会走到ViewGroup的拦截方法。


- 按照正常的步骤进入ViewGroup的拦截方法里面以后，这里有两个出路：一、调用super或者返回false，表示不拦截，这个时候就会走到View的dispatchTouchEvent方法里面去；
二、如果是返回了true，代表事件被ViewGroup拦截了，这里的被拦截的事件，会交给ViewGroup自己处理，也就是会走到自己的OnTouchEvent方法里面去，如果自己处理掉，返回true，则事件消耗结束；如果是返回了false或者是调用了super方法，那么会走到Activity的事件处理方法里面去。

## 总结
- 2、dispatchTouchEvent 和 onTouchEvent 一旦return true,事件就停止传递了（没有谁能再收到这个事件）。
- dispatchTouchEvent 和 onTouchEvent 在return false的时候事件都回传给父控件的onTouchEvent处理。
- 对于dispatchTouchEvent 返回 false 的含义应该是：事件停止往子View传递和分发同时开始往父控件回溯（父控件的onTouchEvent开始从下往上回传直到某个onTouchEvent return true），事件分发机制就像递归，return false 的意义就是**递归停止然后开始回溯**。
- 对于onTouchEvent的return false，它就是不消费事件，并让事件继续往父控件的方向从下往上流动。（对比dispatchTouchEvent的returnfalse，少了一个事件传递）
- View 没有拦截器，为了让View可以把事件分发给自己的onTouchEvent，View的dispatchTouchEvent默认实现（super）就是把事件分发给自己的onTouchEvent。
- Android中事件传递按照从上到下进行层级传递，事件处理从Activity开始到ViewGroup再到View。
- 事件传递方法包括dispatchTouchEvent、onTouchEvent、onInterceptTouchEvent，其中前两个是View和ViewGroup都有的，最后一个是只有ViewGroup才有的方法。这三个方法的作用分别是负责事件分发、事件处理、事件拦截。
- onTouch事件要先于onClick事件执行，onTouch在事件分发方法dispatchTouchEvent中调用，而onClick在事件处理方法onTouchEvent中被调用，onTouchEvent要后于dispatchTouchEvent方法的调用。

【参考文章】
- [图解 Android 事件分发机制](https://www.jianshu.com/p/e99b5e8bd67b)
- [Android View事件分发测试Demo](https://www.zybuluo.com/linux1s1s/note/119018)