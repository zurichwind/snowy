package cn.zurish.snow.core.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Semaphore;

/**
 * 服务端限流包装类
 * 2023/12/29 13:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerServiceSemaphoreWrapper {

    private Semaphore semaphore;

    private int maxNums;

    public ServerServiceSemaphoreWrapper(int maxNums) {
        this.maxNums = maxNums;
        this.semaphore = new Semaphore(maxNums);
    }
}
