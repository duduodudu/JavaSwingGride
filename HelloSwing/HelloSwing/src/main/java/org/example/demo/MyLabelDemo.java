package org.example.demo;

import org.example.utils.MyDemoUtils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MyLabelDemo extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.show("JLabel", new MyLabelDemo());
    }

    public MyLabelDemo() {
        // https://blog.csdn.net/xietansheng/article/details/74362076
        JLabel label = new JLabel("标签主要用于展示 文本 或 图片，也可以 同时显示文本和图片。");
        add(label);

        JLabel label1 = new JLabel("背景颜色");
        label1.setOpaque(true);
        label1.setBackground(Color.ORANGE); // label 设置背景颜色的时候需要设置透明 setOpaque(true)
        label1.setPreferredSize(new Dimension(80, 40));
        add(label1);

        JLabel label2 = new JLabel("图片和文字");
        // 加载图片
        URL resource = this.getClass().getClassLoader().getResource("images/dog.gif");
        label2.setIcon(new ImageIcon(resource));
        // 设置文本和图片的位置
        label2.setHorizontalTextPosition(SwingConstants.CENTER);
        label2.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(label2);

    }
}
