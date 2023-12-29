package cn.zurish.snow.test.wind0.route;

import java.util.TreeSet;

/**
 * 分组下机器地址相同，不同的JOB均匀分散在不同机器上，保证分组下机器分配JOB平均；且每个JOB固定调度其中一台机器；
 *  a. virtual node: 解决不均衡问题
 *  b. hash method replace hashCode: String的hashCode可能重复，需要进一步扩大hashCode的取值范围
 *
 * */

public abstract class SnowRpcLoadBalance {

    public abstract  String route(String serviceKey, TreeSet<String> addressSet);

}
