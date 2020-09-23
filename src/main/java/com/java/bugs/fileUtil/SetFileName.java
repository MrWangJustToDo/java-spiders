package com.java.bugs.fileUtil;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.ArrayList;

/**
 * 获取文件名称的工具类
 */
@Component("getName")
public class SetFileName {
    private String Url;
    @Resource(name = "dateFormat")
    private DateFormat df;

    // 存储文件名称的集合
    private final ArrayList<String> fileNames = new ArrayList<>();


    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    // 根据URL字符串获取保存文件的后缀名
    private String getLastName() {
        return Url.substring(Url.lastIndexOf("."));
    }

    // 返回自动创建的文件名
    public String getFileName() {
        String name = System.currentTimeMillis() + getLastName();
        while (IsExist.isExist(fileNames, name)) {
            name = System.currentTimeMillis() + getLastName();
        }
        fileNames.add(name);
        return name;
    }
}
