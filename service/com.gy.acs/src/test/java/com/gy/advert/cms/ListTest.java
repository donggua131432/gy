package com.gy.advert.cms;

import com.gy.util.JsonUtil;
import net.minidev.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  * @描述: ${DESCRIPTION}
 *  * @作者: FangLin
 *  * @创建时间: 2018/12/17 10:55
 *  * @版本号: V1.0
 *  
 */
public class ListTest {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        //map集合存入数据
        map.put("1", "第一个value");
        map.put("2", "第二个value");
        map.put("3", "第三个value");
        System.out.println("values:"+map.values());
        System.out.println("size:"+map.size());

        System.out.println(JsonUtil.objectToString(map));
        //通过keySet取出map数据[for-each循环]
        System.out.println("-------[for-each循环遍历]通过keySet取出map数据-------");
        Set<String> keys = map.keySet();   //此行可省略，直接将map.keySet()写在for-each循环的条件中
        for(String key:keys){
            System.out.println("key值："+key+" value值："+map.get(key));
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key:"+entry.getKey()+"value:"+entry.getValue());
        }
    }
}
