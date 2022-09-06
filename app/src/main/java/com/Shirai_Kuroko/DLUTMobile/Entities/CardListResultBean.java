package com.Shirai_Kuroko.DLUTMobile.Entities;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName CardListResultBean.java
 * @Description TODO
 * @createTime 2022年09月06日 09:27
 */

@Data
@NoArgsConstructor
public class CardListResultBean
{
    private List<AppBean> list_data;
    private int total;

    public List<AppBean> getList_data() {
        return this.list_data;
    }

    public int getTotal() {
        return this.total;
    }

    public void setList_data(final List<AppBean> list_data) {
        this.list_data = list_data;
    }

    public void setTotal(final int total) {
        this.total = total;
    }
}

