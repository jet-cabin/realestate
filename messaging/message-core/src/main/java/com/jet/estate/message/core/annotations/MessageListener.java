package com.jet.estate.message.core.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MessageListener {
    String topic() default "";
    String tag() default "";
}
