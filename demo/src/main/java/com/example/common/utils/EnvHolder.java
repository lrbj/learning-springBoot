package com.example.common.utils;

import com.example.common.entity.Env;

public class EnvHolder {
    private static final ThreadLocal<Env> HOLDER = new ThreadLocal<>();

    public static Env getHolder() {
        return HOLDER.get() == null ? new Env() : HOLDER.get();
    }

    public static void setHolder(Env env) {
        HOLDER.set(env);
    }

    public static void remove() {
        HOLDER.remove();
    }
}
