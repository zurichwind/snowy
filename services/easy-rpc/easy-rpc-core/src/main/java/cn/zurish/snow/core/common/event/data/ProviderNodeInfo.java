package cn.zurish.snow.core.common.event.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2023/12/29 13:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProviderNodeInfo {
    private String applicationName;

    private String serviceName;

    private String address;

    private Integer weight;

    private String group;

    @Override
    public String toString() {
        return "ProviderNodeInfo{" +
                "applicationName='" + applicationName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", address='" + address + '\'' +
                ", weight=" + weight +
                ", group='" + group + '\'' +
                '}';
    }
}
