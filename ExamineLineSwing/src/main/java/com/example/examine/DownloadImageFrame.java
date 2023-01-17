package com.example.examine;

import com.formdev.flatlaf.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 */
public class DownloadImageFrame extends JPanel {
    public static final String URL_SEPARATOR = "SEPARATOR"; // 字符串分隔符
    public static final String WINDOW_TITLE = "检查线图片下载工具（v0.0.1 build:20221216）";// 标题
    /**
     * 使用说明
     */
    public static final String HELP_TIPS = "使用说明：\n" +
            "1. 复制一下sql语句，修改教育环境地址，修改where条件，执行 \n" +
            "SELECT\n" +
            "-- 教育环境地址 需要修改，例子：http://192.168.132.4:8085\n" +
            " '教育环境地址/imgUrl/' || REPLACE(  REPLACE ( F_URL, F_NAME ), '//', '/')\n" +
            " || 'SEPARATOR' || F_NAME \n" +
            " || 'SEPARATOR' || REPLACE(  REPLACE ( F_URL, F_NAME ), '//', '/')\n" +
            " || 'SEPARATOR' || F_ID || SUBSTR( F_NAME, INSTR( F_NAME, '.',- 1 ) ) \n" +
            "FROM\n" +
            "  T_ATTACHMENT WHERE F_ID in (\n" +
            "-- 仕样图片查询 ----------------------------------------\n" +
            "SELECT F_SAMPLE_PICTURE_ID AS PID FROM T_JCX_MARK_CONTENT3 WHERE F_SAMPLE_PICTURE_ID IS NOT NULL AND F_USE = '1' \n" +
            "-- 此处条件可以修改\n" +
            "AND  F_STATION_ID IN ( '指定岗位的ID' )  AND F_VEHICLE_TYPE_ID IN ('指定车型')\n" +
            "UNION ALL \n" +
            "----- 涂面图片查看 ------------------------\n" +
            "SELECT F_PICTURE_ID  AS  PID  FROM T_JCX_COATING_ITEMS3 WHERE F_PICTURE_ID IS NOT NULL AND F_USE = '1' \n" +
            "AND F_PART_ID IN (\n" +
            "SELECT F_PART_ID FROM T_JCX_CHECK_PARTS3 WHERE F_USE = '1' \n" +
            "-- 此处条件可以修改\n" +
            "AND F_STATION_ID IN ( '指定岗位的ID') AND F_VEHICLE_TYPE_ID IN ('指定车型')\n" +
            ") \n" +
            "---\n" +
            ")  ORDER BY F_ID \n " +
            "2. 第一步的执行结果，粘贴到左边的输入框中。点击【开始下载】 \n" +
            "\n";

