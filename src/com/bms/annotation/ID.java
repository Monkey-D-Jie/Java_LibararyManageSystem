package com.bms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ID {
	//是否是自增列型的主键
	boolean isAutoIncrement() default false;
	//非自增列的话，又是属于String型的主键
	String name();
}
