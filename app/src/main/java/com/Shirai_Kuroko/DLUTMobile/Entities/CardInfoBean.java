package com.Shirai_Kuroko.DLUTMobile.Entities;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName CardInfoBean.java
 * @Description TODO
 * @createTime 2022年09月06日 07:25
 */

@Data
@NoArgsConstructor
public class CardInfoBean
{
    private List<JSONObject> data;
    private ConfigBean global;
    private InfoBean meta;
    private CardTabsBean tabs;

    public List<JSONObject> getData() {
        return this.data;
    }

    public <T> List<T> getData(final Class<T> clazz) {
        final ArrayList<T> list = new ArrayList<>();
        final List<JSONObject> data = this.data;
        if (data != null) {
            if (data.size() != 0) {
                for (final JSONObject jsonObject : this.data) {
                    try {
                        list.add(JSON.parseObject(jsonObject.toJSONString(),clazz));
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return list;
    }

    public <T> List<T> getData(final Class<T> clazz,boolean quchong) {
        final ArrayList<T> list = new ArrayList<>();
        final List<JSONObject> data = this.data;
        if (data != null) {
            if (data.size() != 0) {
                for (final JSONObject jsonObject : this.data) {
                    try {
                        list.add(JSON.parseObject(jsonObject.toJSONString(),clazz));
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return duplicateRemovalByCircle(list);
    }



    public <T> List<T> duplicateRemovalByCircle(List<T> list){
        List<T> newList = new ArrayList<>();
        for (int i = 0;i < list.size();i++){
            for (int j = 0;j < i;j++){
                if(list.get(i).equals(list.get(j))){
                    newList.remove(list.get(j));
                }
            }
            newList.add(list.get(i));
        }
        return newList;
    }

    public ConfigBean getGlobal() {
        return this.global;
    }

    public InfoBean getMeta() {
        return this.meta;
    }

    public CardTabsBean getTabs() {
        return this.tabs;
    }

    public void setData(final List<JSONObject> data) {
        this.data = data;
    }

    public void setMeta(final InfoBean meta) {
        this.meta = meta;
    }

    @Data
    @NoArgsConstructor
    public static class ActionBean
    {
        private String name;
        private String url;

        public String getName() {
            return this.name;
        }

        public String getUrl() {
            return this.url;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public void setUrl(final String url) {
            this.url = url;
        }
    }

    @Data
    @NoArgsConstructor
    public class ConfigBean implements Serializable
    {
        private ActionBean action;
        private ThemeBean theme;
        private int tips;

        public ActionBean getAction() {
            return this.action;
        }

        public ThemeBean getTheme() {
            return this.theme;
        }

        public int getTips() {
            return this.tips;
        }

        public void setTips(final int tips) {
            this.tips = tips;
        }
    }

    @Data
    @NoArgsConstructor
    public class InfoBean
    {
        private String icon;
        private String name;
        private int template;

        public String getIcon() {
            return this.icon;
        }

        public String getName() {
            return this.name;
        }

        public int getTemplate() {
            return this.template;
        }

        public void setIcon(final String icon) {
            this.icon = icon;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public void setTemplate(final int template) {
            this.template = template;
        }
    }

    @Data
    @NoArgsConstructor
    public static class ThemeBean
    {
        private String bgImage;
        private List<String> noDataText;

        public String getBgImage() {
            return this.bgImage;
        }

        public List<String> getNoDataText() {
            return this.noDataText;
        }

        public void setBgImage(final String bgImage) {
            this.bgImage = bgImage;
        }

        public void setNoDataText(final List<String> noDataText) {
            this.noDataText = noDataText;
        }

        @NonNull
        @Override
        public String toString() {
            return "ThemeBean{bgImage='" +
                    this.bgImage +
                    '\'' +
                    ", noDataText=" +
                    this.noDataText +
                    '}';
        }
    }
}
