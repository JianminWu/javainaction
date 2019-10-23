package com.ljljob.designer.feature;

/**
 * @Author: wujianmin
 * @Date: 2019/9/25 15:35
 * @Function:
 * @Version 1.0
 */
@FunctionalInterface
public interface FeatureTask<T> {
    T call() ;
}
