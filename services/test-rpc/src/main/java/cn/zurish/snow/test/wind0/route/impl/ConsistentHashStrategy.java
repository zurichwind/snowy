package cn.zurish.snow.test.wind0.route.impl;

import cn.zurish.snow.test.exception.SnowException;
import cn.zurish.snow.test.wind0.route.SnowRpcLoadBalance;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * consistent hash
 *
 * 单个JOB对应的每个执行器，使用频率最低的优先被选举
 * a(*) LFU(Least Frequently Used): 最不经常使用 频率/次数
 * b. LRU(Least Recently Used): 最近最久未使用，时间
 * */
public class ConsistentHashStrategy extends SnowRpcLoadBalance {

    private final int VIRTUAL_NODE_NUM = 100;

    private long hash(String key) {
        MessageDigest md5;
        try{
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new SnowException("MD5 not support", e);
        }
        md5.reset();
        byte[] keyBytes = null;
        keyBytes = key.getBytes(StandardCharsets.UTF_8);

        md5.update(keyBytes);
        byte[] digest = md5.digest();

        // hash code, Truncate to 32-bits
        long hashCode = ((long) (digest[3] & 0xFF) <<24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);

        return hashCode & 0xffffffffL;
    }

    public String doRoute(String serviceKey, TreeSet<String> addressSet) {


        TreeMap<Long, String> addressRing = new TreeMap<Long, String>();
        for(String address: addressSet) {
            for(int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                long addressHash  = hash("SHARD-" + address + "-NODE-" + i);;
                addressRing.put(addressHash, address);
            }
        }

        long jobHash = hash(serviceKey);
        SortedMap<Long, String>  lastRing = addressRing.tailMap(jobHash);
        if (!lastRing.isEmpty()) {
            return lastRing.get(lastRing.firstKey());
        }
        return addressRing.firstEntry().getValue();
    }

    @Override
    public String route(String serviceKey, TreeSet<String> addressSet) {
        return doRoute(serviceKey, addressSet);
    }
}
