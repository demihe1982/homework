package cn.dyan.tx;

import cn.dyan.datasource.ConnectionHoldor;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by demi on 2017/9/16.
 */
public abstract class MyTransactionManager {

    //存储DataSource: ConnectionHoldor
    private static ThreadLocal<Map<Object,Object>> resources = new ThreadLocal<Map<Object, Object>>();

    /**
     *
     * @param key DataSource
     * @return ConnectionHoldor
     */
    public static Object getResource(Object key){
        Map<Object,Object> map = resources.get();
        if(map == null){
            return null;
        }
        return  map.get(key);
    }

    public static void bindResource(Object key,Object value){
        Map<Object,Object> map = resources.get();
        if(map == null){
            map = new HashMap<Object,Object>();
            resources.set(map);
        }
        resources.get().put(key,value);
    }

}
