package com.Shirai_Kuroko.DLUTMobile.Entities;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName TableCardRowBean.java
 * @Description TODO
 * @createTime 2022年09月06日 08:18
 */

@Data
@NoArgsConstructor
public class TableCardRowBean
{
    private int columns;
    private List<TableCellBean> data;

    public int getColumns() {
        return this.columns;
    }

    public List<TableCellBean> getData() {
        return this.data;
    }

    public void setColumns(final int columns) {
        this.columns = columns;
    }

    public void setData(final List<TableCellBean> data) {
        this.data = data;
    }

    @Data
    @NoArgsConstructor
    public class TableCellBean
    {
        private String color;
        private int span;
        private String text;
        public String getColor() {
            return this.color;
        }

        public int getSpan() {
            return this.span;
        }

        public String getText() {
            return this.text;
        }

        public void setColor(final String color) {
            this.color = color;
        }

        public void setSpan(final int span) {
            this.span = span;
        }

        public void setText(final String text) {
            this.text = text;
        }
    }
}
