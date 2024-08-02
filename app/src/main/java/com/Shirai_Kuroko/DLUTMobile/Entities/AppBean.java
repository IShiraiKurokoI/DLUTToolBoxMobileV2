package com.Shirai_Kuroko.DLUTMobile.Entities;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName AppBean.java
 * @Description TODO
 * @createTime 2022年09月06日 07:46
 */
public class AppBean implements Serializable
{
    public static final int APP_ALREADY_SUBSCRIPTION = 1;
    public static final int APP_FORCE_SUBSCRIPTION = 2;
    public static final int APP_NOT_SUBSCRIPTION = 0;
    public static final String APP_TYPE_APP_CENTER = "app_center";
    public static final String APP_TYPE_LIGHTAPP = "lightapp";
    public static final String APP_TYPE_NATIVE = "native";
    public static final String APP_TYPE_PROXY = "proxy";
    public static final String APP_TYPE_WEBSSO = "websso";
    public static final String KEY_APP_ID = "app_id";
    public static final String KEY_APP_NAME = "app_name";
    public static final String KEY_ICON = "icon";
    private static final AppBean appCenter;
    public static int iconRes = 0;
    private static final long serialVersionUID = 1L;
    private long add_time;
    private String android_pakage_name;
    private String android_pakage_url;
    public Bitmap appCenterBmp;
    private String app_id;
    private String app_name;
    private String card_url;
    private String category;
    private long cs_mtime;
    private String describe;
    private String developer_id;
    private String icon;
    private int isCelRe;
    private boolean isExpand;
    private boolean isNew;
    private int isNewApp;
    private int is_add_card;
    private int is_school_official;
    private int is_sso;
    private int iscollection;
    public int issubscription;
    private int issupport;
    private int iswhistle;
    private int modify_time;
    private String open_with;
    private String platform;
    private String popularity;
    private int recommend;
    private String recommend_icon;
    public List<String> screenshot;
    private int selectTab;
    private int sort;
    private String status;
    private String type;
    private String url;
    @SerializedName("program_id")
    private String wxProgramId;
    @SerializedName("program_type")
    private int wxProgramType;
    @SerializedName("program_url")
    private String wxProgramUrl;

    static {
        appCenter = new AppBean();
        AppBean.iconRes = R.drawable.app_center_normal;
    }

