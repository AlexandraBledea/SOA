package com.ubbcluj.task.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Available at runtime
@Target(ElementType.METHOD)         // Can be used on methods
public @interface LogToKafka {
}
