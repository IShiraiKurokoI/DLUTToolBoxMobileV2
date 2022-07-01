package com.Shirai_Kuroko.DLUTMobile.Entities;

import java.io.*;

public class AppCustomInfoBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    public String identifier;
    public String package_name;
    public int packagesize;
    public String version;

    public String getIdentifier() {
        return this.identifier;
    }

    public String getPackage_name() {
        return this.package_name;
    }

    public int getPackagesize() {
        return this.packagesize;
    }

    public String getVersion() {
        return this.version;
    }

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    public void setPackage_name(final String package_name) {
        this.package_name = package_name;
    }

    public void setPackagesize(final int packagesize) {
        this.packagesize = packagesize;
    }

    public void setVersion(final String version) {
        this.version = version;
    }
}
