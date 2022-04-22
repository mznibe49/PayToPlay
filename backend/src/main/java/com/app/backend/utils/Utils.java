package com.app.backend.utils;

import javax.servlet.http.HttpServletRequest;

public class Utils {

    public static String getSiteUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.replace(request.getServletPath(), "");
    }
}
