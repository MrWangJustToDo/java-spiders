package com.java.bugs.webUtil;

import org.openqa.selenium.JavascriptExecutor;
import org.springframework.stereotype.Component;

/**
 * JS代码执行工具类
 */
@Component("executor")
public class JSExecutor {
    // JS执行对象
    private JavascriptExecutor executor;

    public JavascriptExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(JavascriptExecutor executor) {
        this.executor = executor;
    }

    // 执行JS,获取指定数据
    public Object getTargetHeight(String js) {
        return executor.executeScript(js);
    }

    // 执行滚动
    public void scroll(long currentHeight, long scrollHeight) {
        executor.executeScript("window.scrollTo({top: " + (currentHeight + scrollHeight) + ", behavior: 'smooth'})");
    }
}
