package com.example.fileopera.util;

/**
 * 构建线程安全类存储Page信息
 */
public class PageHolder {

    private static final ThreadLocal<PageSearch> HOLDER = new ThreadLocal<>();

    public static PageSearch getHolder() {
        return HOLDER.get();
    }

    public static void setHolder(PageSearch ps) {
        HOLDER.set(ps);
    }
}
