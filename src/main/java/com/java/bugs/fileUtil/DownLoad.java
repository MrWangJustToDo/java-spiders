package com.java.bugs.fileUtil;


import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 下载传入的URL地址
 */
@Component("download")
public class DownLoad {
    // 存储所有URL
    private List<String> urlList;

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    @Resource(name = "getName")
    private SetFileName setFileName;
    @Resource(name = "executorService")
    private ExecutorService executorService;

    public void store(String parentDir) {
        File dir = new File(parentDir);
        if (dir.exists() && dir.isFile()) {
            throw new RuntimeException("存储文件夹设置错误");
        }
        // 遍历集合,使用线程池进行下载
        for (String url : urlList) {
            setFileName.setUrl(url);
            String name = setFileName.getFileName();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.submit(new StoreRunnable(new File(dir, name), url));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
