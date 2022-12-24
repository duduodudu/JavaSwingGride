package org.example.demo;

import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import org.example.utils.MyDemoUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.jar.JarEntry;

public class MyTextFieldDemo extends JPanel {
    public static void main(String[] args) {
        MyDemoUtils.show("文本框", new MyTextFieldDemo());
    }

    public MyTextFieldDemo() {
        // 设置颜色，分别为: 光标颜色、呈现选中部分的背景颜色、选中部分文本的颜色、不可用时文本的颜色
//        void setCaretColor(Color c)
//        void setSelectionColor(Color c)
//        void setSelectedTextColor(Color c)
//        void setDisabledTextColor(Color c)
        JTextField textField1 = new JTextField(10);
        add(textField1);

        JTextField textField2 = new JTextField(20);
        add(textField2);
        //
        textField2.setCaretColor(Color.RED);
//        textField2.setCaret();
        //
        textField2.setSelectionColor(Color.blue);
        textField2.setSelectedTextColor(Color.ORANGE); // 选中
        //
        textField2.setDisabledTextColor(Color.LIGHT_GRAY); // 禁用是否

        // JTextField 复制粘贴相关方法:
        // 设置光标开始位置，selectionStart >= 0
        //            void setSelectedTextColortionStart(int selectionStart)
        //     设置光标结束位置，selectionEnd >= selectionStart
        //            void setSelectionEnd(int selectionEnd)
        //     复制选中部分文本
        //            void copy()
        //     剪切选中部分文本
        //            void cut()
        //     粘贴文本到文本框
        //            void paste()
        //

        // 添加焦点事件监听器
        //        void addFocusListener(FocusListener listener)
        //// 添加文本框内的 文本改变 监听器
        //        textField.getDocument().addDocumentListener(DocumentListener listener)
        //// 添加按键监听器
        //        void addKeyListener(KeyListener listener)


        //        KeyListener
        //        keyPressed方法：按下键时调用
        //        keyTyped方法：释放键时调用
        //        keyReleased方法：按下键后调用

        textField2.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("keyTyped");
                System.out.println("getKeyCode\t" + e.getKeyCode());
                System.out.println("getKeyChar\t" + e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("keyPressed");
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("按下回车键");
                    if (e.getSource() instanceof JTextField) {
                        JTextField textField = (JTextField) e.getSource();
                        System.out.println("文本框的内容是:" + textField.getText());
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("keyReleased");
            }
        });

        //
        JLabel label = new JLabel("提示消息");
        add(label);
        textField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                label.setText("文本框2获取到焦点");
                if (e.getSource() instanceof JTextField) {
                    JTextField textField = (JTextField) e.getSource();
                    textField.setBackground(Color.green);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                label.setText("文本框2失去到焦点");
                if (e.getSource() instanceof JTextField) {
                    JTextField textField = (JTextField) e.getSource();
                    textField.setBackground(Color.WHITE);
                }
            }
        });

        //
        textField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insertUpdate" + e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("removeUpdate" + e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("changedUpdate" + e);
            }
        });

        //
        JPasswordField passwordField = new JPasswordField(10);
        add(passwordField);
        // 设置密码框默认显示的密码字符
        // passwordField.setEchoChar('@');

        JButton button = new JButton("获取密码框的内容");
        add(button);
        button.addActionListener((e) -> {
            System.out.println("点击按钮，文本内容是\t" + new String(passwordField.getPassword()));
        });

    }
}
