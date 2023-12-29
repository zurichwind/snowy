package cn.zurish.snow.test.wind0.route.impl;

import cn.zurish.snow.test.wind0.route.SnowRpcLoadBalance;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LRUStrategy extends SnowRpcLoadBalance {

    private final ConcurrentMap<String, LinkedHashMap<String, String>> jobLRUMap = new ConcurrentHashMap<String, LinkedHashMap<String, String>>();

    private long CACHE_VALID_TIME = 0;

    public String doRoute(String serviceKey, TreeSet<String> addressSet) {

        // cache clear

        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            jobLRUMap.clear();
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000 * 60 * 60 * 24;
        }

        // init lru
        LinkedHashMap<String, String> lruItem = jobLRUMap.get(serviceKey);
        if (lruItem == null) {
            /**
             * LinkedHashMap
             * a. accessOrder: true=访问顺序排序(get/put时排序) /ACCESS-LAST: false=插入顺序排序/FIFO
             * b. removeEldestEntry: 新增元素时将会调用，返回true时会删除最老的元素，可封装LinkedHashMap并重写该方法，比如定义最大容量，超出时返回true，即可实现固定长度的LRU算法:
             * */
            lruItem = new LinkedHashMap<String, String>(16, 0.75f,true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                    if (super.size() > 1000) {
                        return true;
                    }else {
                        return false;
                    }
                }
            };
            jobLRUMap.putIfAbsent(serviceKey, lruItem);
        }

        // put new
        for (String address : addressSet) {
            if (!lruItem.containsKey(address)) {
                lruItem.put(address, address);
            }
        }

        // remove old
        List<String> delKeys = new ArrayList<>();
        for(String existKey : lruItem.keySet()) {
            if (!addressSet.contains(existKey)) {
                delKeys.add(existKey);
            }
        }
        if (!delKeys.isEmpty()) {
            for (String delKey : delKeys) {
                lruItem.remove(delKey);
            }
        }

        //load
        String eldestKey = lruItem.entrySet().iterator().next().getKey();
        return lruItem.get(eldestKey);
    }
    @Override
    public String route(String serviceKey, TreeSet<String> addressSet) {
        return doRoute(serviceKey, addressSet);
    }
}













