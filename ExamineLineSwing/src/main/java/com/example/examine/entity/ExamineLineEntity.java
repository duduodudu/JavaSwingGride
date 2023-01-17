package com.example.examine.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查线的实体
 * edu 教育
 * official 正式
 * standby 备用
 */
public class ExamineLineEntity {
    private String name;// 名称
    private String eduWsUrl;// 教育环境地址
    private String eduPcUrl;// 教育环境地址
    private String officialWsUrl;// 正式环境
    private String officialPcUrl;// 正式环境
    private String standbyWsUrl;// 备用环境
    private String standbyPcUrl;// 备用环境
    private String nginxWsUrl;// 负载均衡

    public ExamineLineEntity() {

    }

    public ExamineLineEntity(String name, String eduWsUrl, String eduPcUrl, String officialWsUrl, String officialPcUrl) {
        this.name = name;
        this.eduWsUrl = eduWsUrl;
        this.eduPcUrl = eduPcUrl;
        this.officialWsUrl = officialWsUrl;
        this.officialPcUrl = officialPcUrl;
        //
//        this.nginxWsUrl = officialWsUrl;
//        this.standbyWsUrl = officialWsUrl;
//        this.standbyPcUrl = officialPcUrl;
    }

    public ExamineLineEntity(String name, String eduWsUrl, String eduPcUrl, String officialWsUrl, String officialPcUrl, String standbyWsUrl, String standbyPcUrl, String nginxWsUrl) {
        this.name = name;
        this.eduWsUrl = eduWsUrl;
        this.eduPcUrl = eduPcUrl;
        this.officialWsUrl = officialWsUrl;
        this.officialPcUrl = officialPcUrl;
        this.standbyWsUrl = standbyWsUrl;
        this.standbyPcUrl = standbyPcUrl;
        this.nginxWsUrl = nginxWsUrl;
    }

    public String getName() {
        return name;
    }

    public String getEduName() {
        return name + "(教育)";
    }

    public String getOfficialName() {
        return name + "(正式)";
    }

    public String getStandbyName() {
        return name + "(备用)";
    }

    public String getNginxName() {
        return name + "(负载)";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEduWsUrl() {
        return eduWsUrl;
    }

    public void setEduWsUrl(String eduWsUrl) {
        this.eduWsUrl = eduWsUrl;
    }

    public String getEduPcUrl() {
        return eduPcUrl;
    }

    public void setEduPcUrl(String eduPcUrl) {
        this.eduPcUrl = eduPcUrl;
    }

    public String getOfficialWsUrl() {
        return officialWsUrl;
    }

    public void setOfficialWsUrl(String officialWsUrl) {
        this.officialWsUrl = officialWsUrl;
    }

    public String getOfficialPcUrl() {
        return officialPcUrl;
    }

    public void setOfficialPcUrl(String officialPcUrl) {
        this.officialPcUrl = officialPcUrl;
    }

    public String getStandbyWsUrl() {
        return standbyWsUrl;
    }

    public void setStandbyWsUrl(String standbyWsUrl) {
        this.standbyWsUrl = standbyWsUrl;
    }

    public String getStandbyPcUrl() {
        return standbyPcUrl;
    }

    public void setStandbyPcUrl(String standbyPcUrl) {
        this.standbyPcUrl = standbyPcUrl;
    }

    public String getNginxWsUrl() {
        return nginxWsUrl;
    }

    public void setNginxWsUrl(String nginxWsUrl) {
        this.nginxWsUrl = nginxWsUrl;
    }

    /**
     * 测试环境的地址
     *
     * @return
     */
    static public List<ExamineLineEntity> GetDemoLine() {
        List<ExamineLineEntity> list = new ArrayList<>();
        list.add(new ExamineLineEntity(
                "云谷测试",
                "http://192.168.132.4:8081/ExamineLine/rest/GetProperties",
                "http://192.168.132.4:8085/examineLine",
                "http://192.168.132.3:8081/ExamineLine/rest/GetProperties",
                "http://192.168.132.3:8085/examineLine"
        ));
        list.add(new ExamineLineEntity(
                "云谷测试Error",
                "http://192.168.132.3:8081/ExamineLine/rest/GetProperties",
                "http://192.168.132.3:8081/examineLine",
                "http://192.168.132.4:8081/ExamineLine/rest/GetProperties",
                "http://192.168.132.4:8081/examineLine"
        ));
        return list;
    }

}
