package cn.dyan.api;

import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * Created by demi on 2017/9/15.
 */
public class DynPointcut extends DynamicMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> aClass, Object... objects) {
        return false;
    }
}