    public AppBean() {
        super();
        this.appCenterBmp = null;
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
        final AppBean appBean = (AppBean)o;
        if (this.iswhistle != appBean.iswhistle) {
            return false;
        }
        if (this.recommend != appBean.recommend) {
            return false;
        }
        if (this.is_sso != appBean.is_sso) {
            return false;
        }
        if (this.iscollection != appBean.iscollection) {
            return false;
        }
        if (this.modify_time != appBean.modify_time) {
            return false;
        }
        if (this.issupport != appBean.issupport) {
            return false;
        }
        if (this.is_school_official != appBean.is_school_official) {
            return false;
        }
        if (this.issubscription != appBean.issubscription) {
            return false;
        }
        final String app_name = this.app_name;
        Label_0168: {
            if (app_name != null) {
                if (app_name.equals(appBean.app_name)) {
                    break Label_0168;
                }
            }
            else if (appBean.app_name == null) {
                break Label_0168;
            }
            return false;
        }
        final String app_id = this.app_id;
        Label_0200: {
            if (app_id != null) {
                if (app_id.equals(appBean.app_id)) {
                    break Label_0200;
                }
            }
            else if (appBean.app_id == null) {
                break Label_0200;
            }
            return false;
        }
        final String url = this.url;
        Label_0232: {
            if (url != null) {
                if (url.equals(appBean.url)) {
                    break Label_0232;
                }
            }
            else if (appBean.url == null) {
                break Label_0232;
            }
            return false;
        }
        final String type = this.type;
        Label_0264: {
            if (type != null) {
                if (type.equals(appBean.type)) {
                    break Label_0264;
                }
            }
            else if (appBean.type == null) {
                break Label_0264;
            }
            return false;
        }
        final String category = this.category;
        Label_0296: {
            if (category != null) {
                if (category.equals(appBean.category)) {
                    break Label_0296;
                }
            }
            else if (appBean.category == null) {
                break Label_0296;
            }
            return false;
        }
        final String platform = this.platform;
        Label_0328: {
            if (platform != null) {
                if (platform.equals(appBean.platform)) {
                    break Label_0328;
                }
            }
            else if (appBean.platform == null) {
                break Label_0328;
            }
            return false;
        }
        final String recommend_icon = this.recommend_icon;
        Label_0360: {
            if (recommend_icon != null) {
                if (recommend_icon.equals(appBean.recommend_icon)) {
                    break Label_0360;
                }
            }
            else if (appBean.recommend_icon == null) {
                break Label_0360;
            }
            return false;
        }
        final String status = this.status;
        Label_0392: {
            if (status != null) {
                if (status.equals(appBean.status)) {
                    break Label_0392;
                }
            }
            else if (appBean.status == null) {
                break Label_0392;
            }
            return false;
        }
        final String developer_id = this.developer_id;
        Label_0424: {
            if (developer_id != null) {
                if (developer_id.equals(appBean.developer_id)) {
                    break Label_0424;
                }
            }
            else if (appBean.developer_id == null) {
                break Label_0424;
            }
            return false;
        }
        final String describe = this.describe;
        Label_0456: {
            if (describe != null) {
                if (describe.equals(appBean.describe)) {
                    break Label_0456;
                }
            }
            else if (appBean.describe == null) {
                break Label_0456;
            }
            return false;
        }
        final String popularity = this.popularity;
        Label_0488: {
            if (popularity != null) {
                if (popularity.equals(appBean.popularity)) {
                    break Label_0488;
                }
            }
            else if (appBean.popularity == null) {
                break Label_0488;
            }
            return false;
        }
        final String icon = this.icon;
        Label_0520: {
            if (icon != null) {
                if (icon.equals(appBean.icon)) {
                    break Label_0520;
                }
            }
            else if (appBean.icon == null) {
                break Label_0520;
            }
            return false;
        }
        final String open_with = this.open_with;
        final String open_with2 = appBean.open_with;
        if (open_with != null) {
            if (open_with.equals(open_with2)) {
                return b;
            }
        }
        else if (open_with2 == null) {
            return b;
        }
        b = false;
        return b;
    }

    public long getAdd_time() {
        return this.add_time;
    }

    public String getAndroid_pakage_name() {
        return this.android_pakage_name;
    }

    public String getAndroid_pakage_url() {
        return this.android_pakage_url;
    }

    public String getAppPopularity() {
        String popularity = this.getPopularity();
        int int1;
        try {
            int1 = Integer.parseInt(popularity);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            int1 = 0;
        }
        if (int1 > 99999) {
            popularity = "99999+";
        }
        return popularity;
    }

    public String getApp_id() {
        return this.app_id;
    }

    public String getApp_name() {
        return this.app_name;
    }

    public String getCard_url() {
        return this.card_url;
    }

    public String getCategory() {
        return this.category;
    }

    public long getCs_mtime() {
        return this.cs_mtime;
    }


    public String getDescribe() {
        return this.describe;
    }

    public String getDeveloper_id() {
        return this.developer_id;
    }

    public String getIcon() {
        return this.icon;
    }

    public boolean getIsCanelRecomend() {
        final int isCelRe = this.isCelRe;
        return isCelRe == 1;
    }

    public int getIsCelRe() {
        return this.isCelRe;
    }

    public int getIsNewApi() {
        return this.isNewApp;
    }

    public int getIs_school_official() {
        return this.is_school_official;
    }

    public boolean getIs_sso() {
        final int is_sso = this.is_sso;
        return is_sso == 1;
    }

    public int getIscollection() {
        return this.iscollection;
    }

    public int getIssubscription() {
        return this.issubscription;
    }

    public int getIssupport() {
        return this.issupport;
    }

