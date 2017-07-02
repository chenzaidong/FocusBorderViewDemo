# Android TＶ　设置焦点框

１.对于ＴＶ或者投影设备来说没，各种事件的处理，主要依赖于遥控器，通过焦点框来显示焦点存在的位置
２.一般可以通过响应事件来切换控件的背景（例：drawable/selector.xml），但是影响用户体验
3.通过焦点框的动画效果，使用平移动画绘制焦点框的移动轨迹，并且焦点框随着控件的形状而变化，.动画最终状态是,焦点框从失去焦点的位置移动到获得焦点的位置,控件放大,焦点框尺寸最后变为放大后的控件尺寸.

![效果图](https://github.com/chenzaidong/FocusBorderViewDemo/blob/master/resultPic/%E6%95%88%E6%9E%9C%E5%9B%BE.gif)
