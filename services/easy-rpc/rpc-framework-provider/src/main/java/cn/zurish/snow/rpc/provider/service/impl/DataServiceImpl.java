package cn.zurish.snow.rpc.provider.service.impl;

import cn.zurish.snow.rpc.interfaces.DataService;
import cn.zurish.snow.rpc.spring.starter.common.EasyRpcService;

import java.util.ArrayList;
import java.util.List;

/**
 * 2023/12/31 19:41
 */
@EasyRpcService(serviceToken = "data-token",group = "data-group",limit = 2)
public class DataServiceImpl implements DataService {

    @Override
    public String sendData(String body) {
        System.out.println("这里是服务提供者，body is " + body);
        return "success from server";
    }

    @Override
    public List<String> getList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("idea1");
        arrayList.add("idea2");
        arrayList.add("idea3");
        return arrayList;
    }

    @Override
    public void testError() {
        System.out.println(1 / 0);
    }

    @Override
    public String testErrorV2() {
        throw new RuntimeException("测试异常");
    }

}
