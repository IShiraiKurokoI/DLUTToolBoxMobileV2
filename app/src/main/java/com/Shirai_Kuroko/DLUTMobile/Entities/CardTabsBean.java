package com.Shirai_Kuroko.DLUTMobile.Entities;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName CardTabsBean.java
 * @Description TODO
 * @createTime 2022年09月06日 07:27
 */


@Data
@NoArgsConstructor
public class CardTabsBean implements Serializable
{
    private List<TabBean> data;

    public List<TabBean> getData() {
        return this.data;
    }

    public boolean hasTabs() {
        final List<TabBean> data = this.data;
        return data != null && !data.isEmpty();
    }

    public void setData(final List<TabBean> data) {
        this.data = data;
    }

    public static class TabBean
    {
        private String name;

        public TabBean() {
            super();
        }

        public String getName() {
            return this.name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }
}

