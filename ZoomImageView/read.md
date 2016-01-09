使用到的API以及 技巧

实现 OnGlobalLayoutListener，获得View的getViewTreeObserver，给 Observer添加回调接口
在onGlobalLayout中 获得 控件的显示宽高 ，与img比较，取得缩放的一系列对象

ScaleGestureDetector 一个触摸缩放的帮助类，可以获得 手指触摸的中心点 和缩放的 值。

在非中心点放大后缩小时，先进行边界的判断 调整matrix ，在将matrix应用，避免缩小时，图片位置不符合预期（居中）