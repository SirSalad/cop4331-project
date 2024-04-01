package edu.ucf.cop4331project.common.container;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a container.
 * Such classes will be automatically detected and have their lifecycle managed by the backing runtime.
 * Identifying what kind of container should be done with marker interfaces or other annotations.
 */
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Container {
}
