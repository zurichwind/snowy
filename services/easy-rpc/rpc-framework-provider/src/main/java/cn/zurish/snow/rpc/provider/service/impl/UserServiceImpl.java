package cn.zurish.snow.rpc.provider.service.impl;

import cn.zurish.snow.rpc.interfaces.DataService;
import cn.zurish.snow.rpc.interfaces.UserService;
import cn.zurish.snow.rpc.spring.starter.common.EasyRpcService;

import java.util.ArrayList;
import java.util.List;

/**
 * 2023/12/31 19:41
 */
@EasyRpcService
public class UserServiceImpl implements UserService {

    @Override
    public void test() {
        System.out.println("UserServiceImpl : test");
    }
}
