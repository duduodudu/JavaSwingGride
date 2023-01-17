package com.example.examine;

import com.example.examine.utils.MyQrCodeHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 车辆二维码生成器
 * 入参：vinCode
 */
public class CarQrCodeGenerator extends JPanel {
    public static void main(String[] args) {
        new CarQrCodeGenerator();
    }

    private JTextField vinCodeTextFiled;
    private CarQrBox qrCodeBox;
    private JPanel mHistoryPanel;
    private ArrayList<CarQrEntity> entities = new ArrayList<>();

    public CarQrCodeGenerator() {
        initConfig();
        setVisible(true);
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        setLayout(new BorderLayout());
        //
        initTopPanel();
        //
        initCenterPanel();
        //
        initHistoryPanel();
        // 底部说明
        initTipsDescPanel();
    }

    /**
     * 使用说明按钮
     */
    private void initTipsDescPanel() {
        JLabel label = new JLabel();
        label.setText("<html>"
                + "通过车架号，生成从指定位数的二维码信息。其他信息使用数字代替。<br/>"
                + "使用场景:<br/>"
                + "1. 用于返修区扫码读取车辆信息。<br/>"
                + "2. 用于IPad扫描车身卡。<br/>"
                + "</html>");
        label.setPreferredSize(new Dimension(0, 120));
        add(label, BorderLayout.SOUTH);
    }

    /**
     * 顶部
     */
    private void initTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        // 车架号 //////////////////////////////////////////////////////
        JLabel vinLabel = new JLabel("车架号/VIN:");
        panel.add(vinLabel);
        // vin 输入框
        vinCodeTextFiled = new JTextField("12345678901234567");
        vinCodeTextFiled.setPreferredSize(new Dimension(180, 30));
        panel.add(vinCodeTextFiled);

        // 开始位数选择 //////////////////////////////////////////////////////
        JLabel lengthLabel = new JLabel("开始位:");
        panel.add(lengthLabel);
        String[] item = {"31", "6"};
        JComboBox startCombo = new JComboBox(item);
        startCombo.setEditable(true);
        panel.add(startCombo);

        // 生成按钮 //////////////////////////////////////////////////////
        JButton button = new JButton("生成");
        button.addActionListener((e) -> {
            System.out.println("点击生成按钮");
            clickButton(startCombo.getSelectedItem().toString());
        });
        panel.add(button);

        vinCodeTextFiled.addActionListener((e) -> {
            System.out.println("输入VinCode后按下ENTER");
            clickButton(startCombo.getSelectedItem().toString());
        });

        //
        add(panel, BorderLayout.NORTH);
    }

    /**
     * 点击生成按钮
     *
     * @param startLength vin开始位数
     */
    private void clickButton(String startLength) {
        String vinCode = vinCodeTextFiled.getText();
        if (vinCode == null || vinCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入车架号/VIN");
            return;
        }
        entities.add(0, qrCodeBox.getEntity());
        //

        try {
            Integer length = Integer.parseInt(startLength);
            if (length <= 1) {
                length = 31;
            }
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 1; i < length; i++) {
                stringBuffer.append(i % 10);
            }
            stringBuffer.append(vinCode);
            CarQrEntity carQrEntity = new CarQrEntity(vinCode, stringBuffer.toString());
            System.out.println(carQrEntity);
            qrCodeBox.setEntity(carQrEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        reprintHistoryQrCode();
        vinCodeTextFiled.setText("");
    }

    private void initCenterPanel() {
        JPanel panel = new JPanel();
        //
        qrCodeBox = new CarQrBox(new CarQrEntity("vinCode", "12345678901234567"));
        panel.add(qrCodeBox);
        // 
        add(panel, BorderLayout.CENTER);
    }

    private JScrollPane scrollPane;

    /**
     * 历史记录面板
     */
    private void initHistoryPanel() {
        mHistoryPanel = new JPanel(new GridLayout(0, 1));
        scrollPane = new JScrollPane(mHistoryPanel);
        add(scrollPane, BorderLayout.EAST);
    }

    /**
     * 重新历史绘制二维码
     */
    private void reprintHistoryQrCode() {
        // 移除旧的
        mHistoryPanel.removeAll();
        // 添加新的
        for (CarQrEntity entity : entities) {
            System.out.println("重新绘制历史的二维码:" + entity);
            mHistoryPanel.add(new CarQrBox(new CarQrEntity(entity.getVinCode(), entity.getQrCode())));
        }
        mHistoryPanel.setPreferredSize(new Dimension(260, 300 * entities.size()));
        scrollPane.setPreferredSize(new Dimension(300, 300 * entities.size()));
    }

    /**
     * 车辆实体
     * 车架号、二维码
     */

    private class CarQrEntity {
        private String vinCode;
        private String qrCode;

        public CarQrEntity(String vinCode, String qrCode) {
            this.vinCode = vinCode;
            this.qrCode = qrCode;
        }

        public String getVinCode() {
            return vinCode;
        }

        public String getQrCode() {
            return qrCode;
        }

        @Override
        public String toString() {
            return "CarQrEntity{ vinCode=" + vinCode + ", qrCode='" + qrCode + '}';
        }
    }

    private class CarQrBox extends JLabel {
        private CarQrEntity entity;

        public CarQrBox(CarQrEntity entity) {
            initConfig();
            this.setEntity(entity);
        }

        public CarQrEntity getEntity() {
            return entity;
        }

        public void setEntity(CarQrEntity entity) {
            this.entity = entity;
            setIcon(MyQrCodeHelper.createImageIcon(entity.getQrCode(), 200));
            setText(entity.getVinCode());
        }

        private void initConfig() {
            setHorizontalTextPosition(JLabel.CENTER);
            setVerticalTextPosition(JLabel.BOTTOM);
            setHorizontalAlignment(JLabel.CENTER);
        }
    }
}

