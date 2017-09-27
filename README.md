# SmartChart

[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![MinSdk](https://img.shields.io/badge/%20MinSdk%20-%2012%2B%20-f0ad4e.svg?style=flat)](https://android-arsenal.com/api?level=12)


本项目是个人开发所用的一套常用框架，里面包含OkHttp的网络请求封装、tablayout的自定义，SmartRefreshlayout下拉上拉，activity右滑退出，以及各种布局回弹效果和android的版本适配（这些不是主要目的，主要。。。请看下面👇）。

这里主要介绍近期项目所需的表格和折线以及二维表格，有需求的同学可以不妨借鉴 😆。

### 示例

![](https://github.com/huashengzzz/SmartChart/blob/master/images/one.gif)  ![](https://github.com/huashengzzz/SmartChart/blob/master/images/two.gif)

二维表格

![](https://github.com/huashengzzz/SmartChart/blob/master/images/three.gif)

一周以及24小时天气预报

![](https://github.com/huashengzzz/SmartChart/blob/master/images/four.gif)

## 折线图和柱状图

项目中的柱状图和折线图集成的是[hellocharts-android](https://github.com/lecho/hellocharts-android)的核心代码，在此基础上增加一些修改。

### 增加特性说明：

  - X轴Y轴的标题样式和旋转角度
  - 增加label的设置方法
  - 解决scrollview的滑动冲突
  
冲突代码：
```java
 view.setOnTouchListener(new View.OnTouchListener() {
            float ratio = 1.2f; //水平和竖直方向滑动的灵敏度,偏大是水平方向灵敏
            float x0 = 0f;
            float y0 = 0f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x0 = event.getX();
                        y0 = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx = Math.abs(event.getX() - x0);
                        float dy = Math.abs(event.getY() - y0);
                        x0 = event.getX();
                        y0 = event.getY();
                        scrollView.requestDisallowInterceptTouchEvent(dx * ratio > dy);
                        break;
                }
                return false;
            }
        });
 ```
  
## 二维表格

### 使用说明：
  
  开发者只需要关心中间content部分的adapter怎么写，其余的表头部分只需要将数据传进去就可以了，剩下的数据填充及同步滑动部分将由本库自动完成。
  而且表头和下放列表的宽度是自动适应大小，取决于content部分的item的宽度。右侧上下左右滑动的列表随着list集合的增加而向右动态添加。同时支持
  下拉刷新。（待完善：左边不动两个列表也要做成动态添加）
  
### APIs：

```java
  /**
     * 设置表的标题
     */
    public void setTitle(String title);

    /**
     * 设置表标题的背景
     */
    public void setTitleBackgroundResource(int resourceId);

    /**
     * 设置表头的宽度
     */
    public void setTitleWidth(int titleWidth) ;

    /**
     * 设置表头的高度
     */
    public void setTitleHeight(int titleHeight);

    /**
     * 设置横向表头的标题
     */
    public void setRowDataList(List<String> rowDataList);

    /**
     * 设置纵向表头的内容
     */
    public void setColumnDataList(List<String> columnDataList);

    /**
     * 横向表头的分割线
     */
    public void setRowDivider(Drawable rowDivider) ;

    /**
     * 纵向表头的分割线
     */
    public void setColumnDivider(Drawable columnDivider);

    /**
     * 设置纵向表头的背景色
     * 颜色格式如：#607D8B(String)
     * 下同
     */
    public void setColumnColor(String columnColor);

    /**
     * 设置标题的背景色
     */
    public void setTitleColor(String titleColor);

    /**
     * 设置横向表头的背景色
     */
    public void setRowColor(String rowColor) ;

    /**
     * 设置纵向表头的适配器
     */
    public void setColumnAdapter(BaseAdapter columnAdapter);

    /**
     * 设置content的初始position
     * 比如你想进入这个Activity的时候让第300条数据显示在屏幕上（前提是该数据存在）
     */
    public void setInitPosition(int initPosition);

    /**
     * 返回中间内容部分的ListView
     */
    public ListView getContentListView();

    /**
     * 返回左边表头的ListView
     */
    public ListView getColumnListView() ;

    /**
     * 返回上访表头的最外层布局
     */
    public LinearLayout getRowLayout();

    /**
     * 设置是否开启下拉刷新（默认关闭）
     */
    public void setSwipeRefreshEnabled(boolean bool);

    /**
     * 设置监听
     */
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) ;

	/**
	 * 返回下拉刷新控件
	 */
    public SwipeRefreshLayout getSwipeRefreshLayout();
 ```
 
## 24小时天气变化用的hellcharts所写，7天天气预报是自定义view 
 
## 感谢
[hellocharts-android](https://github.com/lecho/hellocharts-android) 








