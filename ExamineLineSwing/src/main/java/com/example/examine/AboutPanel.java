package com.example.examine;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

/**
 * 关于画面
 */
public class AboutPanel extends JPanel {
    private JTextPane mTextPane = new JTextPane();

    public AboutPanel() {
        // 边界布局
        setLayout(new BorderLayout());
        //
        mTextPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(mTextPane);
        add(scrollPane, SwingConstants.CENTER);
        //
        initText();
    }

    public void initText() {
        //
        appendTitle("项目采用Java（JDK1.8）、Swing等进行编写。");
        appendSplitLine();
        //
        appendTitle("目的：");
        appendDescription("1. 制作一些常用工具类。");
        appendSplitLine();
        // 功能说明
        appendTitle("图片下载工具：");
        appendDescription("下载图片到指定位置，常用于新车型跨线别拷贝图片。");

        appendTitle("用户二维码生成：");
        appendDescription("生成用户IPad登录使用的二维码。");

        appendSplitLine();
        //
        appendTitle("参考连接：");
        appendDescription("Java Swing 图形界面开发（目录）https://blog.csdn.net/xietansheng/article/details/72814492");
        appendDescription("Swing程序设计教程@阿发你好 https://www.bilibili.com/video/BV1h7411v7Mq");
        appendDescription("idea的Java窗体可视化工具Swing UI Designer的简单使用（一） https://blog.csdn.net/weixin_43444930/article/details/117855310");
    }

    /**
     * 添加标题
     *
     * @param message
     */
    protected void appendTitle(String message) {
        appendText(message, Color.black, true, 18);
    }

    /**
     * 添加描述
     *
     * @param message
     */
    protected void appendDescription(String message) {
        appendText("\t" + message, Color.black, false, 16);
    }

    /**
     * 换行
     *
     * @param message
     */
    protected void appendSplitLine(String message) {
        appendText((message != null ? message : "") + "\n", Color.black, false, 16);
    }

    protected void appendSplitLine() {
        appendSplitLine(null);
    }

    /**
     * 添加文本
     *
     * @param message
     * @param fontColor
     * @param bold
     * @param fontSize
     */
    protected void appendText(String message, Color fontColor, boolean bold, int fontSize) {
        String msg = message + "\n";
        System.out.println(msg);
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        // 字体颜色
        if (fontColor != null) {
            StyleConstants.setForeground(attributeSet, fontColor);
        }
        // 加粗
        if (bold == true) {
            StyleConstants.setBold(attributeSet, true);
        }
        // 字体大小
        StyleConstants.setFontSize(attributeSet, fontSize);
        Document document = mTextPane.getDocument();
        try {
            document.insertString(document.getLength(), msg, attributeSet);
            mTextPane.setCaretPosition(document.getLength());// 滚动到
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

    }
}
