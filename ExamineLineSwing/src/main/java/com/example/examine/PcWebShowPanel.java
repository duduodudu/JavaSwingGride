package com.example.examine;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;

/**
 * 简单浏览器的加载网页
 */
public class PcWebShowPanel extends JPanel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("显示网页");
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            frame.setContentPane(new PcWebShowPanel("1#教育", "http://www.baidu.com"));
            frame.setVisible(true);
        });
    }

    public PcWebShowPanel(String title, String url) {
        super();
        // 网页 /////////////////////////////////////////////////////////
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.addHyperlinkListener((event) -> {
            // 处理超链接
            if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    editorPane.setPage(event.getURL());
                } catch (IOException e) {
                    editorPane.setText("<html><h1>" + event.getURL() + "加载失败</h1></html>");
                }
            }
        });

        try {
            editorPane.setPage(url);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getLocalizedMessage() + "\n" + e.getMessage());
            // System.exit(1); // 退出程序
        }
        // 标题 /////////////////////////////////////////////////////////
        JLabel label = new JLabel(title + "\t\t" + url);
        // 网页 /////////////////////////////////////////////////////////
        setLayout(new BorderLayout());
        add(new JScrollPane(label), BorderLayout.NORTH);
        add(new JScrollPane(editorPane), BorderLayout.CENTER);
    }
}
