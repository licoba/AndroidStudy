# ListView和RecycleView
> Create Time : 2020/04/03
>
> Author：licoba

## 一、ListView的优化
### 值得优化的地方
- 每次滑动显示item的时候，都会走到getView方法里面去，如果每次getView都需要用View.inflate方法去获取一个新的view，会引起内存上的增加，并且View.inflate方法是将xml文件解析成为View对象，如果item的构成比较复杂的话，这个步骤将是一个非常耗内存的操作。

- findViewById这个操作也是比较耗时的，因为这个方法要找到指定的布局文件，进行不断地解析每个节点：从最顶端的节点进行一层一层的解析查询，找到后在一层一层的返回，如果在左边没找到，就会接着解析右边，并进行相应的查询，直到找到位置。

## 优化方法
- 复用getView方法中的convertView
在getView方法中，ListView为我们提供了一个convertView对象，这个对象就是ListView的历史缓存对象，我们可以在convertView不为空的时候去复用他，而不用去new一个对象，这样可以减少内存上的损耗。

- 使用ViewHolder减少findViewById的使用

ViewHolder可以设置成为一个静态类，ViewHolder里面只用存储需要findViewById的对象；通过converview的setTag和getTag方法将view与相应的holder对象绑定在一起，避免不必要的findviewbyid操作

- item的布局层次尽量不要做太深，避免View的过度绘制

- 避免在 getView 方法中做耗时的操作，比如图片加载，可以用异步加载来完成，等待滑动完毕之后再去加载图片，这里可以用Glide来实现。在用户过度滑动ListView时停止加载item里面的图片。因为从用户的角度来讲，用户在快速滑动的时候不需要非常看清楚item里面的图片，并且加载图片是一个比较耗时的操作，特别是从网络加载图片的时候.

- 图片加载的三级缓存优化
1. 内存缓存 优先加载，速度最快
2. 本地缓存 次优先加载 速度稍快
3. 网络缓存 最后加载 速度由网络速度决定<br>
也就是优先加载内存中的图片，其次再考虑将重复的图片缓存到本地，避免加载时的网络请求，最后才考虑加载网络上缓存的图片


- 可以使用RecycleView来代替ListView，listView每次数据有变化都会调用notifyDataSetChanged来刷新整个列表，使用RecycleView可以实现item的局部刷新


- 分页加载
当数据量过大的时候，可以考虑做分页加载，来减少列表过长的时候容易出现的卡顿现象。

## 总结
- 复用convertView
- 使用ViewHolder来代替findViewById
- 异步加载图片
- 图片三级缓存
- 使用RecycleView局部刷新item
- 分页加载


## ListView和RecycleView的区别
或者说RecycleView相比ListView起来有什么优势？
1. RecyclerView的Adapter里面已经封装好了ViewHolder，不用自己写了
2. RecycleView可以提供局部刷新的方法
3. RecycleView使用了四级缓存，ListView使用了两极缓存
4. RecycleView相比起ListView起来更灵活丰富，例如动画效果、拖动效果等
5. RecycleView在嵌套滚动方面支持比较足，例如RecycleView向上滑动的时候能收起其它layout