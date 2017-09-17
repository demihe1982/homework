package cn.dyan.tx;

/**
 * 定义无@MyTransaction的事务属性
 * Created by demi on 2017/9/16.
 */
public class NullMyTransactionAttribute implements MyTransactionAttribute  {
   private String quailifier;
    @Override
    public String getQualifier() {
        return this.quailifier;
    }

    public void setQuailifier(String quailifier) {
        this.quailifier = quailifier;
    }
}
