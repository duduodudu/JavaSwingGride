package org.example.demo;

import org.example.utils.MyDemoUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MyProgressBarDemo extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.show("进度条", new MyProgressBarDemo());
    }


    public MyProgressBarDemo() {
        //
        int minValue = 0;
        int maxValue = 100;

        //
        JProgressBar progressBar1 = new JProgressBar();
//        progressBar.setPreferredSize(new Dimension(300, 40));
        //
        progressBar1.setMinimum(minValue); // 最小值
        progressBar1.setMaximum(maxValue); // 最大值
        progressBar1.setValue(1); // 当前值
        //
        progressBar1.setStringPainted(true); // 绘制百分比文本
        //
        // 设置 最小进度值、最大进度值、当前进度值
        //        void setMinimum(int min)
        //        void setMaximum(int max)
        //        void setValue(int n)

        //// 获取当前进度的百分比
        //        double getPercentComplete()
        //
        //// 是否绘制百分比文本（进度条中间显示的百分数）
        //        void setStringPainted(boolean b)
        //
        //// 设置进度条进度是否为不确定模式
        //        void setIndeterminate(boolean newValue)
        //
        //// 设置进度条的方向，SwingConstants.VERTICAL 或 SwingConstants.HORIZONTAL
        //        void setOrientation(int newOrientation)


        new Timer(200, (e) -> {
            int curValue = progressBar1.getValue();
            System.out.println("当前进度:" + curValue);
            System.out.println("当前进度:" + progressBar1.getPercentComplete()
                    + "\t" + progressBar1.getMaximum());
            curValue++;
            if (curValue >= progressBar1.getMaximum()) {
                if (e.getSource() instanceof Timer) {
                    Timer timer = (Timer) e.getSource();
                    timer.stop();
                    System.out.println("定时器停止");
                }
            }
            progressBar1.setValue(curValue);
            progressBar1.updateUI();
        }).start();
        add(progressBar1);


        //
        JProgressBar progressBar2 = new JProgressBar(SwingConstants.VERTICAL, minValue, maxValue);
        add(progressBar2);

        JProgressBar progressBar3 = new JProgressBar(SwingConstants.VERTICAL, minValue, maxValue);
        add(progressBar3);
        progressBar3.setIndeterminate(true);

        progressBar1.addChangeListener((e) -> {
            if (e.getSource() instanceof JProgressBar) {
                JProgressBar progressBar = (JProgressBar) e.getSource();
                progressBar2.setValue(progressBar.getValue());
                progressBar3.setValue(progressBar.getValue());
                if (progressBar3.getValue() >= progressBar3.getMaximum()) {
                    // 停止进度条的不确定模式
                    progressBar3.setIndeterminate(false);
                }
                updateUI();
            }
        });

    }
}
