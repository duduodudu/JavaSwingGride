package org.example.demo;

import org.example.utils.MyDemoUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class MyRadioButtonDemo extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.show("JRadioButton", new MyRadioButtonDemo());
    }

    public MyRadioButtonDemo() {
        JRadioButton radioButton1 = new JRadioButton("男");
        radioButton1.setContentAreaFilled(false); //不绘制默认按钮背景
        radioButton1.setFocusPainted(false);//不绘制图片或文字周围的焦点虚框

        JRadioButton radioButton2 = new JRadioButton("女");
        JRadioButton radioButton3 = new JRadioButton("保密");
        JRadioButton radioButton4 = new JRadioButton("禁用");
        radioButton4.setEnabled(false);
        //
        radioButton1.addActionListener(new MyRadioListener());
        radioButton2.addActionListener(new MyRadioListener());
        radioButton3.addActionListener(new MyRadioListener());
        radioButton4.addActionListener(new MyRadioListener());
        //
        add(radioButton1);
        add(radioButton2);
        add(radioButton3);
        add(radioButton4);
        // 需要添加到按钮组中才会实现单选的效果
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);

//        ButtonModel selection = buttonGroup.getSelection();
//        selection.getMnemonic()

        JButton button = new JButton("获取选中的值");
        add(button);
        button.addActionListener((e) -> {
            String selectText = myGetRadioSelectText(buttonGroup);
            System.out.println("选中的值是：" + selectText);
        });

        JButton clearButton = new JButton("清除选中");
        add(clearButton);
        clearButton.addActionListener((e) -> {
            buttonGroup.clearSelection();
        });

    }

    private String myGetRadioSelectText(ButtonGroup group) {
        Enumeration<AbstractButton> elements = group.getElements();
        while (elements.hasMoreElements()) {
            AbstractButton button = elements.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    private class MyRadioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) e.getSource();
                System.out.println("监听到事件发生变化: \t" + radioButton.getText());
            }
        }
    }
}
