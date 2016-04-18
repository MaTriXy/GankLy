package com.gank.gankly.config;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        String downloadDirectoryPath = Environment.getDownloadCacheDirectory().getAbsolutePath();
//        String diskCacheFolder = "/GankLy";
//        downloadDirectoryPath = downloadDirectoryPath + diskCacheFolder;
//        KLog.d("downloadDirectoryPath:" + downloadDirectoryPath);
//        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, 250 * 1024 * 1024));
        String diskCache = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/GankLy/cache/img";
        builder.setDiskCache(new DiskLruCacheFactory(diskCache, 150 * 1024 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
    }
}