package org.example.demo;

import org.example.utils.MyDemoUtils;

import javax.swing.*;

public class MyTextAreaDemo extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.show("文本域", new MyTextAreaDemo());
    }

    public MyTextAreaDemo() {
        // 是否自动换行，默认为 false
        //        void setLineWrap(boolean wrap)
        // 设置自动换行方式。如果为 true，则将在单词边界（空白）处换行; 如果为 false，则将在字符边界处换行。默认为 false。
        //        void setWrapStyleWord(boolean word)

        ///////////////
        // 获取文本框中的文本
        //        String getText()
        // 追加文本到文档末尾
        //        void append(String str)
        // 替换部分文本
        //        void replaceRange(String str, int start, int end)
        ///////////////////////
        // 获取内容的行数（以换行符计算，满行自动换下一行不算增加行数）
        //        int getLineCount()

        // 获取指定行（行数从0开始）的行尾（包括换行符）在全文中的偏移量
        //        int getLineEndOffset(int line)

        // 获取指定偏移量所在的行数（行数从0开始）
        //        int getLineOfOffset(int offset)
        JTextArea textArea1 = new JTextArea(30, 30);
        add(textArea1);
        // 如果需要滚动
        JTextArea textArea2 = new JTextArea("可以滚动的哦", 30, 30);
        add(new JScrollPane(textArea2));
    }
}
