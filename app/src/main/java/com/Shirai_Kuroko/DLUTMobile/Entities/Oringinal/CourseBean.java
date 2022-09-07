package com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import lombok.Data;

@Data
public class CourseBean implements Serializable, Cloneable
{
    public static final int TYPE_COURSE_ITEM = 0;
    public static final int TYPE_DATE_TITLE = 1;
    public static final int TYPE_EMPTY_ITEM = 2;
    public static final int TYPE_PLACEHOLDER_ITEM = 3;
    private static List<CourseBean> localList;
    @JsonProperty("typeid")
    private Integer typeid;
    @JsonProperty("lesson_times")
    private Integer lesson_times;
    @JsonProperty("succession")
    private Integer succession;
    @JsonProperty("week_times")
    private String week_times;
    @JsonProperty("event_begintime")
    private String event_begintime;
    @JsonProperty("event_endtime")
    private String event_endtime;
    @JsonProperty("course_name")
    private String course_name;
    @JsonProperty("course_address")
    private String course_address;

    private int itemType;

    static {
        CourseBean.localList = new ArrayList<>();
    }



    public CourseBean() {
        super();
    }

    private static CourseBean getDateTitleBean() {
        final CourseBean courseBean = new CourseBean();
        courseBean.setItemType(1);
        return courseBean;
    }

    public enum CourseEmptyWords
    {
        DATA_ERR("数据解析错误"),
        DATA_LOADING("数据加载中"),
        DATA_NO("暂无数据"),
        NOW_NO("此时段无课"),
        SET_NO("暂未配置课程表"),
        TODAY_NO("今日无课"),
        USER_FORBIDDEN("暂无课表");

        public String txt;

        private CourseEmptyWords(final String txt) {
            this.txt = txt;
        }
    }

    private static CourseBean getEmptyBean(final CourseEmptyWords courseEmptyWords) {
        final CourseBean courseBean = new CourseBean();
        courseBean.setItemType(2);
        courseBean.setCourseName(courseEmptyWords.txt);
        return courseBean;
    }

    public static List<CourseBean> getLocalList() {
        return CourseBean.localList;
    }

    private static boolean hasLessonAfterNoon(final List<CourseBean> list) {
        final boolean b = false;
        boolean b2;
        try {
            final Iterator<CourseBean> iterator = list.iterator();
            do {
                b2 = b;
                if (iterator.hasNext()) {
                    continue;
                }
                return b2;
            } while (iterator.next().getLessonTimes() <= 4);
            b2 = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            b2 = b;
        }
        return b2;
    }

    public static void updateLocalList(final List<CourseBean> list) {
        CourseBean.localList.clear();
        CourseBean.localList.addAll(list);
        final List<CourseBean> localList = CourseBean.localList;
        final CourseBean dateTitleBean = getDateTitleBean();
        boolean b = false;
        localList.add(0, dateTitleBean);
        if (v0(list)) {
            CourseBean.localList.add(getEmptyBean(CourseEmptyWords.TODAY_NO));
        }
        else if (!hasLessonAfterNoon(list)) {
            if (new GregorianCalendar().get(9) == Calendar.PM) {
                b = true;
            }
            if (b) {
                CourseBean.localList.clear();
                CourseBean.localList.add(getEmptyBean(CourseEmptyWords.NOW_NO));
            }
        }
    }

    public static <V> boolean v0(final List<V> list) {
        return list == null || list.size() == 0;
    }

    @NonNull
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getBeginTime() {
        String s;
        if (TextUtils.isEmpty((CharSequence)this.event_begintime)) {
            s = "";
        }
        else if (this.event_begintime.length() > 5) {
            s = this.event_begintime.substring(0, 5);
        }
        else {
            s = this.event_begintime;
        }
        return s;
    }

    public String getClassRoom() {
        return this.course_address;
    }

    public String getCourseName() {
        return this.course_name;
    }

    public String getEndTime() {
        String s;
        if (TextUtils.isEmpty((CharSequence)this.event_endtime)) {
            s = "";
        }
        else if (this.event_endtime.length() > 5) {
            s = this.event_endtime.substring(0, 5);
        }
        else {
            s = this.event_endtime;
        }
        return s;
    }

    public int getItemType() {
        return this.itemType;
    }

    public String getLessonSuccession() {
        final int itemType = this.itemType;
        if (itemType == 3 || itemType == 2) {
            return "";
        }
        final int lessonTimes = this.lesson_times;
        if (lessonTimes < 1) {
            return "早晨";
        }
        if (lessonTimes > 11) {
            return "睡前";
        }
        final int i = this.succession + lessonTimes - 1;
        String s;
        if (lessonTimes == i) {
            s = U("第", lessonTimes, "节");
        }
        else {
            s = "第" +
                    lessonTimes +
                    "-" +
                    i +
                    "节";
        }
        return s;
    }

    public static String U(final String str, final int i, final String str2) {
        return str +
                i +
                str2;
    }

    public int getLessonTimes() {
        return this.lesson_times;
    }

    public int getSuccession() {
        return this.succession;
    }

    public int getTypeId() {
        return this.typeid;
    }

    public String getWeekTimes() {
        return this.week_times;
    }

    public void setClassRoom(final String classRoom) {
        this.course_address = classRoom;
    }

    public void setCourseName(final String courseName) {
        this.course_name = courseName;
    }

    public void setItemType(final int itemType) {
        this.itemType = itemType;
    }
}