    public int getIswhistle() {
        return this.iswhistle;
    }

    public int getModify_time() {
        return this.modify_time;
    }

    public String getOpen_with() {
        return this.open_with;
    }

    public String getPlatform() {
        return this.platform;
    }

    public String getPopularity() {
        return this.popularity;
    }

    public String getRecommend_icon() {
        return this.recommend_icon;
    }

    public List<String> getScreenshot() {
        final ArrayList<String> list = new ArrayList<String>();
        for (final String e : this.screenshot) {
            if (!TextUtils.isEmpty(e)) {
                list.add(e);
            }
        }
        return list;
    }

    public int getSelectTab() {
        return this.selectTab;
    }

    public int getSort() {
        return this.sort;
    }

    public String getStatus() {
        return this.status;
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public String getWxProgramId() {
        return this.wxProgramId;
    }

    public int getWxProgramType() {
        return this.wxProgramType;
    }

    public String getWxProgramUrl() {
        return this.wxProgramUrl;
    }

    @Override
    public int hashCode() {
        final String app_name = this.app_name;
        int hashCode = 0;
        int hashCode2;
        if (app_name != null) {
            hashCode2 = app_name.hashCode();
        }
        else {
            hashCode2 = 0;
        }
        final String app_id = this.app_id;
        int hashCode3;
        if (app_id != null) {
            hashCode3 = app_id.hashCode();
        }
        else {
            hashCode3 = 0;
        }
        final String url = this.url;
        int hashCode4;
        if (url != null) {
            hashCode4 = url.hashCode();
        }
        else {
            hashCode4 = 0;
        }
        final String type = this.type;
        int hashCode5;
        if (type != null) {
            hashCode5 = type.hashCode();
        }
        else {
            hashCode5 = 0;
        }
        final String category = this.category;
        int hashCode6;
        if (category != null) {
            hashCode6 = category.hashCode();
        }
        else {
            hashCode6 = 0;
        }
        final String platform = this.platform;
        int hashCode7;
        if (platform != null) {
            hashCode7 = platform.hashCode();
        }
        else {
            hashCode7 = 0;
        }
        final int iswhistle = this.iswhistle;
        final String recommend_icon = this.recommend_icon;
        int hashCode8;
        if (recommend_icon != null) {
            hashCode8 = recommend_icon.hashCode();
        }
        else {
            hashCode8 = 0;
        }
        final String status = this.status;
        int hashCode9;
        if (status != null) {
            hashCode9 = status.hashCode();
        }
        else {
            hashCode9 = 0;
        }
        final int recommend = this.recommend;
        final int is_sso = this.is_sso;
        final String developer_id = this.developer_id;
        int hashCode10;
        if (developer_id != null) {
            hashCode10 = developer_id.hashCode();
        }
        else {
            hashCode10 = 0;
        }
        final String describe = this.describe;
        int hashCode11;
        if (describe != null) {
            hashCode11 = describe.hashCode();
        }
        else {
            hashCode11 = 0;
        }
        final String popularity = this.popularity;
        int hashCode12;
        if (popularity != null) {
            hashCode12 = popularity.hashCode();
        }
        else {
            hashCode12 = 0;
        }
        final int iscollection = this.iscollection;
        final String icon = this.icon;
        int hashCode13;
        if (icon != null) {
            hashCode13 = icon.hashCode();
        }
        else {
            hashCode13 = 0;
        }
        final String open_with = this.open_with;
        if (open_with != null) {
            hashCode = open_with.hashCode();
        }
        return (((((((((((((((((((hashCode2 * 31 + hashCode3) * 31 + hashCode4) * 31 + hashCode5) * 31 + hashCode6) * 31 + hashCode7) * 31 + iswhistle) * 31 + hashCode8) * 31 + hashCode9) * 31 + recommend) * 31 + is_sso) * 31 + hashCode10) * 31 + hashCode11) * 31 + hashCode12) * 31 + iscollection) * 31 + hashCode13) * 31 + hashCode) * 31 + this.modify_time) * 31 + this.issupport) * 31 + this.is_school_official) * 31 + this.issubscription;
    }

    public boolean isAddCard() {
        final int is_add_card = this.is_add_card;
        return is_add_card == 1;
    }

    public boolean isCollection() {
        final int iscollection = this.iscollection;
        return iscollection == 1;
    }

    public boolean isExpand() {
        return this.isExpand;
    }

    public boolean isNew() {
        return this.isNew;
    }

    public boolean isNewApp() {
        final int isNewApp = this.isNewApp;
        return isNewApp == 1;
    }

    public boolean isRecommend() {
        return "recommend".equals(this.status);
    }

    public boolean isSubscribe() {
        final int issubscription = this.issubscription;
        return issubscription == 1;
    }

    public void setAndroid_pakage_name(final String android_pakage_name) {
        this.android_pakage_name = android_pakage_name;
    }

    public void setAndroid_pakage_url(final String android_pakage_url) {
        this.android_pakage_url = android_pakage_url;
    }

    public void setApp_id(final String app_id) {
        this.app_id = app_id;
    }

    public void setApp_name(final String app_name) {
        this.app_name = app_name;
    }

    public void setCard_url(final String card_url) {
        this.card_url = card_url;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public void setCs_mtime(final long cs_mtime) {
        this.cs_mtime = cs_mtime;
    }

    public void setDescribe(final String describe) {
        this.describe = describe;
    }

    public void setDeveloper_id(final String developer_id) {
        this.developer_id = developer_id;
    }

    public void setExpand(final boolean isExpand) {
        this.isExpand = isExpand;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public void setIsAddCard(final boolean is_add_card) {
        this.is_add_card = (is_add_card ? 1 : 0);
    }

    public void setIsCelRe(final int isCelRe) {
        this.isCelRe = isCelRe;
    }

    public void setIsNew(final boolean isNew) {
        this.isNew = isNew;
    }

    public void setIsRecommend(final boolean b) {
        this.isCelRe = ((!b) ? 1 : 0);
    }

    public void setIs_school_official(final int is_school_official) {
        this.is_school_official = is_school_official;
    }

    public void setIs_sso(final int is_sso) {
        this.is_sso = is_sso;
    }

    public void setIscollection(final int iscollection) {
        this.iscollection = iscollection;
    }

    public void setIscollection(final boolean iscollection) {
        this.iscollection = (iscollection ? 1 : 0);
    }

    public void setIssubscription(final int issubscription) {
        this.issubscription = issubscription;
    }

    public void setIssubscription(final Boolean b) {
        this.issubscription = (b ? 1 : 0);
    }

    public void setIssupport(final int issupport) {
        this.issupport = issupport;
    }

    public void setIswhistle(final int iswhistle) {
        this.iswhistle = iswhistle;
    }

    public void setModify_time(final int modify_time) {
        this.modify_time = modify_time;
    }

    public void setNew(final boolean isNew) {
        this.isNew = isNew;
    }

    public void setNewApp(final int isNewApp) {
        this.isNewApp = isNewApp;
    }

    public void setOpen_with(final String open_with) {
        this.open_with = open_with;
    }

    public void setPlatform(final String platform) {
        this.platform = platform;
    }

    public void setPopularity(final String popularity) {
        this.popularity = popularity;
    }

    public void setRecommend(final int recommend) {
        this.recommend = recommend;
    }

    public void setRecommend_icon(final String recommend_icon) {
        this.recommend_icon = recommend_icon;
    }

    public void setSelectTab(final int selectTab) {
        this.selectTab = selectTab;
    }

    public void setSort(final int sort) {
        this.sort = sort;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setWxProgramId(final String wxProgramId) {
        this.wxProgramId = wxProgramId;
    }

    public void setWxProgramType(final int wxProgramType) {
        this.wxProgramType = wxProgramType;
    }

    public void setWxProgramUrl(final String wxProgramUrl) {
        this.wxProgramUrl = wxProgramUrl;
    }
}

