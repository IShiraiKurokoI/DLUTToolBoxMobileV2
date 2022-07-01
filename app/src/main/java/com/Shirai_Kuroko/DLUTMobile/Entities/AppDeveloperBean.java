package com.Shirai_Kuroko.DLUTMobile.Entities;

import java.io.*;

public class AppDeveloperBean implements Serializable
{
    private String jid;
    private String name;
    private String organization;

    public String getJid() {
        return this.jid;
    }

    public String getName() {
        return this.name;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setJid(final String jid) {
        this.jid = jid;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOrganization(final String organization) {
        this.organization = organization;
    }
}

