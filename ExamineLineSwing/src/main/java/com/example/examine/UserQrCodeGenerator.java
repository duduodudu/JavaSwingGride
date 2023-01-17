package com.example.examine;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

/**
 * 用户二维码生成器
 * <p>
 * <p>
 * 根据A4纸尺寸是210毫米×297毫米，而1英寸=2.54厘米，我们可以得出当分辨率是多少像素时，得出A4纸大小尺寸为多少毫米。如下是我们较长用到的规格尺寸：
 * 当分辨率是72像素/英寸时， A4纸像素长宽分别是842×595；
 * 当分辨率是96像素/英寸时， A4纸像素长宽分别是1123x794；(默认)
 * 当分辨率是120像素/英寸时，A4纸像素长宽分别是2105×1487；
 * 当分辨率是150像素/英寸时，A4纸像素长宽分别是1754×1240；
 * 当分辨率是300像素/英寸时，A4纸像素长宽分别是3508×2479；
 */
public class UserQrCodeGenerator extends JPanel {
    public static void main(String[] args) {
        System.setProperty("swing.plaf.metal.controlFont", "宋体");
        System.out.println("屏幕尺寸" + Toolkit.getDefaultToolkit().getScreenSize());
        new UserQrCodeGenerator();
    }

    JPanel leftPanel, rightPanel, corePanel;
    JSplitPane splitPane;
    TextArea leftTextArea;
    JComboBox qrSizeComboBox;
    JTextField defaultPasswordTextField;
    ArrayList<UserQrCodeEntity> userEntities = new ArrayList<UserQrCodeEntity>();

    public UserQrCodeGenerator() {
        initData();
        //
        configUi();
        //
        configCommon();
    }

    private void logInfo(Object x) {
        String s = String.valueOf(x);
        System.out.println(s);
    }

    /**
     * A4大小的尺寸
     *
     * @return
     */
    private Dimension getA4Size() {
        return new Dimension(595, 842);
    }

    private void initData() {
        userEntities.add(new UserQrCodeEntity("张三", "0500501", "123451", 120));
        userEntities.add(new UserQrCodeEntity("聂海胜", "0500502", "123452", 120));
        userEntities.add(new UserQrCodeEntity("刘伯明", "0500503", "123453", 120));
        userEntities.add(new UserQrCodeEntity("汤洪波", "0500504", "123454", 120));
        userEntities.add(new UserQrCodeEntity("神舟十二", "0500505", "123455", 120));
        userEntities.add(new UserQrCodeEntity("测试员", "0500506", "123456", 120));
    }

