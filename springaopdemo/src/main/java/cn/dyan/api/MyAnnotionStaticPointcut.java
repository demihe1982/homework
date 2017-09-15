package cn.dyan.api;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by demi on 2017/9/15.
 */
public class MyAnnotionStaticPointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return method.isAnnotationPresent(MyAnnotation.class) || aClass.isAnnotationPresent(MyAnnotation.class);
    }
}
