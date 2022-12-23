# Java Swing 学习笔记

#

``` java
/* 
 * 下面的方法定义在 JComponent 基类中
 */
// 设置文本的字体类型、样式 和 大小
void setFont(Font font)
// 设置字体颜色
void setForeground(Color fg)
// 当鼠标移动到组件上时显示的提示文本
void setToolTipText(String text)
// 设置组件的背景
void setBackground(Color bg)
// 设置组件是否可见
void setVisible(boolean visible)
// 设置组件是否为 不透明，JLabel默认为透明，设置为不透明后才能显示背景
void setOpaque(boolean isOpaque)
// 设置组件的 首选 大小
void setPreferredSize(Dimension preferredSize)
// 设置组件的 最小 大小
void setMinimumSize(Dimension minimumSize)
// 设置组件的 最大 大小
void setMaximumSize(Dimension maximumSize)
```

# 参考连接

- [Java Swing 图形界面开发（目录）](https://blog.csdn.net/xietansheng/article/details/72814492)