    static {
        /**
         * Swing应用程序美化--FlatLaf (IDEA风格)
         * 添加依赖
         <dependency>
         <groupId>com.formdev</groupId>
         <artifactId>flatlaf</artifactId>
         <version>0.26</version>
         </dependency>
         * 初始化 FlatLightLaf.install();
         * 设置风格
         */
//        com.formdev.flatlaf.FlatLightLaf.install(); // 方便,但无法在主题间切换
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
//            UIManager.setLookAndFeel(new FlatDarkLaf()); // 暗黑模式
//            UIManager.setLookAndFeel(new FlatDarculaLaf());// 暗黑模式2
//            UIManager.setLookAndFeel(new FlatIntelliJLaf());// IDEA浅色风格
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to initialize LaF");
        }
    }

    public static void main(String[] args) {


        /**
         * AWT 中有一个先进先出（FIFO）的事件队列（EventQueue）单例，添加到该队列中的任务（Runnable）将按顺序逐一在同一线程中被执行，该线程被称为 事件调度线程。
         * Swing 组件也延用了该队列实例，所有 Swing 组件的创建、修改、绘制、响应输入都必须要添加到该事件队列中执行。
         * 组件注册的各种监听器，回调方法也是被添加到事件队列中执行，即所有监听器的回调方法中就处于事件调度线程，可以直接操作UI。
         */
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        DownloadImageFrame panel = new DownloadImageFrame();
                        JFrame frame = configFrame(new JFrame());
                        frame.setContentPane(panel);
                    }
                }
        );
    }

    public DownloadImageFrame() throws HeadlessException {
        myInit();
    }

    private JTextArea mUrlsTextArea;
    private JButton mStartButton, mStopButton;
    private JTextPane mLogTextPane;
    private JCheckBox mAsyncCheckBox;
    private JComboBox<Integer> mTimeOutComboBox;
    private boolean stopFlag = false; // 是否点击了停止按钮

    private void myInit() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setSize(screenSize.width, screenSize.height - 200); // 全屏
        // 输入的URL的面板
        JTextArea urlsTextArea = new JTextArea(120, 100);
        urlsTextArea.setText("http://192.168.132.3:8085/imgUrl/58/26/SEPARATOR橙色_745.jpgSEPARATOR58/26/SEPARATOR3423104.jpg\n" +
                "http://192.168.132.3:8085/imgUrl/72/30/SEPARATOR无识别_817.jpgSEPARATOR72/30/SEPARATOR3423080.jpg\n" +
                "http://192.168.132.3:8085/imgUrl/51/18/SEPARATOR米_123.jpgSEPARATOR51/18/SEPARATOR3423055.jpg\n" +
                "http://192.168.132.3:8085/imgUrl/62/21/SEPARATOR米2_752.jpgSEPARATOR62/21/SEPARATOR3423057.jpg\n" +
                "http://192.168.132.3:8085/imgUrl/24/66/SEPARATOR右边_208.pngSEPARATOR24/66/SEPARATOR3423450.png");
        urlsTextArea.setEditable(true);
//        urlsTextArea.setToolTipText("图片下载地址" + URL_SEPARATOR + "图片名称" + URL_SEPARATOR + "文件夹路径名称" + URL_SEPARATOR + "图片ID名称");
        mUrlsTextArea = urlsTextArea;

        // 开始按钮 /////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton helpButton = new JButton("使用说明");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logHelpMessage();
            }
        });

//        lambda表达式
//        helpButton.addActionListener((e) -> {
//            logHelpMessage();
//        });

        // 开始按钮 /////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton startButton = new JButton("开始");
        mStartButton = startButton;
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                useSwingWorkerToDownloadImage();
            }
        });
        /* // 优化了 多线程并发与线程安全  // 工作线程（SwingWorker）
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                logMessage("点击开始按钮，设定的超时时间为" + mTimeOutComboBox.getSelectedItem() + "秒。");
                //
                String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String downloadPcDir = "./download_" + now + "_PC";
                String downloadPadDir = "./download_" + now + "_PAD";
                logMessage("Web图片下载到文件夹:" + downloadPcDir);
                logMessage("Pad图片下载到文件夹:" + downloadPadDir);
                // 开始下载
                startDownload(true);
                //
                String[] lines = urlsTextArea.getText().split("\n");
                for (String lineStr : lines) {
                    if (mAsyncCheckBox.isSelected()) {
                        // 开启线程去下载和处理
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                // run方法具体重写
                                downloadByLine(lineStr, downloadPcDir, downloadPadDir);
                            }
                        });
                        t.start();
                    } else {
                        downloadByLine(lineStr, downloadPcDir, downloadPadDir);
                    }
                    // 点击过停止按钮
                    if (stopFlag) {
                        stopFlag = false;
                        break;
                    }

                }

                // 下载结束
                startDownload(false);

            }
        });
        */
        // 停止按钮 /////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton stopButton = new JButton("停止");
        mStopButton = stopButton;
        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logMessage("点击停止按钮");
                stopFlag = true;
                mDownloadTask.cancel(true);
            }
        });
        // 清空日志按钮 /////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton clearLogButton = new JButton("清空日志");
        clearLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mLogTextPane.getDocument().remove(0, mLogTextPane.getDocument().getLength());
                    logMessage("清空日志成功");
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // 同步异步 /////////////////////////////////////////////////////////////////////////////////////////////////////
        JCheckBox checkBox = new JCheckBox("开启线程异步下载");
        mAsyncCheckBox = checkBox;


        // 超时时间 /////////////////////////////////////////////////////////////////////////////////////////////////////
        JLabel timeOutLabel = new JLabel("单张图片下载超时时间(秒)");
        Integer[] seconds = {1, 3, 5, 10, 20, 30};
        JComboBox<Integer> timeOutComboBox = new JComboBox<Integer>(seconds);
        timeOutComboBox.setSelectedItem(5);
        timeOutComboBox.setSize(80, 30);
        timeOutComboBox.setPreferredSize(new Dimension(80, 30));
        mTimeOutComboBox = timeOutComboBox;

        // 超时时间 /////////////////////////////////////////////////////////////////////////////////////////////////////
        JLabel themeLabel = new JLabel("主题：");
        JComboBox<String> themeComboBox = new JComboBox<String>(new String[]{"FlatLightLaf", "FlatDarkLaf", "FlatIntelliJLaf", "FlatDarculaLaf"});
        themeComboBox.setSelectedItem(5);
        themeComboBox.setSize(180, 30);
        themeComboBox.setPreferredSize(new Dimension(180, 30));
        themeComboBox.addActionListener((e) -> {
            // 更换主题
            FlatLaf laf = null;
            switch (themeComboBox.getSelectedIndex()) {
                case 0:
                    laf = new FlatLightLaf();
                    break;
                case 1:
                    laf = new FlatDarkLaf();
                    break;
                case 2:
                    laf = new FlatIntelliJLaf();
                    break;
                case 3:
                    laf = new FlatDarculaLaf();
                    break;
                default:
                    laf = new FlatLightLaf();
                    break;
            }
            try {
                UIManager.setLookAndFeel(laf);
                FlatLaf.updateUI();
            } catch (Exception ex) {
                System.err.println("Failed to initialize LaF");
            }
        });


        // 按钮组 /////////////////////////////////////////////////////////////////////////////////////////////////////
