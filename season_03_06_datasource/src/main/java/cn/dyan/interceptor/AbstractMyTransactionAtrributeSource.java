package cn.dyan.interceptor;

import cn.dyan.tx.MyTransactionAttribute;
import cn.dyan.tx.NullMyTransactionAttribute;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.MethodClassKey;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by demi on 2017/9/16.
 */
public abstract class AbstractMyTransactionAtrributeSource implements MyTransactionAttributeSource {

    private final static MyTransactionAttribute NULL_TRANSACTION_ATTRIBUTE = new NullMyTransactionAttribute();
    /**
     * 缓存所有拦截的方法，无事务则保存为空事务
     */
    private final Map<Object, MyTransactionAttribute> attributeCache = new ConcurrentHashMap<Object, MyTransactionAttribute>(1024);

    @Override
    public MyTransactionAttribute getTransactionAttribute(Method method, Class<?> targetClass) {
        Object cachekey = getCacheKey(method,targetClass);
        Object value = this.attributeCache.get(cachekey);
        if(value !=null){
            if(value == NULL_TRANSACTION_ATTRIBUTE){
                return null;
            }else{
                return (MyTransactionAttribute)value;
            }
        }else {
            MyTransactionAttribute txAttr = computeTransactionAttribute(method, targetClass);
            if(txAttr ==null){
                this.attributeCache.put(cachekey,NULL_TRANSACTION_ATTRIBUTE);
            }else {
                //构造识别方法标识符
                String methodIdentification = ClassUtils.getQualifiedMethodName(method, targetClass);
                if (txAttr instanceof NullMyTransactionAttribute) {
                    ((NullMyTransactionAttribute) txAttr).setQuailifier(methodIdentification);
                }
                this.attributeCache.put(cachekey,txAttr);
            }
            return txAttr;
        }
    }

    public Object getCacheKey(Method method, Class<?> targetClass){
        return new MethodClassKey(method,targetClass);
    }

    protected MyTransactionAttribute computeTransactionAttribute(Method method, Class<?> targetClass){

        //利用反射查看方法的修饰符，判断是否为Public
        if(!Modifier.isPublic(method.getModifiers())){
            return null;
        }

        //忽略代理类，获取实际对象
        Class<?> userClass = ClassUtils.getUserClass(targetClass);

        //如何是一个接口方法，则获取实际的方法
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, userClass);

        //获取实际被代理的方法
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        MyTransactionAttribute txAttr = findTransactionAttribute(specificMethod);

        if(txAttr!=null){
            return txAttr;
        }

        txAttr = findTransactionAttribute(specificMethod.getDeclaringClass());
        if (txAttr != null && ClassUtils.isUserLevelMethod(method)) {
            return txAttr;
        }

        if(specificMethod != method){
            txAttr = findTransactionAttribute(method);
            if(txAttr!=null){
                return txAttr;
            }
            txAttr = findTransactionAttribute(method.getDeclaringClass());
            if (txAttr != null && ClassUtils.isUserLevelMethod(method)) {
                return txAttr;
            }
        }
        return null;
    }

    public abstract MyTransactionAttribute findTransactionAttribute(Method method);
    public abstract MyTransactionAttribute findTransactionAttribute(Class<?> targetClass);

}
