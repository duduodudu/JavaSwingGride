package com.example.examine;

import com.example.examine.utils.MyDemoUtils;

import javax.swing.*;
import java.awt.*;

public class PcCheckBoardPanel extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.showDemo(new PcCheckBoardPanel());
    }

    public PcCheckBoardPanel() {
        setLayout(new BorderLayout());
        // 顶部 ////////////////////////////////////////////

        // 中间 URL输入 ///////////////////////////////////////////
        center();
        // 请求结束输出 ///////////////////////////////////////////
    }

    private void center() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 3, 10, 10));
        for (int i = 0; i < 15; i++) {
//            panel.add(new PcWebShowPanel(i + "调试", "http://www.baidu.com"));
            panel.add(new PcWebShowPanel(i + "调试", "http://192.168.132.3:8085/examineLine/login.do"));
        }
        add(panel, BorderLayout.CENTER);
    }
}
