package cn.zurish.snow.core.common.event.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 2023/12/29 13:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class URLChangeWrapper {
    private String serviceName;

    private List<String> providerUrl;

    /**
     *  记录每个ip下边的url详细信息，包括权重，分组等
     */
    private Map<String,String> nodeDataUrl;


    @Override
    public String toString() {
        return "URLChangeWrapper{" +
                "serviceName='" + serviceName + '\'' +
                ", providerUrl=" + providerUrl +
                ", nodeDataUrl=" + nodeDataUrl +
                '}';
    }
}
