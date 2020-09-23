package com.java.bugs.webUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 浏览器的初始化
 */
@Component("init")
public class WebInit {
    // 需要打开的URL
    private String url;
    // 需要选中下载的资源
    private String[] filter;
    // 获取的目标连接集合
    private List<WebElement> elements = new LinkedList<>();
    // 获取的目标URL集合
    private List<String> re;
    // JS执行工具
    @Resource(name = "executor")
    private JSExecutor executor;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getFilter() {
        return filter;
    }

    public void setFilter(String[] filter) {
        this.filter = filter;
    }

    // 初始化浏览器
    private void init() {
        System.setProperty("webdriver.chrome.driver", this.getClass().getResource("/chromedriver").getPath());
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        // 与浏览器同步非常重要，必须等待浏览器加载完毕
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // 打开目标地址
        webDriver.get(url);
        // 初始化JS执行工具
        executor.setExecutor((JavascriptExecutor) webDriver);
        try {
            Thread.sleep(3000);
            // 获取页面内容总高度
            long height = (long) executor.getTargetHeight("return parseInt(document.body.offsetHeight)");
            // 获取窗体内容的高度
            long windowHeight = (long) executor.getTargetHeight("return parseInt(window.innerHeight)");
            // 每一次滚动长度
            long h = height / 6;
            // 初始滚动位置
            long start = 0;
            while (((start + windowHeight) + 200) < height) {
                Thread.sleep(200);
                executor.scroll(start, h);
                start += h;
                Thread.sleep(600);
            }
            Thread.sleep(1000);
            Arrays.stream(filter).forEach(str -> {
                elements.addAll(webDriver.findElements(By.cssSelector(str)));
            });
            // 获取所有目标链接URL
            re = elements.stream().map(webElement -> webElement.getAttribute("href")).collect(Collectors.toList());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //暂停2秒钟后关闭
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            webDriver.quit();
        }
    }

    // 返回获取的所有URL
    public List<String> getAllUrl() {
        if (re == null) {
            if (url == null) {
                throw new RuntimeException("传入网址为空");
            }
            init();
        }
        return re;
    }
}
