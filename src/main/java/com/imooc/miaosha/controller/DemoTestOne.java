package com.imooc.miaosha.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * @author wt
 * @version 1.0
 * @date 2020/8/28 14:35
 */
public class DemoTestOne {
    public static void main(String[] args) {
        long id = IdWorker.getId();
        String uuid = IdWorker.get32UUID();

        System.out.println("测唯一:   " + id);
        System.out.println("测试二:   " + uuid);
    }
}
