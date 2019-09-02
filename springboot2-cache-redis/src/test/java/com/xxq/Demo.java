package com.xxq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
public class Demo {

    public static void main(String[] args) {
        for (int m = 1; m < 50; m++) {
            for (int n = 1; n < 50; n++) {
                if (m * n == 1997) System.out.println("m=" + m + ",n=" + n);
            }
        }
    }

    @Test
    public void testTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MMddHHmmssSSS");
        String format = now.format(pattern);
        System.out.println(format);
    }

    @Test
    public void test100100() {
        long chapterId = 4060368390l;
        System.out.println((chapterId / 100) % 100);
    }

    @Test
    public void test10010() {
        long id = 4060368390l;
        System.out.println((id / 100) % 10);
    }

    @Test
    public void test200() {
        long id = 4060368390l;
        System.out.println(id % 200);
    }

    @Test
    public void test100200() {
        long id = 4060368390l;
        System.out.println((id / 100) % 200);
    }

    @Test
    public void testCollector() {
        String[] arr = new String[]{};
        List<String> collect = Arrays.stream(arr).map(s -> s + "1").collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect));
    }

    @Test
    public void test2() {
        String str = "{\"70001\":\"11676462901017405\",\"70002\":\"11653617901014305\",\"70003\":\"11987368201024205\",\"70004\":\"11652753801014205\",\"70005\":\"12222676701029505\",\"70006\":\"12335339401035105\",\"70007\":\"12197488601029105\",\"70008\":\"11686410601018705\",\"70009\":\"10486246401027205\",\"700010\":\"7479922501012705\",\"700011\":\"13534274801047905\",\"700012\":\"10390463201026005\",\"700013\":\"10234821601022105\"}";
//        Map<String,String> parse = JSON.parse(str);
        Map<String, String> map = (Map<String, String>) JSON.parse(str);
        System.out.println(JSON.toJSONString(map));
        int key = 70001;
        System.out.println(map.get(key));

        System.out.println(26 / 20);
        double dd = 2.0;
        int ii = 2;
        System.out.println(dd == ii);

    }

    @Test
    public void testClassPath() {
//        String classPath = System.getProperty("java.class.path");
//        System.out.println("classPath="+classPath);
//        classPath = classPath.substring(0,classPath.lastIndexOf("/"));
//        String path = classPath+"conf/application.properties";
//        System.out.println("path="+path);

        String classesloadPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println("classesPath=" + classesloadPath);
        System.out.println("user.dir=" + System.getProperty("user.dir"));
        System.out.println("java.class.path=" + System.getProperty("java.class.path"));

//        String path = "/data/app/taf/tafnode/data/CoopOversea.ApiService/bin/CoopOversea.ApiService.jar!/BOOT-INF/classes!/";
//        path = path.substring(0,path.lastIndexOf(".jar"));
//        System.out.println(path);
//        path = path.substring(0,path.lastIndexOf("/"));
//        System.out.println(path);
    }
}
