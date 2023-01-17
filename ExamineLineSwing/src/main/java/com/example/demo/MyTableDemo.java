package com.example.demo;

import com.example.examine.utils.MyDemoUtils;

import javax.swing.*;
import java.awt.*;

public class MyTableDemo extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.showDemo(new MyTableDemo());
    }

    public MyTableDemo() {
        demo1();
    }

    private void demo1() {
        Object[] colNames = {"姓名", "语文", "数学", "英语", "总分"};
        // 表格所有行数据
        Object[][] rowData = {
                {"张三", 80, 80, 80, 240},
                {"John", 70, 80, 90, 240},
                {"Sue", 70, 70, 70, 210},
                {"Jane", 80, 70, 60, 210},
                {"Joe", 80, 70, 60, 210}
        };

        JTable table = new JTable(rowData, colNames);
        //
        setLayout(new BorderLayout());
        add(table.getTableHeader(), BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
}
