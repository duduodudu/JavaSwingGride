package org.example.demo;

import org.example.utils.MyDemoUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyCheckBoxDemo extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.show("复选框", new MyCheckBoxDemo());
    }

    public MyCheckBoxDemo() {
        JCheckBox checkBox1 = new JCheckBox("语文");
        JCheckBox checkBox2 = new JCheckBox("数学");
        JCheckBox checkBox3 = new JCheckBox("英语");
        //
        add(checkBox1);
        add(checkBox2);
        add(checkBox3);
        //
        checkBox1.addActionListener(new MyCheckBoxActionListener());
        checkBox2.addActionListener(new MyCheckBoxActionListener());
        checkBox3.addActionListener(new MyCheckBoxActionListener());
        //
        checkBox1.setFocusPainted(false);
        //

    }

    /**
     * 监听事件
     */
    private class MyCheckBoxActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                System.out.println("点击的是:\t" + checkBox.getText() + "\t是否选中：" + checkBox.isSelected());
            }
        }
    }
}
