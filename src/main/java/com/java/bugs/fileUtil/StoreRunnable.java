package com.java.bugs.fileUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 执行下载操作
 */
public class StoreRunnable implements Runnable {
    // 下载目标文件
    private File targetFile;
    // 下载文件的URL
    private String url;

    public StoreRunnable(File targetFile, String url) {
        this.targetFile = targetFile;
        this.url = url;
    }

    public File getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(File targetFile) {
        this.targetFile = targetFile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        URL l = null;
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream inputStream = null;
        try (BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(targetFile))) {
            System.out.println("开始下载： " + url);
            l = new URL(url);
            httpURLConnection = (HttpURLConnection) l.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.connect();
            // 获取字节流
            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            byte[] bytes = new byte[1024 * 10];
            int i = 0;
            while ((i = inputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, i);
                fos.flush();
            }
            System.out.println("存储完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
