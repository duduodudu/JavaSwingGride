package com.example.examine;

import com.example.examine.entity.ExamineLineEntity;
import com.example.examine.utils.MyDemoUtils;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WsGetPanel extends JPanel {
    public static void main(String[] args) {
        try {
//            String content = new WsGetPanel().httpGet("http://www.baidu.com");
//            String content = new WsGetPanel().httpGet("http://192.168.132.3:8081/ExamineLine/rest/GetProperties");
//            System.out.println(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        MyDemoUtils.showDemo(new WsGetPanel());
    }

    public WsGetPanel() {
        setLayout(new BorderLayout());
        // 顶部 ////////////////////////////////////////////
        topPanel();
        // 中间 URL输入 ///////////////////////////////////////////
        // 请求结束输出 ///////////////////////////////////////////
        centerPanel.setLayout(new GridLayout(0, 4, 10, 10));
        add(centerPanel, BorderLayout.CENTER);
    }

    private void topPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(panel, BorderLayout.NORTH);
        //
        JLabel label = new JLabel("环境:");
        panel.add(label);

        JComboBox<String> linesComboBox = new JComboBox<>(new String[]{"云谷演示", "广丰正式"});
        panel.add(linesComboBox);
        //
        JButton startButton = new JButton("开始请求");
        panel.add(startButton);
        startButton.addActionListener((e) -> {
            if (linesComboBox.getSelectedIndex() == 0) {
                updateCenterUIIsc();
            } else {
                updateCenterUIDemo();
            }
            WsGetPanel.this.updateUI();
        });
    }

    private JPanel centerPanel = new JPanel();

    /**
     * 随机测试DEMO
     */
    private void updateCenterUIDemo() {

        centerPanel.removeAll();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                centerPanel.add(new WsBoxPanel(i + "#服务器名称", "http://192.168.132.3:8081/ExamineLine/rest/GetProperties"));
            } else {
                centerPanel.add(new WsBoxPanel(i + "#服务器名称", "http://192.168.132.5:8081/ExamineLine/rest/GetProperties"));
            }
        }

    }

    /**
     * 云谷环境
     */
    private void updateCenterUIIsc() {
        centerPanel.removeAll();
        List<ExamineLineEntity> lineEntities = ExamineLineEntity.GetDemoLine();
        for (ExamineLineEntity line : lineEntities) {
            centerPanel.add(new WsBoxPanel(line.getEduName(), line.getEduWsUrl()));
            centerPanel.add(new WsBoxPanel(line.getOfficialName(), line.getOfficialWsUrl()));
            centerPanel.add(new WsBoxPanel(line.getStandbyName(), line.getStandbyWsUrl()));
            centerPanel.add(new WsBoxPanel(line.getNginxName(), line.getNginxWsUrl()));
        }
    }


    /**
     * get请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    static public String httpGet(String url) throws Exception {
        String content = null;
        URLConnection urlConnection = new URL(url).openConnection();
        HttpURLConnection connection = (HttpURLConnection) urlConnection;
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(3 * 1000);
        //连接
        connection.connect();
        //得到响应码
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder bs = new StringBuilder();
            String l;
            while ((l = bufferedReader.readLine()) != null) {
                bs.append(l).append("\n");
            }
            content = bs.toString();
        }
        return content;
    }

    class WsBoxPanel extends JPanel {
        JLabel mTitleLabel;
        JTextArea mResultTextArea = new JTextArea("");
        String title, url;

        public WsBoxPanel(String title, String url) {
            super();
            //  /////////////////////////////////////////////////////////
            this.title = title;
            this.url = url;
            // /////////////////////////////////////////////////////////
            mTitleLabel = new JLabel(title + "    " + (url != null ? url : "尚未设置"));

            ///////////////////////////////////////////////////////////
            mResultTextArea.setWrapStyleWord(true);//设置自动换行
            mResultTextArea.setLineWrap(true);
            mResultTextArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(mResultTextArea);
            // 取消显示水平滚动条
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            // 显示垂直滚动条
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            ///////////////////////////////////////////////////////////
            setLayout(new BorderLayout());
            add(mTitleLabel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            // 开始请求
            startRequest();
        }

        private SwingWorker worker;
        // 当前请求次数
        private int workerCount = 0;
        // 最大请求次数
        private final int workerMaxCount = 5;

        public void startRequest() {
            // 没有填写URL
            if (url == null || url.isEmpty()) {
                updateResult("尚未设置请求地址。", false);
                mResultTextArea.setBackground(Color.lightGray);
                return;
            }
            workerCount += 1;
            if (workerCount > workerMaxCount) {
                updateResult("超过最大重试次数:" + workerMaxCount + "。停止请求！！！", false);
                return;
            }

            // 开始请求
            if (worker != null) {
                worker = null;
            }
//            SwingWorker实例不可复用，每次执行任务必须生成新的实例。
            int count = workerCount;
            updateResult("第" + count + "次请求即将开始", false);
            worker = new SwingWorker<Map, Void>() {
                @Override
                protected Map doInBackground() throws Exception {
                    Map resultMap = new HashMap<>();

                    try {
                        String result = httpGet(url);
                        System.out.println(title + "\t" + url + "\n" + result + "\n");
                        resultMap.put("result", result);
                        resultMap.put("success", true);
                    } catch (Exception e) {
                        resultMap.put("result", "第" + count + "请求失败:\t" + e.getLocalizedMessage());
                        resultMap.put("success", false);
                    } finally {
                        return resultMap;
                    }
                }

                @Override
                protected void done() {
                    super.done();
                    try {
                        Map resultMap = get();
                        if (resultMap != null) {
                            Boolean success = (Boolean) resultMap.get("success");
                            String result = (String) resultMap.get("result");
                            updateResult(result, success);
                            if (!success.booleanValue()) {
                                startRequest();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            };
            worker.execute();
        }

        /**
         * 更新文本信息
         *
         * @param result
         * @param isSuccess
         */
        public void updateResult(String result, boolean isSuccess) {
            System.out.println(title + "\t请求地址:" + url + "\t结果:" + result);
            if (isSuccess) {
                mResultTextArea.setText(result + "\n");
                mResultTextArea.setBackground(Color.green);
            } else {
                String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                mResultTextArea.append(now + "\t" + result + "\n");
//                mResultTextArea.setCaretPosition(mResultTextArea.getText().length());
                mResultTextArea.selectAll();
                if (result != null) {
                    if (result.contains("停止请求")) {
                        mResultTextArea.setBackground(Color.red);
                    } else {
                        mResultTextArea.setBackground(Color.orange);
                    }
                }
            }
        }
    }
}






