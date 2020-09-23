package com.java.bugs.test;

import com.java.bugs.fileUtil.DownLoad;
import com.java.bugs.webUtil.WebInit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class Test_getAllImg {
    @Autowired
    private DownLoad downLoad;
    @Autowired
    private WebInit webInit;
    // 获取的目标链接集合
    private List<String> list;

    @Before
    public void getAllUrl() {
        if (list == null) {
            webInit.setUrl("ftp://10.3.3.3/%E4%BA%94%E6%9C%9F%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/");
            webInit.setFilter(new String[]{"a[href$='.mkv']"});
            list = webInit.getAllUrl();
        }
    }

    @Test
    public void store() {
        System.out.println(list);
        downLoad.setUrlList(list);
        downLoad.store("/home/mrwang/Downloads/temp");
    }
}
