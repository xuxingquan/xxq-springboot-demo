package com.xxq;

import com.xxq.controller.request.BannerItem;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    @Test
    public void testList(){
        List<BannerItem> items = Lists.newArrayList();
        BannerItem item1 = new BannerItem();
        item1.setId("1212");
        item1.setImageUrl("erwerewr");
        item1.setJumpUrl("werwerer");

        BannerItem item2 = new BannerItem();
        item2.setId("2121");
        item2.setImageUrl("bfghnfgh");
        item2.setJumpUrl("jkluilk");

        items.add(item1);
        items.add(item2);
        System.out.println(items);

    }
}
