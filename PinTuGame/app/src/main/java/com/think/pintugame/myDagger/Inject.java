package com.think.pintugame.myDagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by XiuWuZhuo on 2016/1/13.
 * Emial:nimdanoob@163.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Inject {
    int value();
}
