package com.ck19.infodroid;

import android.graphics.drawable.Drawable;

public class AppInfo {

    private String appLabel;    //应用程序标签
    private Drawable appIcon ;  //应用程序图像
    private String pkgName ;    //应用程序所对应的包名

    public AppInfo(){}

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appName) {
        this.appLabel = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPkgName(){
        return pkgName ;
    }

    public void setPkgName(String pkgName){
        this.pkgName=pkgName ;
    }
}
