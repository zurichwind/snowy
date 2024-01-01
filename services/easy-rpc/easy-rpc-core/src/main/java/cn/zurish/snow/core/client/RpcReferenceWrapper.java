package cn.zurish.snow.core.client;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  rpc远程调用包装类
 * 2023/12/30 10:57
 */
@Getter
public class RpcReferenceWrapper<T> {

    private Class<T> aimClass;

    private Map<String,Object> attachments = new ConcurrentHashMap<>();

    public void setAimClass(Class<T> aimClass) {
        this.aimClass = aimClass;
    }

    /**
     * 失败重试次数
     */
    public int getRetry(){
        return (int) attachments.getOrDefault("retry",0);
    }

    public void setRetry(int retry){
        this.attachments.put("retry",retry);
    }

    public void setTimeOut(int timeOut) {
        attachments.put("timeOut", timeOut);
    }

    public String getTimeOut() {
        return String.valueOf(attachments.getOrDefault("timeOut",""));
    }
    public boolean isAsync(){
        return Boolean.parseBoolean(String.valueOf(attachments.get("async")));
    }

    public void setAsync(boolean async){
        this.attachments.put("async",async);
    }

    public String getUrl(){
        return String.valueOf(attachments.get("url"));
    }

    public void setUrl(String url){
        attachments.put("url",url);
    }

    public String getServiceToken(){
        return String.valueOf(attachments.get("serviceToken"));
    }

    public void setServiceToken(String serviceToken){
        attachments.put("serviceToken",serviceToken);
    }

    public String getGroup(){
        return String.valueOf(attachments.get("group"));
    }

    public void setGroup(String group){
        attachments.put("group",group);
    }

    public void setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
    }
}
