package org.example.demo;

import org.example.utils.MyDemoUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MyButtonDemo extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.show("JButton", new MyButtonDemo());
    }

    public MyButtonDemo() {
        JButton button = new JButton("按钮");
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("点击按钮");
            }
        });
        button.addActionListener((event) -> {
            System.out.println("点击按钮222");
        });


        //
        JButton button1 = new JButton("图片按钮");
        // 加载图片
        URL resource = this.getClass().getClassLoader().getResource("images/dog.gif");
        button1.setIcon(new ImageIcon(resource));
        // 设置图片和文字的位置
        button1.setHorizontalTextPosition(SwingConstants.CENTER);
        button1.setVerticalTextPosition(SwingConstants.BOTTOM);
        //
        // 设置按钮在 默认、按下、不可用 时显示的图片
        //        void setIcon(Icon defaultIcon)
        //        void setPressedIcon(Icon pressedIcon)
        //        void setDisabledIcon(Icon disabledIcon)
        //
        button1.setBorderPainted(false); // 是否绘制边框
        add(button1);
    }
}
