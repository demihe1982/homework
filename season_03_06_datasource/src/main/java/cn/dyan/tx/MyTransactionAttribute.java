package cn.dyan.tx;

/**
 * 保存事务属性
 * 类名加+方法名组合
 * 类或者方法上带有@MyTransactional 注解，则实例化该对象
 * Created by demi on 2017/9/16.
 */
public interface MyTransactionAttribute {
    String getQualifier();
}
