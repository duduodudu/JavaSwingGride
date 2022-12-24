package org.example;

import org.example.demo.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainApp extends JTabbedPane {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            JFrame frame = new JFrame();
            frame.setTitle("Swing学习指南/Hello Swing Guide");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 默认的关闭事件
            frame.setContentPane(app);
            //   frame.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // 屏幕绝对尺寸
            // 最大化时候的尺寸
            frame.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
//            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null); // 居中显示
            frame.setVisible(true); // 显示界面上
        });
    }

    public MainApp() {
        add("JLabel 文本标签", new MyLabelDemo());
        //
        add("JButton 按钮", new MyButtonDemo());
        add("JRadioButton 单选按钮", new MyRadioButtonDemo());
        add("JCheckButton 复选按钮", new MyCheckBoxDemo());
        add("JToggleButton 开关按钮", new MyToggleButtonDemo());
        //
        add("JTextField 文本框", new MyTextFieldDemo());
        add("JTextArea 文本域", new MyTextAreaDemo());
        //
        add("JProgressBar 进度条", new MyProgressBarDemo());
        add("JSlider 滑块", new MySliderDemo());

        ImageIcon layoutIcon = new ImageIcon(getClass().getClassLoader().getResource("images/tab_layout.png"));
        layoutIcon.setImage(layoutIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        addTab("JSlider 滑块", layoutIcon, new MySliderDemo());

    }
}
