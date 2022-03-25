package com.jianmu.tools;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数生成工具
 *
 * @author kong
 */
public class RandomTools {

    private static final Random RANDOM = ThreadLocalRandom.current();

    /**
     * 获取替换了 中横线的 uuid  唯一随机字符
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static int randInt(int max) {
        Random rand = new Random();
        int randNum = rand.nextInt((max + 1));
        return randNum > 0 ? randNum : randInt(max);
    }

    /**
     * 随机生成范围随机数
     */
    public static Integer random(int start, int end) {
        int max;
        int min;
        if (start > end) {
            max = start;
            min = end;
        } else {
            max = end;
            min = start;

        }
        return RANDOM.nextInt(max) % (max - min + 1) + min;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(random(1, 2));
        }
    }
}