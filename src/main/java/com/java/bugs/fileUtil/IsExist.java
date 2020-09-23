package com.java.bugs.fileUtil;

import java.util.ArrayList;

/**
 * 判断文件名集合中是否有指定文件的工具类
 */
public final class IsExist {

    public static boolean isExist(ArrayList<String> fileNames, String target) {
        return fileNames.contains(target);
    }
}
