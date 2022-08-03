package com.Shirai_Kuroko.DLUTMobile.Entities;

public class SearchBean {
    public boolean Is_Notification;
    public NotificationHistoryDataBaseBean notificationHistoryDataBaseBean;
    public ApplicationConfig applicationConfig;

    public SearchBean(boolean is_Notification, NotificationHistoryDataBaseBean notificationHistoryDataBaseBean, ApplicationConfig applicationConfig) {
        this.Is_Notification = is_Notification;
        this.notificationHistoryDataBaseBean = notificationHistoryDataBaseBean;
        this.applicationConfig = applicationConfig;
    }

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public NotificationHistoryDataBaseBean getNotificationHistoryDataBaseBean() {
        return notificationHistoryDataBaseBean;
    }

    public Boolean getisnotification() {
        return Is_Notification;
    }


}
