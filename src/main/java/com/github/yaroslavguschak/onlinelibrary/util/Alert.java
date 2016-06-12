package com.github.yaroslavguschak.onlinelibrary.util;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)

public class Alert {
    private String message;
    private boolean isShow;

    public Alert() {
        message = "hi from alert";
        isShow = false;
    }

    public Alert(String message, boolean isShow) {
        this.message = message;
        this.isShow = isShow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
