package org.example.demo;

import org.example.utils.MyDemoUtils;

import javax.swing.*;
import java.awt.*;

public class MyToggleButtonDemo extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.show("开关按钮", new MyToggleButtonDemo());
    }

    public MyToggleButtonDemo() {
        JToggleButton toggleButton = new JToggleButton("开关按钮");
        add(toggleButton);


        /////////////
        JToggleButton toggleButton1 = new JToggleButton();
        add(toggleButton1);
        toggleButton1.setBorderPainted(false); // 描边 绘制边框
        toggleButton1.setFocusPainted(false); // 聚焦
        // toggleButton1.setOpaque(true); // Opaque 不透明
        toggleButton1.setContentAreaFilled(false); // 填充内容区域   绘制默认按钮默认背景
        toggleButton1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/toggle_off.png")));
        toggleButton1.setSelectedIcon(new ImageIcon(getClass().getClassLoader().getResource("images/toggle_on.png")));

        toggleButton.addActionListener((e) -> {
            if (e.getSource() instanceof JToggleButton) {
                JToggleButton button = (JToggleButton) e.getSource();
                System.out.println("点击按钮" + button.getText() + " \t" + button.isSelected());
                toggleButton1.setSelected(button.isSelected());
            }
        });


    }
}
