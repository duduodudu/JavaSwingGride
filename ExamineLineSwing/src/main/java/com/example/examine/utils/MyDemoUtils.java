package com.example.examine.utils;

import javax.swing.*;
import java.awt.*;

public class MyDemoUtils {
    public static void main(String[] args) {
        System.out.println(getMaxWindowSize());
        System.out.println(getScreenSize());
    }

    static public void showDemo(JPanel panel) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            frame.setContentPane(panel);
            frame.setVisible(true);
        });
    }

    /**
     * 获取整个屏幕的大小。
     * 不包含了任务栏的高度。
     * 最大化时候的尺寸
     *
     * @return
     */
    static public Dimension getMaxWindowSize() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return ge.getMaximumWindowBounds().getSize();
    }

    /**
     * 整个屏幕的绝对尺寸
     *
     * @return
     */
    static public Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
