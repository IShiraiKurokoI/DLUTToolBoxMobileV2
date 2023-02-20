package com.Shirai_Kuroko.DLUTMobile.Entities;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName DrcomStatusBean.java
 * @Description TODO
 * @createTime 2023年02月20日 18:29
 */
@NoArgsConstructor
@Data
public class DrcomStatusBean {

    @JSONField(name = "result")
    private Integer result;
    @JSONField(name = "time")
    private Integer time;
    @JSONField(name = "flow")
    private Long flow;
    @JSONField(name = "fsele")
    private Integer fsele;
    @JSONField(name = "fee")
    private Integer fee;
    @JSONField(name = "m46")
    private Integer m46;
    @JSONField(name = "v46ip")
    private String v46ip;
    @JSONField(name = "myv6ip")
    private String myv6ip;
    @JSONField(name = "oltime")
    private Long oltime;
    @JSONField(name = "olflow")
    private Long olflow;
    @JSONField(name = "lip")
    private String lip;
    @JSONField(name = "stime")
    private String stime;
    @JSONField(name = "etime")
    private String etime;
    @JSONField(name = "uid")
    private String uid;
    @JSONField(name = "v6af")
    private Integer v6af;
    @JSONField(name = "v6df")
    private Integer v6df;
    @JSONField(name = "v46m")
    private Integer v46m;
    @JSONField(name = "v4ip")
    private String v4ip;
    @JSONField(name = "v6ip")
    private String v6ip;
    @JSONField(name = "AC")
    private String ac;
    @JSONField(name = "ss5")
    private String ss5;
    @JSONField(name = "ss6")
    private String ss6;
    @JSONField(name = "vid")
    private Integer vid;
    @JSONField(name = "ss1")
    private String ss1;
    @JSONField(name = "ss4")
    private String ss4;
    @JSONField(name = "cvid")
    private Integer cvid;
    @JSONField(name = "pvid")
    private Integer pvid;
    @JSONField(name = "hotel")
    private Integer hotel;
    @JSONField(name = "aolno")
    private Integer aolno;
    @JSONField(name = "eport")
    private Integer eport;
    @JSONField(name = "eclass")
    private Integer eclass;
    @JSONField(name = "zxopt")
    private Integer zxopt;
    @JSONField(name = "NID")
    private String nid;
    @JSONField(name = "udate")
    private String udate;
    @JSONField(name = "olmac")
    private String olmac;
    @JSONField(name = "ollm")
    private Integer ollm;
    @JSONField(name = "olm1")
    private String olm1;
    @JSONField(name = "olm2")
    private String olm2;
    @JSONField(name = "olm3")
    private Integer olm3;
    @JSONField(name = "olmm")
    private Integer olmm;
    @JSONField(name = "olm5")
    private Integer olm5;
    @JSONField(name = "gid")
    private Integer gid;
    @JSONField(name = "ispid")
    private Integer ispid;
    @JSONField(name = "opip")
    private String opip;
    @JSONField(name = "olno")
    private Integer olno;
    @JSONField(name = "olip")
    private String olip;
    @JSONField(name = "oaf")
    private Integer oaf;
    @JSONField(name = "oat")
    private Integer oat;
    @JSONField(name = "actM")
    private Integer actM;
    @JSONField(name = "actt")
    private Integer actt;
    @JSONField(name = "actdf")
    private Integer actdf;
    @JSONField(name = "actuf")
    private Integer actuf;
    @JSONField(name = "act6df")
    private Integer act6df;
    @JSONField(name = "act6uf")
    private Integer act6uf;
    @JSONField(name = "allfm")
    private Integer allfm;
    @JSONField(name = "d1")
    private Integer d1;
    @JSONField(name = "u1")
    private Integer u1;
    @JSONField(name = "d2")
    private Integer d2;
    @JSONField(name = "u2")
    private Integer u2;
    @JSONField(name = "o1")
    private Integer o1;
    @JSONField(name = "nd1")
    private Integer nd1;
    @JSONField(name = "nu1")
    private Integer nu1;
    @JSONField(name = "nd2")
    private Integer nd2;
    @JSONField(name = "nu2")
    private Integer nu2;
    @JSONField(name = "no1")
    private Integer no1;

    public Integer getResult() {
        return result;
    }

    public Integer getTime() {
        return time;
    }

    public Long getFlow() {
        return flow;
    }

    public Integer getFsele() {
        return fsele;
    }

    public Integer getFee() {
        return fee;
    }

    public Integer getM46() {
        return m46;
    }

    public String getV46ip() {
        return v46ip;
    }

    public String getMyv6ip() {
        return myv6ip;
    }

    public Long getOltime() {
        return oltime;
    }

    public Long getOlflow() {
        return olflow;
    }

    public String getLip() {
        return lip;
    }

    public String getStime() {
        return stime;
    }

    public String getEtime() {
        return etime;
    }

    public String getUid() {
        return uid;
    }

    public Integer getV6af() {
        return v6af;
    }

    public Integer getV6df() {
        return v6df;
    }

    public Integer getV46m() {
        return v46m;
    }

    public String getV4ip() {
        return v4ip;
    }

    public String getV6ip() {
        return v6ip;
    }

    public String getAc() {
        return ac;
    }

    public String getSs5() {
        return ss5;
    }

    public String getSs6() {
        return ss6;
    }

    public Integer getVid() {
        return vid;
    }

    public String getSs1() {
        return ss1;
    }

    public String getSs4() {
        return ss4;
    }

    public Integer getCvid() {
        return cvid;
    }

    public Integer getPvid() {
        return pvid;
    }

    public Integer getHotel() {
        return hotel;
    }

    public Integer getAolno() {
        return aolno;
    }

    public Integer getEport() {
        return eport;
    }

    public Integer getEclass() {
        return eclass;
    }

    public Integer getZxopt() {
        return zxopt;
    }

    public String getNid() {
        return nid;
    }

    public String getUdate() {
        return udate;
    }

    public String getOlmac() {
        return olmac;
    }

    public Integer getOllm() {
        return ollm;
    }

    public String getOlm1() {
        return olm1;
    }

    public String getOlm2() {
        return olm2;
    }

    public Integer getOlm3() {
        return olm3;
    }

    public Integer getOlmm() {
        return olmm;
    }

    public Integer getOlm5() {
        return olm5;
    }

    public Integer getGid() {
        return gid;
    }

    public Integer getIspid() {
        return ispid;
    }

    public String getOpip() {
        return opip;
    }

    public Integer getOlno() {
        return olno;
    }

    public String getOlip() {
        return olip;
    }

    public Integer getOaf() {
        return oaf;
    }

    public Integer getOat() {
        return oat;
    }

    public Integer getActM() {
        return actM;
    }

    public Integer getActt() {
        return actt;
    }

    public Integer getActdf() {
        return actdf;
    }

    public Integer getActuf() {
        return actuf;
    }

    public Integer getAct6df() {
        return act6df;
    }

    public Integer getAct6uf() {
        return act6uf;
    }

    public Integer getAllfm() {
        return allfm;
    }

    public Integer getD1() {
        return d1;
    }

    public Integer getU1() {
        return u1;
    }

    public Integer getD2() {
        return d2;
    }

    public Integer getU2() {
        return u2;
    }

    public Integer getO1() {
        return o1;
    }

    public Integer getNd1() {
        return nd1;
    }

    public Integer getNu1() {
        return nu1;
    }

    public Integer getNd2() {
        return nd2;
    }

    public Integer getNu2() {
        return nu2;
    }

    public Integer getNo1() {
        return no1;
    }
}