    private void configUi() {
        leftPanel = new JPanel();
        leftPanel.setBackground(Color.white);
        leftPanel.setLayout(new BorderLayout());
        // 左边文本输入框
        leftTextArea = new TextArea();
        leftTextArea.append("用户名@账号@密码\n");
        leftTextArea.append("test@1234567@123456\n");
        leftTextArea.append("测试员@0500506@123123\n");
        leftTextArea.append("测试测试@0500501@654321\n");
        leftTextArea.append("测试测试@0500502@654322\n");
        leftTextArea.append("测试测试@0500503@654323\n");
        leftTextArea.append("测试测试@0500504@654324\n");
        leftTextArea.append("测试@0500502\n");
        leftPanel.add(leftTextArea, BorderLayout.CENTER);
        // 左边顶部
        JPanel leftTopPanel = new JPanel();
//        leftTopPanel.setLayout(new FlowLayout(SwingConstants.LEFT));
        leftTopPanel.setLayout(new GridLayout(2, 4));
        leftTopPanel.setSize(400, 400);
        leftPanel.setSize(600, 400);
        leftPanel.add(leftTopPanel, BorderLayout.NORTH);
        //
        JLabel textField = new JLabel("默认密码:");
        leftTopPanel.add(textField);
        //
        defaultPasswordTextField = new JTextField("123456");
        leftTopPanel.add(defaultPasswordTextField);
        textField = new JLabel("大小:");
        leftTopPanel.add(textField);
        // 二维码大小选择框
        qrSizeComboBox = new JComboBox();
        qrSizeComboBox.addItem("100");
        qrSizeComboBox.addItem("120");
        qrSizeComboBox.addItem("140");
        qrSizeComboBox.addItem("160");
        qrSizeComboBox.addItem("180");
        qrSizeComboBox.addItem("200");
        qrSizeComboBox.setEditable(true);
        qrSizeComboBox.setSelectedItem("140");
        qrSizeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("选择的大小是" + qrSizeComboBox.getSelectedItem());
                for (UserQrCodeEntity entity : userEntities) {
                    entity.setSize(Integer.parseInt(qrSizeComboBox.getSelectedItem().toString()));
                }
                generateQrCode();
            }
        });
        leftTopPanel.add(qrSizeComboBox);

        // 生成二维码按钮
        JButton button = new JButton("生成");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("点击生成二维码按钮");
                generateQrCode();
            }
        });
        //
        leftTopPanel.add(button);
        JButton button1 = new JButton("导出");
        leftTopPanel.add(button1);
        button1.addActionListener((e) -> {
            corePanel.revalidate();
            String pdfName = JOptionPane.showInputDialog(null, "请输入导入的PDF名称。\n默认名称为:用户二维码信息_yyyyMMdd_HHmm", "导出", JOptionPane.PLAIN_MESSAGE);
            UserQrCodeHelper.exportToPDF(corePanel, pdfName);
        });
        // 打印按钮 //////////////////////////////////////////////////////////////////////////////////
        JButton button2 = new JButton("打印");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(leftTopPanel, "家里没有打印机！功能尚未开发。敬请期待");
            }
        });
        leftTopPanel.add(button2);
        leftTopPanel.setSize(400, 200);

        // 底部的提示语
        JLabel leftInfoLabel = new JLabel("<html>" +
                "使用说明:<br/>" +
                "1、每一行写一个账号。<br/>" +
                "2、格式为<span style=\"font-weight:bold;font-size:16px;\">用户名@账号@密码</span><br/>" +
                "3、密码可以不填写，就是使用顶部的默认密码。<br/>" +
                "4、点击生成按钮，预览结果。<br/>" +
                "5、点击导出就会在软件所在文件夹生成一个PDF文件。<br/>" +
                "6、仅作为辅助工具使用。<br/>" +
                "<br/></html>");
        leftInfoLabel.setSize(300, 500);
        leftPanel.add("South", leftInfoLabel);

        // 右边的大面板
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(getA4Size());
        rightPanel.setSize(getA4Size());
        rightPanel.setBackground(Color.lightGray);

        //
        JPanel panel = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        panel.setLayout(layout);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(panel, BorderLayout.NORTH);
        //   rightPanel.add(new JLabel(""), BorderLayout.EAST);
        // 二维码面板
        corePanel = new JPanel();
        corePanel.setSize(getA4Size());
        corePanel.setPreferredSize(getA4Size());
        corePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        corePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        rightPanel.add(corePanel);

        leftPanel.setSize(300, 600);
        rightPanel.setSize(corePanel.getWidth() + 20, 500);

        setLayout(new BorderLayout(10, 10));
        add("Center", leftPanel);
        add("East", rightPanel);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
//                splitPane.setDividerLocation(0.3);
            }
        });
        //
        generateQrCode();
    }


    private void configCommon() {
        setSize(leftPanel.getWidth() + rightPanel.getWidth() + 200, 800);
        setVisible(true);
    }

    /**
     * 文本 转 实体
     */
    private void generateUsers() {
        userEntities.clear();
        String input = leftTextArea.getText();
        String[] lines = input.split("\n");
        for (String line : lines) {
            String[] infos = line.split("@");
            UserQrCodeEntity entity = new UserQrCodeEntity(Integer.parseInt(qrSizeComboBox.getSelectedItem().toString()));
            if (null != infos) {
                if (infos.length >= 1) {
                    entity.setName(infos[0]);
                }
                if (infos.length >= 2) {
                    entity.setAccount(infos[1]);
                }
                if (infos.length >= 3) {
                    entity.setPassword(infos[2]);
                } else {
                    entity.setPassword(defaultPasswordTextField.getText());
                }
                userEntities.add(entity);
            }
        }
    }

    /**
     * 生成二维码
     */
    private void generateQrCode() {
        // 封装实体
        generateUsers();

        // 绘制组件
        logInfo("corePanel:移除所有的子组件");
        corePanel.removeAll();
        repaint();
        for (UserQrCodeEntity entity : userEntities) {
            logInfo(entity);
            corePanel.add(new UserQrCodeBox2(entity));
        }
        revalidate();
    }
}

