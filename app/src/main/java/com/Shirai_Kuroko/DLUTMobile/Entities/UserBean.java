package com.Shirai_Kuroko.DLUTMobile.Entities;

import java.io.*;
import com.google.gson.annotations.*;
import java.util.*;
import android.text.*;

public class UserBean extends BaseBean implements Serializable
{
    public static final String IDENTITY_BACHELOR = "bachelor";
    public static final String IDENTITY_DOCTOR = "doctor";
    public static final String IDENTITY_FATHER = "father";
    public static final String IDENTITY_MASTER = "master";
    public static final String IDENTITY_MOTHER = "mather";
    public static final String IDENTITY_OTHER = "other";
    public static final String IDENTITY_PARENT = "parent";
    public static final String IDENTITY_STUDENT = "student";
    public static final String IDENTITY_TEACHER = "teacher";
    public static final String IDENTITY_VISITOR = "visitor";
    public static final String SEX_BOY = "boy";
    public static final String SEX_GIRL = "girl";
    private static UserBean defaultUser;
    private static final long serialVersionUID = 1L;
    private String card_number;
    private String celphone;
    private String course_name;
    private String email;
    private boolean has_parent;
    private String head;
    private String id_photo;
    private String identity;
    private String jid;
    private List<String> l_subject;
    private List<String> l_title;
    private String landline;
    private String mood_words;
    private String name;
    private String nick_name;
    private String officephone;
    private List<String> org;
    private List<String> orgPath;
    @SerializedName("parent_active")
    private int parentActive;
    @SerializedName("parent_activetime")
    private long parentActiveTime;
    @SerializedName("parent_bind")
    private String parentBind;
    private String parent_title;
    private String pinyin;
    private String pycc;
    private String qqcode;
    private String receipt_opt_info;
    private int receiverType;
    private String sex;
    private String short_url;
    private String skey;
    private String stu_identity;
    private String student_number;
    private String sznj;
    private String tittle;
    private String user_id;
    private String verify;
    private String verify_phone;
    private String wechatcode;

    public UserBean() {
        this.receiverType = 2;
    }

    public static UserBean getDefaultUser() {
        if (UserBean.defaultUser == null) {
            (UserBean.defaultUser = new UserBean()).setCard_number("");
            UserBean.defaultUser.setCelphone("");
            UserBean.defaultUser.setCourse_name("");
            UserBean.defaultUser.setEmail("");
            UserBean.defaultUser.setHas_parent(false);
            UserBean.defaultUser.setHead("");
            UserBean.defaultUser.setIdentity("");
            UserBean.defaultUser.setJid("");
            UserBean.defaultUser.setLandline("");
            UserBean.defaultUser.setMood_words("");
            UserBean.defaultUser.setName("\u795e\u79d8\u4eba");
            UserBean.defaultUser.setNick_name("");
            UserBean.defaultUser.setOrg(new ArrayList<String>());
            UserBean.defaultUser.setPinyin("shenmiren");
            UserBean.defaultUser.setReceipt_opt_info("");
            UserBean.defaultUser.setReceiverType(0);
            UserBean.defaultUser.setSex("");
            UserBean.defaultUser.setSkey("");
            UserBean.defaultUser.setTitle("");
            UserBean.defaultUser.setUser_id("");
            UserBean.defaultUser.setVerify("");
            UserBean.defaultUser.setVerify_phone("");
            UserBean.defaultUser.setOfficephone("");
            UserBean.defaultUser.setWechatcode("");
            UserBean.defaultUser.setQqcode("");
            UserBean.defaultUser.setPycc("");
            UserBean.defaultUser.setStu_identity("");
        }
        return UserBean.defaultUser;
    }

