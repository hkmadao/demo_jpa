package org.hkmadao.tcdt;

import java.util.Random;

public class RandomTest {
    public static void main(String[] args) {
        Random random = new Random(100);
        for (int i = 0; i < 20; i++) {
            System.out.println(random.nextInt(5));
        }
        Random random1 = new Random(100);
        for (int i = 0; i < 10; i++) {
            System.out.println("1======" + random1.nextInt());
        }
    }
}