class UserQrCodeBox extends JPanel {
    private UserQrCodeEntity boxEntity;

    public UserQrCodeBox(UserQrCodeEntity entity) {
        super();
        this.boxEntity = entity;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        //
        JLabel imageLabel = new JLabel();
        // 设置图片
        ImageIcon imageIcon = new ImageIcon(UserQrCodeHelper.createImage("" + boxEntity.getAccount() + boxEntity.getPassword(), boxEntity));
        imageIcon.getImage().getScaledInstance(entity.getSize(), entity.getSize(), Image.SCALE_DEFAULT);
        imageLabel.setIcon(imageIcon);

        // 设置文字在图片下方
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalTextPosition(JLabel.BOTTOM);
        imageLabel.setHorizontalTextPosition(JLabel.CENTER);
        //
        imageLabel.setSize(entity.getSize(), entity.getSize());

        imageLabel.setText("" + boxEntity.getName() + " [" + boxEntity.getAccount() + "]");
        imageLabel.setFont(new Font(null, Font.PLAIN, entity.getSize() / 11));
        add(imageLabel);
//
        setSize(entity.getSize() + 20, entity.getSize() + 20);
        setBackground(Color.WHITE);
    }
}

class UserQrCodeBox2 extends JPanel {
    private UserQrCodeEntity boxEntity;

    public UserQrCodeBox2(UserQrCodeEntity entity) {
        super();
        //  System.out.println("UserQrCodeBox2");
        this.boxEntity = entity;
        setLayout(new BorderLayout());
        //
        JLabel imageLabel = new JLabel();
        // 设置图片
        ImageIcon imageIcon = new ImageIcon(UserQrCodeHelper.createImage("" + boxEntity.getAccount() + boxEntity.getPassword(), boxEntity));
        imageIcon.getImage().getScaledInstance(entity.getSize(), entity.getSize(), Image.SCALE_DEFAULT);
        imageLabel.setIcon(imageIcon);

        // 设置文字在图片下方
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalTextPosition(JLabel.BOTTOM);
        imageLabel.setHorizontalTextPosition(JLabel.CENTER);
        //
        imageLabel.setSize(entity.getSize(), entity.getSize());

        //
        PdfStringPanel nameP = new PdfStringPanel(
                entity.getName() + "[" + entity.getAccount() + "]",
                entity.getSize() / 11,
                new Dimension(entity.getSize(), 30 * entity.getSize() / 140));
        nameP.setBackground(Color.WHITE);
        //
        add(imageLabel, BorderLayout.CENTER);
        add(nameP, BorderLayout.SOUTH);
        //
        setSize(entity.getSize() + 20, entity.getSize() + 40);
        setBackground(Color.WHITE);
    }
}

class UserQrCodeLabel extends JLabel {
    private UserQrCodeEntity boxEntity;

    public UserQrCodeLabel(UserQrCodeEntity entity) {
        super();
        this.boxEntity = entity;
        // 设置图片
        ImageIcon imageIcon = new ImageIcon(UserQrCodeHelper.createImage("" + boxEntity.getAccount() + boxEntity.getPassword(), boxEntity));
        // imageIcon.getImage().getScaledInstance(entity.getSize(), entity.getSize(), Image.SCALE_DEFAULT);
        setIcon(imageIcon);

        // 设置文字在图片下方
        setVerticalTextPosition(JLabel.BOTTOM);
        setHorizontalTextPosition(JLabel.CENTER);
        // 设置文字居中
        setHorizontalAlignment(JLabel.RIGHT);
        setVerticalAlignment(JLabel.BOTTOM);
        //
        setText("" + boxEntity.getName() + "[" + boxEntity.getAccount() + "]");
//        setText("<html>" + boxEntity.getName() + "[" + boxEntity.getAccount() + "]</html>");
        setFont(new Font("宋体", Font.PLAIN, entity.getSize() / 11));
        setForeground(Color.RED);
        //
        setSize(entity.getSize() + 20, entity.getSize() + 20);
    }
}