    @Override
    public boolean equals(final Object o) {
        boolean b = true;
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final UserBean userBean = (UserBean)o;
        if (this.has_parent != userBean.has_parent) {
            return false;
        }
        final String student_number = this.student_number;
        Label_0077: {
            if (student_number != null) {
                if (student_number.equals(userBean.student_number)) {
                    break Label_0077;
                }
            }
            else if (userBean.student_number == null) {
                break Label_0077;
            }
            return false;
        }
        final String name = this.name;
        Label_0109: {
            if (name != null) {
                if (name.equals(userBean.name)) {
                    break Label_0109;
                }
            }
            else if (userBean.name == null) {
                break Label_0109;
            }
            return false;
        }
        final String sex = this.sex;
        Label_0141: {
            if (sex != null) {
                if (sex.equals(userBean.sex)) {
                    break Label_0141;
                }
            }
            else if (userBean.sex == null) {
                break Label_0141;
            }
            return false;
        }
        final String identity = this.identity;
        Label_0173: {
            if (identity != null) {
                if (identity.equals(userBean.identity)) {
                    break Label_0173;
                }
            }
            else if (userBean.identity == null) {
                break Label_0173;
            }
            return false;
        }
        final String head = this.head;
        Label_0205: {
            if (head != null) {
                if (head.equals(userBean.head)) {
                    break Label_0205;
                }
            }
            else if (userBean.head == null) {
                break Label_0205;
            }
            return false;
        }
        final String user_id = this.user_id;
        Label_0237: {
            if (user_id != null) {
                if (user_id.equals(userBean.user_id)) {
                    break Label_0237;
                }
            }
            else if (userBean.user_id == null) {
                break Label_0237;
            }
            return false;
        }
        final String mood_words = this.mood_words;
        Label_0269: {
            if (mood_words != null) {
                if (mood_words.equals(userBean.mood_words)) {
                    break Label_0269;
                }
            }
            else if (userBean.mood_words == null) {
                break Label_0269;
            }
            return false;
        }
        final String nick_name = this.nick_name;
        Label_0301: {
            if (nick_name != null) {
                if (nick_name.equals(userBean.nick_name)) {
                    break Label_0301;
                }
            }
            else if (userBean.nick_name == null) {
                break Label_0301;
            }
            return false;
        }
        final String celphone = this.celphone;
        Label_0333: {
            if (celphone != null) {
                if (celphone.equals(userBean.celphone)) {
                    break Label_0333;
                }
            }
            else if (userBean.celphone == null) {
                break Label_0333;
            }
            return false;
        }
        final String email = this.email;
        Label_0365: {
            if (email != null) {
                if (email.equals(userBean.email)) {
                    break Label_0365;
                }
            }
            else if (userBean.email == null) {
                break Label_0365;
            }
            return false;
        }
        final String landline = this.landline;
        Label_0397: {
            if (landline != null) {
                if (landline.equals(userBean.landline)) {
                    break Label_0397;
                }
            }
            else if (userBean.landline == null) {
                break Label_0397;
            }
            return false;
        }
        final String tittle = this.tittle;
        Label_0429: {
            if (tittle != null) {
                if (tittle.equals(userBean.tittle)) {
                    break Label_0429;
                }
            }
            else if (userBean.tittle == null) {
                break Label_0429;
            }
            return false;
        }
        final String jid = this.jid;
        final String jid2 = userBean.jid;
        if (jid != null) {
            if (jid.equals(jid2)) {
                return b;
            }
        }
        else if (jid2 == null) {
            return b;
        }
        b = false;
        return b;
    }

    public String getCard_number() {
        return this.card_number;
    }

    public String getCelphone() {
        return this.celphone;
    }

