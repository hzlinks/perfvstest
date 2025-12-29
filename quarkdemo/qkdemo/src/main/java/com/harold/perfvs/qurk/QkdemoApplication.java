package com.harold.perfvs.qurk;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;

public class QkdemoApplication implements QuarkusApplication {
    @Override
    public int run(String... args) throws Exception {
        System.out.println("App started...");
        Quarkus.waitForExit();
        return 0;
    }

    public static void main(String[] args) {
        Quarkus.run(QkdemoApplication.class, args);
    }

}