/**
 * 用户二维码 实体
 */
class UserQrCodeEntity {
    private String name;
    private String account;
    private String password;
    private Integer size = 100;

    public UserQrCodeEntity(Integer size) {
        this.size = size;
        this.name = "";
        this.account = "";
        this.password = "";
    }

    public UserQrCodeEntity(String name, String account, String password, Integer size) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "UserQrCodeEntity{" +
                "name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", size=" + size +
                '}';
    }
}

class UserQrCodeHelper {
    /**
     * 根据文字内容获取二维码图片
     *
     * @param content
     * @return
     */
    public static BufferedImage createImage(String content, UserQrCodeEntity boxEntity) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, boxEntity.getSize() - 20, boxEntity.getSize() - 20, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return image;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出PDF
     *
     * @param panel 需要导出的面板
     */
    public static void exportToPDF(JPanel panel, String fileName) {
        if (null == fileName || "".equals(fileName)) {
            fileName = "用户二维码信息";
        }
        fileName += "_";
        Document document = new Document();
        String outPath = fileName + new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date()) + ".pdf";
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outPath));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(panel.getWidth(), panel.getHeight());
            Graphics2D g2 = template.createGraphics(panel.getWidth() + 20, panel.getHeight());
            panel.print(g2);
            g2.dispose();
            contentByte.addTemplate(template, 0, 0);

            File outFile = new File(outPath);
            String pdf = outFile.getAbsolutePath();
            if (null != pdf && !"".equals(pdf)) {
                // 播放系统告警音
                Toolkit.getDefaultToolkit().beep();
                //
                JOptionPane.showConfirmDialog(null,
                        "PDF导出成功，保存路径为:\n" + pdf,
                        "导出成功", JOptionPane.PLAIN_MESSAGE);
                //                System.out.println(outFile.getParent());// null
                //java获取文件路径的父目录 https://blog.csdn.net/wuzuyu365/article/details/53256860/

                // 打开导出文件夹
                Desktop.getDesktop().open(new File(new File(pdf).getParent()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("导出PDF完成:" + outPath);
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}

/**
 * 中文不能导出PDF
 */
class PdfStringPanel extends JPanel {

    private Shape shape;
    private Dimension dimension;
    private String text;
    private float fontSize;

    public PdfStringPanel(String text, float fontSize, Dimension dimension) {
        super();
        this.dimension = dimension;
        this.text = text;
        this.fontSize = fontSize;
        //
        Font f = getFont().deriveFont(Font.PLAIN, fontSize);
        GlyphVector v = f.createGlyphVector(getFontMetrics(f).getFontRenderContext(), text);
        shape = v.getOutline();
        setPreferredSize(dimension);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics metrics = g.getFontMetrics(getFont().deriveFont(Font.PLAIN, fontSize));
        Rectangle2D bounds2 = metrics.getStringBounds(this.text, null);
//        System.out.println("Rectangle2D is :" + bounds2);
        double tx = (this.dimension.width - bounds2.getWidth()) * 0.5;
        double ty = (this.dimension.height - bounds2.getY() + 1 - bounds2.getHeight()) * 0.5;
//        System.out.println("偏移量X:" + tx + "\tY:" + ty);
        g2.translate(tx, ty);
//        g2.rotate(0.4);
//        g2.setPaint(Color.red);
        g2.fill(shape);
//        g2.setPaint(Color.black);
//        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1, new float[]{1, 0.4f, 1.5f}, 0));
        g2.draw(shape);
    }
}
