package com.example.demo;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;

/**
 * 简单的Web浏览器
 *
 * @author Vic https://blog.csdn.net/zhaoweitco/article/details/8027902
 */
public class SimpleWebBrowser {
    public static void main(String[] args) {
//        String initialPage = "http://www.cafeaulait.org/";
        String initialPage = "http://www.baidu.com";

        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);
        jep.addHyperlinkListener(new LinkFollower(jep));

        try {
            jep.setPage(initialPage);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        JScrollPane scrollPane = new JScrollPane(jep);
        JFrame f = new JFrame("Simple Web Browser");
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setContentPane(scrollPane);
        f.setSize(512, 342);

        EventQueue.invokeLater(new FrameShower(f));
    }

    private static class FrameShower implements Runnable {

        private final Frame frame;

        public FrameShower(Frame frame) {
            this.frame = frame;
        }

        public void run() {
            frame.setVisible(true);
        }

    }


}

class LinkFollower implements HyperlinkListener {

    private JEditorPane pane;

    public LinkFollower(JEditorPane pane) {
        this.pane = pane;
    }

    public void hyperlinkUpdate(HyperlinkEvent evt) {
        if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                pane.setPage(evt.getURL());
            } catch (IOException e) {
                pane.setText("<html>could not load " + evt.getURL() + "</html>");
            }
        }
    }

}