    public String getCourse_name() {
        return this.course_name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getHead() {
        return this.head;
    }

    @Override
    public String getId() {
        return this.getUser_id();
    }

    public String getId_photo() {
        return this.id_photo;
    }

    public String getIdentity() {
        return this.identity;
    }

    public String getJid() {
        return this.jid;
    }

    public List<String> getL_subject() {
        return this.l_subject;
    }

    public List<String> getL_title() {
        return this.l_title;
    }

    public String getLandline() {
        return this.landline;
    }

    public String getMood_words() {
        return this.mood_words;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getNick_name() {
        return this.nick_name;
    }

    public String getOfficephone() {
        return this.officephone;
    }

    public List<String> getOrg() {
        if (this.org == null) {
            this.org = new ArrayList<String>();
        }
        return this.org;
    }

    public List<String> getOrgPath() {
        return this.orgPath;
    }

    public int getParentActive() {
        return this.parentActive;
    }

    public long getParentActiveTime() {
        return this.parentActiveTime;
    }

    public String getParentBind() {
        return this.parentBind;
    }

    public String getParent_title() {
        return this.parent_title;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public String getPycc() {
        return this.pycc;
    }

    public String getQqcode() {
        return this.qqcode;
    }

    public String getReceipt_opt_info() {
        return this.receipt_opt_info;
    }

    @Override
    public int getReceiverType() {
        return this.receiverType;
    }

    public String getSex() {
        return this.sex;
    }

    public String getShort_url() {
        return this.short_url;
    }

    public String getSkey() {
        return this.skey;
    }

    public String getStudent_number() {
        return this.student_number;
    }

    public String getSznj() {
        return this.sznj;
    }

    public String getTitle() {
        return this.tittle;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public String getVerify() {
        return this.verify;
    }

    public String getVerify_phone() {
        return this.verify_phone;
    }

    public String getWechatcode() {
        return this.wechatcode;
    }

    public boolean hasBindParent() {
        return this.has_parent;
    }

    @Override
    public int hashCode() {
        final String student_number = this.student_number;
        int hashCode = 0;
        int hashCode2;
        if (student_number != null) {
            hashCode2 = student_number.hashCode();
        }
        else {
            hashCode2 = 0;
        }
        final String name = this.name;
        int hashCode3;
        if (name != null) {
            hashCode3 = name.hashCode();
        }
        else {
            hashCode3 = 0;
        }
        final String sex = this.sex;
        int hashCode4;
        if (sex != null) {
            hashCode4 = sex.hashCode();
        }
        else {
            hashCode4 = 0;
        }
        final String identity = this.identity;
        int hashCode5;
        if (identity != null) {
            hashCode5 = identity.hashCode();
        }
        else {
            hashCode5 = 0;
        }
        final String head = this.head;
        int hashCode6;
        if (head != null) {
            hashCode6 = head.hashCode();
        }
        else {
            hashCode6 = 0;
        }
        final String user_id = this.user_id;
        int hashCode7;
        if (user_id != null) {
            hashCode7 = user_id.hashCode();
        }
        else {
            hashCode7 = 0;
        }
        final String mood_words = this.mood_words;
        int hashCode8;
        if (mood_words != null) {
            hashCode8 = mood_words.hashCode();
        }
        else {
            hashCode8 = 0;
        }
        final String nick_name = this.nick_name;
        int hashCode9;
        if (nick_name != null) {
            hashCode9 = nick_name.hashCode();
        }
        else {
            hashCode9 = 0;
        }
        final String celphone = this.celphone;
        int hashCode10;
        if (celphone != null) {
            hashCode10 = celphone.hashCode();
        }
        else {
            hashCode10 = 0;
        }
        final String email = this.email;
        int hashCode11;
        if (email != null) {
            hashCode11 = email.hashCode();
        }
        else {
            hashCode11 = 0;
        }
        final String landline = this.landline;
        int hashCode12;
        if (landline != null) {
            hashCode12 = landline.hashCode();
        }
        else {
            hashCode12 = 0;
        }
        final String tittle = this.tittle;
        int hashCode13;
        if (tittle != null) {
            hashCode13 = tittle.hashCode();
        }
        else {
            hashCode13 = 0;
        }
        final String jid = this.jid;
        if (jid != null) {
            hashCode = jid.hashCode();
        }
        return ((((((((((((hashCode2 * 31 + hashCode3) * 31 + hashCode4) * 31 + hashCode5) * 31 + hashCode6) * 31 + hashCode7) * 31 + hashCode8) * 31 + hashCode9) * 31 + hashCode10) * 31 + hashCode11) * 31 + hashCode12) * 31 + hashCode13) * 31 + hashCode) * 31 + (this.has_parent ? 1 : 0);
    }

    public boolean isAbsoluteStudent() {
        return "student".equals(this.getIdentity()) && TextUtils.isEmpty((CharSequence)this.stu_identity);
    }

    public boolean isBachelor() {
        return "bachelor".equals(this.getPycc());
    }

    public boolean isBoy() {
        return "boy".equals(this.sex);
    }

    public boolean isDoctor() {
        return "doctor".equals(this.getPycc());
    }

    public boolean isGirl() {
        return "girl".equals(this.sex);
    }

    public boolean isHas_parent() {
        return this.has_parent;
    }

    public boolean isMaster() {
        return "master".equals(this.getPycc());
    }

    public boolean isOther() {
        return "other".equals(this.getIdentity());
    }

    public boolean isParent() {
        return "parent".equals(this.getIdentity()) || "father".equals(this.getIdentity()) || "mather".equals(this.getIdentity());
    }

    public boolean isParentActive() {
        final int parentActive = this.parentActive;
        boolean b = true;
        if (parentActive != 1) {
            b = false;
        }
        return b;
    }

    public boolean isSelf() {
        return true;
    }

    public boolean isStuParent() {
        return "parent".equals(this.stu_identity);
    }

    public boolean isStudent() {
        return "student".equals(this.getIdentity());
    }

    public boolean isTeacher() {
        return "teacher".equals(this.getIdentity());
    }

    public boolean isVisitor() {
        return "visitor".equals(this.getIdentity());
    }

    public void setCard_number(final String card_number) {
        this.card_number = card_number;
    }

    public void setCelphone(final String celphone) {
        this.celphone = celphone;
    }

    public void setCourse_name(final String course_name) {
        this.course_name = course_name;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setHas_parent(final boolean has_parent) {
        this.has_parent = has_parent;
    }

    public void setHead(final String head) {
        this.head = head;
    }

    public void setId_photo(final String id_photo) {
        this.id_photo = id_photo;
    }

    public void setIdentity(final String identity) {
        this.identity = identity;
    }

    public void setJid(final String jid) {
        this.jid = jid;
    }

    public void setLandline(final String landline) {
        this.landline = landline;
    }

    public void setMood_words(final String mood_words) {
        this.mood_words = mood_words;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setNick_name(final String nick_name) {
        this.nick_name = nick_name;
    }

    public void setOfficephone(final String officephone) {
        this.officephone = officephone;
    }

    public void setOrg(final List<String> org) {
        this.org = org;
    }

    public void setOrgPath(final List<String> orgPath) {
        this.orgPath = orgPath;
    }

    public void setParentActive(final int parentActive) {
        this.parentActive = parentActive;
    }

    public void setParentActiveTime(final long parentActiveTime) {
        this.parentActiveTime = parentActiveTime;
    }

    public void setParentBind(final String parentBind) {
        this.parentBind = parentBind;
    }

    public void setPinyin(final String pinyin) {
        this.pinyin = pinyin;
    }

    public void setPycc(final String pycc) {
        this.pycc = pycc;
    }

    public void setQqcode(final String qqcode) {
        this.qqcode = qqcode;
    }

    public void setReceipt_opt_info(final String receipt_opt_info) {
        this.receipt_opt_info = receipt_opt_info;
    }

    public void setReceiverType(final int receiverType) {
        this.receiverType = receiverType;
    }

    public void setSex(final String sex) {
        this.sex = sex;
    }

    public void setShort_url(final String short_url) {
        this.short_url = short_url;
    }

    public void setSkey(final String skey) {
        this.skey = skey;
    }

    public void setStu_identity(final String stu_identity) {
        this.stu_identity = stu_identity;
    }

    public void setStudent_number(final String student_number) {
        this.student_number = student_number;
    }

    public void setTitle(final String tittle) {
        this.tittle = tittle;
    }

    public void setUser_id(final String user_id) {
        this.user_id = user_id;
    }

    public void setVerify(final String verify) {
        this.verify = verify;
    }

    public void setVerify_phone(final String verify_phone) {
        this.verify_phone = verify_phone;
    }

    public void setWechatcode(final String wechatcode) {
        this.wechatcode = wechatcode;
    }
}

