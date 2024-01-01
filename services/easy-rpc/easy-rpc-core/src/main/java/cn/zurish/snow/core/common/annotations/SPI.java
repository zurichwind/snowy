package cn.zurish.snow.core.common.annotations;

import java.lang.annotation.*;

/**
 * 2023/12/30 10:17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPI {

    String value() default "";
}
