# RookieStore
A electricity business project,continuous update...
ok...
This is a electricity business project which cost me 500RMB!!! 
Just for finding a good job.
If there are some bug or places you can make it better,please tell me!!!Thank you so much!
--------------------------------------------------------------------------------------------
菜鸟商城
一个电商项目,持续更新中...
好吧...
这是一个花了我500RMB的电商项目
只是为了找到一个好工作而已
如果有bug或者一些地方可以改进的,请一定要告诉我啊!!!非常感谢!

---------------------------------------------------------------------
# 先瞅瞅目前做的效果图咯：
</br>
<div align=center>
<img src="http://i.imgur.com/ApuJ9SJ.jpg" width=256/></br>
HomeFragment
</div>
</br>
<div align=center>
<img src="http://i.imgur.com/UtrvsHG.jpg" width=256/></br>
HotFragment
</div>
</br>
<div align=center>
<img src="http://i.imgur.com/g0n7awQ.jpg" width=256/></br>
CategoryFragment
</div>
</br>
<div align=center>
<img src="http://i.imgur.com/1D2yUyo.jpg" width=256/></br>
CartFragment
</div>

-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------
# 哇咔咔 first issue
之前添加的remadme.txt文件是直接在github上写的 也没有同步到本地,之后在提交代码的时候出现这个
error: failed to push some refs to 'https://github.com/aniruddhabarapatre/learn-rails.git'
hint: Updates were rejected because the remote contains work that you do
hint: not have locally. This is usually caused by another repository pushing
hint: to the same ref. You may want to first merge the remote changes (e.g.,
hint: 'git pull') before pushing again.
hint: See the 'Note about fast-forwards' in 'git push --help' for details.
人家也告诉咱们了 说是远程有的本地没有啊 后来才发现就是readme搞得鬼,很简单解决这个issue啦
git pull
Fix any merge conflicts, if you have a `README.md` locally
git push -u origin master
是不是so easy~自言自语好无聊╮(╯_╰)╭

# 哇咔咔 second issue
源代码里面提供了recyclerview分割线的类,但是并没有使用,于是百度了一下结果发现代码和网上的一模一样=_=
使用起来呢其实也挺简单的,不过还是遇到了一些小问题
中规中矩的按照步骤一步一步的做,结果并没有出现,后来知道需要给item布局加margin,不然在画线的时候会被item布局覆盖掉,
呈现出wtf我哪里出错的错觉,其实并没有出错but also!!!!最完美的的方式是给item加marginBottom = "1dp",给的颜色取最
底层的白色(我的是f1f1f1,不知道的拿取色器去一下就知道了),这样呈现的效果我认为是perfect!感觉我的这些small problem
被大家看到会被嘲笑...没办法啊-_-|| 智商就是这样子的啦...搞出来自认为完美的效果费了我不笑功夫呢-_-
TODO://
看了京东的客户端你会发现当他们的item超过middle position时,条目会自动上移,跳回前几个item时他又会跳回来
我以为直接做个判断然后调用scrollToPosition方法,结果没效果,因为这个方法不是很了解啊,所以还得研究一下咯

# 哇咔咔 third issue
一下午搞定了一个功能...虽然一直知道自己的智商不够高,但是今天才发现!!!这tm的也太低了吧
京东的分类界面大家都知道,左侧的lv或者rv是可以根据具体的点击item位置来自动滑动的,然后呢我用的是rv,很天真的以为直接用scrollToPosition
就可以解决,结果并没有什么卵用,他竟然不动,所以上网找了好几个人的思路:
1. 简单实现,但是不可以平滑的滑动
            mLinearLayoutManager.setStackFromEnd(true);
            mLinearLayoutManager.scrollToPositionWithOffset(pos,0);
2. 比较复杂,可以实现平滑滑动https://github.com/tyzlmjj/AndroidUI/tree/master/RecyclerViewLocation
    第二种方法相对复杂,需要判断好多种情况,并作出相应的操作,也研究了半天差不多明白了,但是用起来貌似不太适用于我,可能我不会用吧,哈哈哈哈
    所以我就自己写了一下,也并没有很复杂,只是做出了相应的判断,并且只用了smoothScrollBy这个方法,因为我发现最终生效的方法其实就是这个,那么到最后
    我写出来的方法就是这样子的,好简单的(好吧,其实是有bug的,整页条目显示出来会有一个item显示不全,导致在滑动的时候距离不准确):

```
	private void smoothMoveToPosition(int n,View view) {
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (n > firstItem) {
            //scroll up
            mLeftRecyclerView.smoothScrollBy(0,mLeftRecyclerView.getChildAt(n - firstItem).getTop());
        }else if (n <= firstItem){
            //scroll down
            if (n == 0) {
                mLeftRecyclerView.smoothScrollBy(0,-view.getHeight() * (firstItem + 1));
            }else {
                mLeftRecyclerView.smoothScrollBy(0,-view.getHeight() * (firstItem - n));
            }
        }
    }
```

# 哇咔咔 fourth issue

购物车的基本功能已经实现了,但是遇到一个bug

一开始把商品删除所有之后再次任意添加一个商品 切换到购物车界面后之前删掉的又会出现 新添加的也在里面 

通过调试和打Log之后发现这是一个逻辑上的问题:
1. CartProvider没有使用单例模式,所以在HWAdapter和CartAdapter中的两个CartProvider对象不是同一个对象,意味着两个对象中的datas中的数据并没有同步,因此在没有改造成但是模式的情况下可以再put操
作前使用listToSparse方法重新更新一下datas中的数据,并且将listTosparse方法稍作修改,当一键删除所有商品后list.size() = 0,所以里面的方法不会执行到,意味着datas不会发生变化,加个else将执行datas.clear()即可!
2. 完全可以使用单例模式来解决,这样就很轻松的达到数据同步的效果

--------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------
# 2016_09_28 Work Schedule
国际化实现
internationalization
# 2016_10_06 Work Schedule
完成分类界面
completed interface of category
# 2016_10_07 Work Schedule
完成购物车界面的自定义控件--商品数量,添加SharePreferenceUtils和JSONUtils工具类,并完成商品信息本地存储功能
completed the custome view about control the count of wares,and add the tool class of SharePreferenceUtils and JSONUtils,
one more thing is that completed the function of save wares information to local
# 2016_10_11 Work Schedule
购物车界面完成:商品价格结算 编辑 商品数量控制 数据刷新 数据信息错误BUG解决
