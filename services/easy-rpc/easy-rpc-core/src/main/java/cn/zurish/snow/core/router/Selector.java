package cn.zurish.snow.core.router;

/**
 * 2023/12/29 14:35
 */
public class Selector {

    /**
     * 服务命名
     * eg: com.shaogezhu.test.DataService
     */
    private String providerServiceName;

    public String getProviderServiceName() {
        return providerServiceName;
    }

    public void setProviderServiceName(String providerServiceName) {
        this.providerServiceName = providerServiceName;
    }

}