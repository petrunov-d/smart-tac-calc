package com.dp.trains.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class CacheHolder {

    private static LoadingCache<String, String> LoadingCache;

    private CacheHolder() {
    }

    public static LoadingCache<String, String> getLoadingCache() {

        if (LoadingCache == null) {

            synchronized (EventBusHolder.class) {

                if (LoadingCache == null) {

                    LoadingCache = CacheBuilder.newBuilder()
                            .expireAfterWrite(1500, TimeUnit.SECONDS)
                            .build(new CacheLoader<String, String>() {
                                @Override
                                public String load(String key) {
                                    return key.toUpperCase();
                                }
                            });
                }
            }
        }

        return LoadingCache;
    }
}
