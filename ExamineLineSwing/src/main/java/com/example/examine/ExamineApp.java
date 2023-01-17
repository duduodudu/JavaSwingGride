package com.example.examine;

import com.formdev.flatlaf.*;

import javax.swing.*;
import java.awt.*;

public class ExamineApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            JTabbedPane tabbedPane = new JTabbedPane();
            //
            tabbedPane.add("WS点检（开发中）", new WsGetPanel());
//            tabbedPane.add("PC点检（开发中）", new PcCheckBoardPanel());
            //
            tabbedPane.add("图片下载", new DownloadImageFrame());
            tabbedPane.add("用户二维码", new UserQrCodeGenerator());
            tabbedPane.add("车辆二维码", new CarQrCodeGenerator());
            //
            tabbedPane.add("版本更新说明", new ChangeLogPanel());
            tabbedPane.add("关于", new AboutPanel());
            createGUI(tabbedPane);
        });
    }

    private static JMenuBar GetMyMenu() {
        JMenuBar menuBar = new JMenuBar();
        //
        JMenu themeMenu = new JMenu("更换主题");
        menuBar.add(themeMenu);

        JRadioButtonMenuItem theme1 = new JRadioButtonMenuItem("Flat Light 浅色");
        theme1.addActionListener((e) -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
                FlatLaf.updateUI();
            } catch (UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
        });
        JRadioButtonMenuItem theme2 = new JRadioButtonMenuItem("Flat Dark  深色");
        theme2.addActionListener((e) -> {
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
                FlatLaf.updateUI();
            } catch (UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
        });
        JRadioButtonMenuItem theme3 = new JRadioButtonMenuItem("Flat IntelliJ  浅色");
        theme3.addActionListener((e) -> {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                FlatLaf.updateUI();
            } catch (UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
        });
        JRadioButtonMenuItem theme4 = new JRadioButtonMenuItem("Flat Darcula  深色");
        theme4.addActionListener((e) -> {
            try {
                UIManager.setLookAndFeel(new FlatDarculaLaf());
                FlatLaf.updateUI();
            } catch (UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
        });
        themeMenu.add(theme1);
        themeMenu.add(theme2);
        themeMenu.add(theme3);
        themeMenu.add(theme4);

        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(theme1);
        themeGroup.add(theme2);
        themeGroup.add(theme3);
        themeGroup.add(theme4);

        return menuBar;
    }

    /**
     * 初始化画面
     */
    private static void createGUI(JComponent component) {
        JFrame frame = new JFrame();
        frame.setTitle("检查线常用工具（v0.1.20221217）");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) screenSize.getWidth(), (int) (screenSize.getHeight() - 150));
        frame.setLocationRelativeTo(null);// 居中
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// 点击x退出程序
        frame.setContentPane(component);
        // 菜单
        frame.setJMenuBar(GetMyMenu());
        //
        frame.setVisible(true);
    }
}
