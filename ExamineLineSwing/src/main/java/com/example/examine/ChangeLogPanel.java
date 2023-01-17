package com.example.examine;

/**
 * 版本更新说明
 */
public class ChangeLogPanel extends AboutPanel {
    @Override
    public void initText() {
        // super.initText(); // 无需调用父类
        appendTitle("软件更新说明:");
        appendVersion("v0.1.20221217");
        appendVersionDesc("图片下载工具:跨线别导入新车型时，可以批量下载指定的图片。");
    }

    /**
     * 添加说明点
     *
     * @param message
     */
    protected void appendVersion(String message) {
        appendSplitLine();
        appendTitle(message);
    }

    protected void appendVersionDesc(String message) {
        appendDescription("- " + message);
    }
}
