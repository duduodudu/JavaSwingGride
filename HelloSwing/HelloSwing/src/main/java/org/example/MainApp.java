package org.example;

import org.example.demo.*;

import javax.swing.*;

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
            //      frame.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
            frame.setSize(800, 600);
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
    }
}
