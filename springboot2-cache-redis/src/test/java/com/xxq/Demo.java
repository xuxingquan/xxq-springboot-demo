package com.xxq;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Demo {

    public static void main(String[] args) {
        for (int m = 1;m<50;m++){
            for (int n = 1;n<50;n++){
                if (m*n == 1997) System.out.println("m="+m+",n="+n);
            }
        }
    }

    @Test
    public void testTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MMddHHmmssSSS");
        String format = now.format(pattern);
        System.out.println(format);
    }
}
