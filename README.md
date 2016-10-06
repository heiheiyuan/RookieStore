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
这是一个花了我500块的电商项目
只是为了找到一个好工作而已
如果有bug或者一些地方可以改进的,请一定要告诉我啊!!!非常感谢!


-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------
#哇咔咔 firsht issue
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

#哇咔咔 second issue
源代码里面提供了recyclerview分割线的类,但是并没有使用,于是百度了一下结果发现代码和网上的一模一样=_=
使用起来呢其实也挺简单的,不过还是遇到了一些小问题
中规中矩的按照步骤一步一步的做,结果并没有出现,后来知道需要给item布局加margin,不然在画线的时候会被item布局覆盖掉,
呈现出wtf我哪里出错的错觉,其实并没有出错but also!!!!最完美的的方式是给item加marginBottom = "1dp",给的颜色取最
底层的白色(我的是f1f1f1,不知道的拿取色器去一下就知道了),这样呈现的效果我认为是perfect!感觉我的这些small problem
被大家看到会被嘲笑...没办法啊-_-|| 智商就是这样子的啦...搞出来自认为完美的效果费了我不笑功夫呢-_-
TODO://
看了京东的客户端你会发现当他们的item超过middle position时,条目会自动上移,跳回前几个item时他又会跳回来
我以为直接做个判断然后调用scrollToPosition方法,结果没效果,因为这个方法不是很了解啊,所以还得研究一下咯


--------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------
#2016_09_28 Work Schedule
1.国际化实现---internationalization