package com.github.yaroslavguschak.onlinelibrary.entity;

public enum Permission {

    GUEST      (false, false, false),
    SUBSCRIBER (true,  false, false),
    ADMIN      (true,  true,  true );

    private boolean download = false;
    private boolean edit     = false;
    private boolean delete   = false;

    Permission(boolean download,boolean edit, boolean delete) {
        this.download = download;
        this.edit     = edit;
        this.delete   = delete;
    }

    public boolean isDownload(){
        return this.download;
    }

    public boolean isEdit() {
        return edit;
    }

    public boolean isDelete(){
        return this.delete;
    }
}
