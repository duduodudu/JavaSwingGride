package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class TabbedPaneDemo extends JTabbedPane {
    public TabbedPaneDemo() {
        add("选项卡", new JLabel("测试"));
    }

    /**
     * 全屏显示
     * 方法一：设置窗口最大（伪最大化） 按照屏幕的绝对尺寸
     * JFrame frame =new JFrame();
     * frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
     * frame.setLocation(0,0);
     * frame.show();
     * <p>
     * 方法二：设置最大化 （JFrame.MAXIMIZED_BOTH）
     * JFrame frame =new JFrame();
     * frame.show();
     * frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
     * //设置最小化的语句frame.setExtendedState(JFrame.ICONIFIED);
     * <p>
     * 方法三：设置全屏模式
     * GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
     */
    public static class DownloadImageDemo {
        public static void main(String[] args) {
            JFrame frame = new JFrame();
            configFullScreen1(frame); // 设置全屏
            //
            JTextArea urlsTextArea = new JTextArea(100, 100);
            urlsTextArea.setText("http://192.168.132.3:8085/imgUrl/58/26/SEPARATOR橙色_745.jpgSEPARATOR58/26/SEPARATOR3423104.jpg\n" +
                    "http://192.168.132.3:8085/imgUrl/72/30/SEPARATOR无识别_817.jpgSEPARATOR72/30/SEPARATOR3423080.jpg\n" +
                    "http://192.168.132.3:8085/imgUrl/51/18/SEPARATOR米_123.jpgSEPARATOR51/18/SEPARATOR3423055.jpg\n" +
                    "http://192.168.132.3:8085/imgUrl/62/21/SEPARATOR米2_752.jpgSEPARATOR62/21/SEPARATOR3423057.jpg\n" +
                    "http://192.168.132.3:8085/imgUrl/24/65/SEPARATOR右边_208.pngSEPARATOR24/65/SEPARATOR3423450.png");
            urlsTextArea.setEditable(true);
            frame.add(urlsTextArea);
            //
            JButton startButton = new JButton("开始");
            frame.add(startButton);
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("点击按钮");
                    String[] lines = urlsTextArea.getText().split("\n");


                    for (String lineStr : lines) {
                        String[] names = lineStr.split("SEPARATOR");
                        String http = names[0];
                        String fileName = names[1];
                        String outDir = names[2];
                        String imageName = names[3];
                        System.out.println(http);
                        System.out.println(fileName);
                        System.out.println(outDir);
                        System.out.println(imageName);
                        System.out.println("");
                        try {
                            downloadPicture(http, fileName, "./download" + File.separator + outDir, fileName);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    }
                }
            });
            //
            frame.setLayout(new FlowLayout());
            // 设置显示
            frame.setBackground(Color.gray);
            frame.setVisible(true);
        }

        /**
         * 设置全屏方式
         * 含顶部标题和按钮
         *
         * @param frame
         */
        public static void configFullScreen1(JFrame frame) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(screenSize);
        }

        /**
         * @param picUrls 图片地址     path 图片下载存放目录  fileNames 文件名称 数量与图片地址数量保持一致
         * @return void
         * @throws
         * @Title: downloadPicture
         * @Description: 下载图片
         */
        public static void downloadPicture(String httpUrl, String picUrls, String path, String fileName) throws IOException {
            try {
                //根据图片地址构建URL
                URL url = new URL(httpUrl + URLEncoder.encode(picUrls, "utf-8"));
                URLConnection conn = url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(10000);
                conn.connect();
                DataInputStream dataInputStream = new DataInputStream(conn.getInputStream());
                //创建目录和图片
                File pathFile = new File(path);
                File file = new File(path + File.separator + fileName);
                System.out.println(file.getAbsolutePath());
                if (!pathFile.exists()) {
                    pathFile.mkdirs();
                    file.createNewFile();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                //通过流复制图片
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = dataInputStream.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                fileOutputStream.write(output.toByteArray());
                dataInputStream.close();
                fileOutputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
