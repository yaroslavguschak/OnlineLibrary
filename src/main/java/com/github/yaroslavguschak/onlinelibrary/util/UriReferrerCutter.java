package com.github.yaroslavguschak.onlinelibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by yars on 16.05.2016.
 */
public class UriReferrerCutter {
    public static String cutReferre(String referrerURI)  {
        String redirectURI;
        if (referrerURI != null){
            int lastIndex =referrerURI.indexOf('/', referrerURI.indexOf("//") + 1) ;
            redirectURI = referrerURI.substring(lastIndex);
        } else {
            redirectURI = "index";
        }
        return redirectURI;
    }
}
