package org.example.demo;

import org.example.utils.MyDemoUtils;

import javax.swing.*;
import java.util.Hashtable;

public class MySliderDemo extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.show("Slider", new MySliderDemo());
    }

    public MySliderDemo() {

        // 设置滑块的 最小值、最大值、当前值


        //// 设置 主刻度标记间隔
        //        void setMajorTickSpacing(int n)
        //// 设置单个主刻度内的 次刻度标记间隔
        //        void setMinorTickSpacing(int n)

        //// 设置是否绘制 刻度线
        //        void setPaintTicks(boolean b)
        //// 设置是否绘制 刻度标签（刻度值文本）
        //        void setPaintLabels(boolean b)
        //// 设置是否绘制 滑道
        //        void setPaintTrack(boolean b)

        //// 设置滑块的方向，SwingConstants.VERTICAL 或 SwingConstants.HORIZONTAL
        //        void setOrientation(int orientation)

        //// 设置是否颠倒刻度值（刻度值从大到小）
        //        void setInverted(boolean b)

        //// 设置滑块是否对齐到刻度。设置为 true，则滑块最终只能在有刻度的位置取值，即滑块取值不连续。
        //        void setSnapToTicks(boolean b)

        //// 用于指定将在 给定值处 绘制 对应的标签 来替代刻度数值文本的显示
        //        void setLabelTable(Dictionary<Integer, JComponent> labels)

        //// 添加滑块的值改变监听器
        //        void addChangeListener(ChangeListener l)

        int maxVal = 100;
        int minVal = 0;
        JSlider slider1 = new JSlider();
        slider1.setMaximum(maxVal);
        slider1.setMinimum(minVal);
        add(slider1);

        JSlider slider2 = new JSlider(minVal, maxVal);
        add(slider2);
        //
        slider2.setMajorTickSpacing(20); // major 主要
        slider2.setMinorTickSpacing(10); // minor 次要
//        slider2.setPaintTrack(true);
        slider2.setPaintTicks(true); // 刻度
        slider2.setPaintLabels(true); // 标签
        slider2.setSnapToTicks(true); // 对齐到刻度
        //
        slider2.addChangeListener((e) -> {
            System.out.println("slider changed: " + slider2.getValue());
            slider1.setValue(slider2.getValue());
        });

        // 自定义标签
        JSlider slider3 = new JSlider(SwingConstants.VERTICAL, minVal, maxVal, 30);

        // 刻度
        slider3.setPaintTicks(true); // 显示刻度
        slider3.setMajorTickSpacing(10); // 主要刻度
        slider3.setMinorTickSpacing(5); // 次要刻度
        add(slider3);
        // 自定义标签
        slider3.setPaintLabels(true); // 显示标签
        Hashtable<Integer, JComponent> labels = new Hashtable<>();
        labels.put(0, new JLabel("最小值"));
        labels.put(25, new JLabel("较小值"));
        labels.put(50, new JLabel("中间值"));
        labels.put(75, new JLabel("较大值"));
        labels.put(100, new JLabel("最大值"));
        slider3.setLabelTable(labels);
        //
    }
}