//        Box buttonsBox = Box.createHorizontalBox();// 创建第一个水平箱容器
        JPanel buttonsBox = new JPanel();// 创建第一个水平箱容器
        buttonsBox.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonsBox.add(helpButton);
        buttonsBox.add(startButton);
        buttonsBox.add(stopButton);
        buttonsBox.add(clearLogButton);
        buttonsBox.add(checkBox);
        buttonsBox.add(timeOutLabel);
        buttonsBox.add(timeOutComboBox);
        buttonsBox.add(themeLabel);
        buttonsBox.add(themeComboBox);
        startDownload(false);


        // 日志输出面板 /////////////////////////////////////////////////////////////////////////////////////////////////////
        JTextPane logTextPane = new JTextPane();
//        logTextPane.setToolTipText("日志输出");
        logTextPane.setEditable(false);
        mLogTextPane = logTextPane;


        // 分割
        JSplitPane splitPane = new JSplitPane();
        urlsTextArea.setPreferredSize(new Dimension((int) (screenSize.width * 0.5), screenSize.height - 250));
        urlsTextArea.setSize(new Dimension((int) (screenSize.width * 0.5), screenSize.height - 250));

        logTextPane.setPreferredSize(new Dimension((int) (screenSize.width * 0.5), screenSize.height - 250));
        logTextPane.setSize(new Dimension((int) (screenSize.width * 0.5), screenSize.height - 250));


        splitPane.setLeftComponent(setScrollPane(urlsTextArea));
        splitPane.setRightComponent(setScrollPane(logTextPane));

        splitPane.setOneTouchExpandable(true);        // 分隔条上显示快速 折叠/展开 两边组件的小按钮
        splitPane.setContinuousLayout(true);     // 拖动分隔条时连续重绘组件
        splitPane.setDividerLocation((int) (screenSize.width * 0.5));     // 设置分隔条的初始位置


        //
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        add(buttonsBox, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        // 创建一个垂直箱容器，放置上面两个水平箱（Box组合嵌套）
//        Box vBox = Box.createVerticalBox();
//        vBox.add(buttonsBox);
//        vBox.add(panel);
//        add(vBox);


        //

        logMessage("程序启动成功，日志输出如下:");
        logHelpMessage();
    }

    private void startDownload(boolean start) {
        mStartButton.setEnabled(!start); // 开始时候禁用按钮
        mStartButton.setText(start ? "正在下载" : "开始下载"); // 开始时候禁用按钮
        mStopButton.setEnabled(start); //
        mStopButton.setText(start ? "停止下载" : "等待开始"); //
        // paintImmediately(0, 0, getWidth(), getHeight());
    }

    private static JFrame configFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height - 100);
        frame.setTitle(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设定窗体关闭后自动退出进程
        frame.setBackground(Color.LIGHT_GRAY);// 背景颜色
//        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // 全屏
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        return frame;
    }

    /**
     * 自动上传信息显示区域
     */
    private JScrollPane setScrollPane(JComponent component) {
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(component);
        jScrollPane.setAutoscrolls(true);
        return jScrollPane;

    }

    /**
     * @param str
     * @param fontColor
     * @param bold
     * @param fontSize
     */
    private void appendLogToTextPane(String str, Color fontColor, boolean bold, int fontSize) {
        System.out.println(str);
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        if (fontColor != null) {
            StyleConstants.setForeground(attributeSet, fontColor);// 颜色
        }
        StyleConstants.setBold(attributeSet, Boolean.valueOf(bold)); // 加粗
        StyleConstants.setFontSize(attributeSet, fontSize);//字体大小

        Document doc = mLogTextPane.getDocument();
        try {
            doc.insertString(doc.getLength(), str, attributeSet);
            mLogTextPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印日志到显示上
     *
     * @param msg
     */
    private void logMessage(String msg) {
        String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\t" + msg + "\n";
        if (mLogTextPane != null) {
            appendLogToTextPane(message, null, false, 16);
        }
//        paintImmediately(0, 0, getWidth(), getHeight());
    }

    /**
     * 打印帮助信息
     */
    private void logHelpMessage() {
        logMessage("普通日志");
        logSuccessMessage("成功日志");
        logErrorMessage("失败日志");
        if (mLogTextPane != null) {
            Color color = new Color(88, 157, 246);
            appendLogToTextPane("--------- 以下信息为使用说明 ---------\n\n", color, false, 16);
            appendLogToTextPane(HELP_TIPS, color, false, 16);
            appendLogToTextPane("--------- 以上信息为使用说明 ---------\n\n", color, false, 16);
        }
    }

    /**
     * 打印日志到显示上 ERROR 级别
     *
     * @param msg
     */
    private void logErrorMessage(String msg) {
        String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\tERROR\t" + msg + "\n";
        if (mLogTextPane != null) {
            appendLogToTextPane(message, Color.red, false, 16);
        }
//        paintImmediately(0, 0, getWidth(), getHeight());
    }

    /**
     * 打印日志到显示上 SUCCESS 级别
     *
     * @param msg
     */
    private void logSuccessMessage(String msg) {
        String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\tSUCCESS\t" + msg + "\n";
        if (mLogTextPane != null) {
            appendLogToTextPane(message, new Color(66, 187, 66), false, 16);
        }
        paintImmediately(0, 0, getWidth(), getHeight());
    }

    private void downloadByLine(String lineStr, String downloadPcDir, String downloadPadDir) {
        String[] names = lineStr.split(URL_SEPARATOR);
        if (names != null && names.length > 1) {
            String http = names[0];
            String fileName = names[1];
            String outDir = names[2];
            String imageNameById = null;
            if (names.length >= 4) {
                imageNameById = names[3].trim();
            }
            System.out.println(http);
            System.out.println(fileName);
            System.out.println(outDir);
            System.out.println("PAD图片的名称:" + imageNameById);
            System.out.println("");
            try {
                // PC
                downloadPicture(http, fileName, downloadPcDir + File.separator + outDir, fileName);
                logSuccessMessage("下载成功:" + http + fileName + "\n\t保存到:" + downloadPcDir + File.separator + outDir + File.separator + fileName);
                // PAD
                if (imageNameById != null && !imageNameById.isEmpty()) {
                    downloadPicture(http, fileName, downloadPadDir + File.separator, imageNameById);
                    logSuccessMessage("下载成功:" + http + fileName + "\n\t保存到:" + downloadPadDir + File.separator + imageNameById);
                }
            } catch (Exception exception) {
                logErrorMessage("下载失败:" + http + fileName + "\n\t原因:" + exception.getClass().getName());
            }
        }
    }

    /**
     * @param picUrls 图片地址     path 图片下载存放目录  fileNames 文件名称 数量与图片地址数量保持一致
     * @return void
     * @throws
     * @Title: downloadPicture
     * @Description: 下载图片
     */
    private void downloadPicture(String httpUrl, String picUrls, String path, String fileName) throws Exception {
        int timeoutDuration = (int) mTimeOutComboBox.getSelectedItem();
        if (timeoutDuration < 1) {
            timeoutDuration = 3;
        }
        logMessage("开始下载:" + httpUrl + picUrls);
        //根据图片地址构建URL
        URL url = new URL(httpUrl + URLEncoder.encode(picUrls, "utf-8"));
        URLConnection conn = url.openConnection();
        conn.setReadTimeout(timeoutDuration * 1000);
        conn.setConnectTimeout(timeoutDuration * 1000);
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
    }

    private SwingWorker<String, Integer> mDownloadTask;

    /**
     * 开启线程去下载图片
     * <p>
     * 对于耗时的任务，可以创建子线程在子线程中执行耗时操作，执行完后再提交任务到事件调度线程操作UI。
     * 为此，Swing 提供了 SwingWorker 来处理耗时任务，并更好的支持了与事件调度线程之间的通信。
     * SwingWorker 是一个抽象类，设计用于需要在后台线程中运行长时间运行任务的情况，并可在完成后或者在处理过程中向 UI 提供数据并更新。
     * SwingWorker 的子类必须实现 doInBackground() 方法，在该方法中执行后台耗时计算。
     */
    private void useSwingWorkerToDownloadImage() {
        JPanel dialogOwner = this;
        logMessage("点击开始按钮，设定的超时时间为" + mTimeOutComboBox.getSelectedItem() + "秒。");
        if (mDownloadTask != null) {
            mDownloadTask.cancel(true);
            mDownloadTask.execute();
        }
        // 创建后台任务
        mDownloadTask = new SwingWorker<String, Integer>() {
            @Override
            protected String doInBackground() throws Exception {
                try {
                    // 开始下载
                    startDownload(true);
                    //
                    String text = mUrlsTextArea.getText().trim();

                    if (text == null || text.isEmpty()) {
                        JOptionPane.showMessageDialog(dialogOwner, "请输入url下载地址，可以点击【使用说明】按钮进行查看使用说明:\n" + HELP_TIPS, "提示", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                        String downloadPcDir = "./download_" + now + "_PC";
                        String downloadPadDir = "./download_" + now + "_PAD";
                        logMessage("Web图片下载到文件夹:" + downloadPcDir);
                        logMessage("Pad图片下载到文件夹:" + downloadPadDir);

                        String[] lines = text.split("\n");
                        for (String lineStr : lines) {
                            if (mAsyncCheckBox != null && mAsyncCheckBox.isSelected()) {
                                // 开启线程去下载和处理
                                Thread t = new Thread(new Runnable() {
                                    public void run() {
                                        downloadByLine(lineStr, downloadPcDir, downloadPadDir);
                                    }
                                });
                                t.start();
                            } else {
                                downloadByLine(lineStr, downloadPcDir, downloadPadDir);
                            }
                            // 点击过停止按钮
                            if (stopFlag) {
                                stopFlag = false;
                                break;
                            }
                        }
                    }
                    // 下载结束
                    startDownload(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // 返回计算结果
                return "下载完成";
            }

            @Override
            protected void done() {
                logMessage("任务完成");
            }
        };
        // 启动任务
        mDownloadTask.execute();
    }

    private class Cha implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {


        }
    }
}
