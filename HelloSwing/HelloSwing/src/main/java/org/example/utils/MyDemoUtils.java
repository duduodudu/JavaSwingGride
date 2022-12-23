package org.example.utils;


import javax.swing.*;
import java.awt.*;

/**
 * 工具类
 */
public class MyDemoUtils {
    /**
     * 显示在画面上
     *
     * @param title
     * @param demoPanel
     */
    static public void show(String title, JPanel demoPanel) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 默认的关闭事件
        frame.setContentPane(demoPanel);
        //   frame.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // 屏幕绝对尺寸
        // 最大化时候的尺寸
        //        frame.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // 居中显示
        frame.setVisible(true); // 显示界面上
    }
}
