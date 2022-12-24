package org.example.demo;

import org.example.utils.MyDemoUtils;

import javax.swing.*;
import java.awt.event.ItemEvent;

public class MyComboBoxDemo extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.show("", new MyComboBoxDemo());
    }

    public MyComboBoxDemo() {
        String[] listData = new String[]{"香蕉", "苹果", "哈密瓜", "超级无敌甜的大西瓜"};
        JComboBox<String> comboBox1 = new JComboBox<String>(listData);
        comboBox1.setSelectedIndex(2);
        add(comboBox1);
        comboBox1.addItemListener((e) -> {
            System.out.println("监听到变化" + e);
//            System.out.println("监听到变化\t" + e.getStateChange());
            if (e.getStateChange() == ItemEvent.SELECTED) {
                System.out.println("选中:\t" + e.getItem());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                System.out.println("取消选中:\t" + e.getItem());
            }
        });

    }
}
