package com.pandawork.crm.common.utils;

import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.framework.dao.impl.FieldPair;
import com.pandawork.core.framework.dao.repository.EntityRepository;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体设置工具类
 *
 * @author: zhangteng
 * @time: 2014/9/29 15:59
 */
public final class EntityUtil {

    /**
     * 实体-fieldPairList缓存
     *
     */
    private static FieldPairRepository fieldPairRepository = new FieldPairRepository();

    private final static String[] escaps = {"class", "allFileds", "tableName", "allFiledsKeys", "allFiledsValues"};

    /**
     * 将实体中null字段的值设置为默认值
     * String类型设置为""
     * Integer类型设置为0
     *
     * @param obj
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void setNullFieldDefault(Object obj) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        List<FieldPair> fieldPairList = fieldPairRepository.getValue(obj.getClass(), obj);
        if(fieldPairList != null) {
            for(FieldPair fieldPair : fieldPairList) {
                setDefault(obj, fieldPair);
            }
        }
        return ;
    }

    /**
     * 设置默认值
     *
     * @param obj
     * @param fieldPair
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void setDefault(Object obj, FieldPair fieldPair) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Object value = readValue(obj, fieldPair);
        if(value != null) {
            return ;
        }
        Field field = fieldPair.getField();

        Class<?> cls = field.getType();
        if(cls == String.class) {
            setValue(obj, fieldPair, "");
        } else if(cls == Integer.class || cls == Float.class || cls == Double.class) {
            setValue(obj, fieldPair, 0);
        } else if(cls == BigDecimal.class) {
            setValue(obj, fieldPair, BigDecimal.ZERO);
        }
        return ;
    }

    /**
     * 获取FieldPair的value
     *
     * @param bean
     * @param fieldPair
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object readValue(Object bean, FieldPair fieldPair) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Method readMethod = getReadMethod(fieldPair);
        if (readMethod == null) {
            return null;
        }
        return readMethod.invoke(bean);
    }

    /**
     * 设置FieldPair的value
     *
     * @param bean
     * @param fieldPair
     * @param value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void setValue(Object bean, FieldPair fieldPair, Object value) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Method writeMethod = getWriteMethod(fieldPair);
        if(writeMethod == null) {
            return ;
        }
        writeMethod.invoke(bean, value);
        return ;
    }

    /**
     * 获得FieldPair的读方法
     *
     * @param fieldPair
     * @return
     */
    public static Method getReadMethod(FieldPair fieldPair) {
        Method method = fieldPair.getPd().getReadMethod();
        return method;
    }

    /**
     * 获得FieldPair的写方法
     *
     * @param fieldPair
     * @return
     */
    public static Method getWriteMethod(FieldPair fieldPair) {
        Method method = fieldPair.getPd().getWriteMethod();
        return method;
    }

    /**
     * 实体-fieldPairList缓存
     */
    static class FieldPairRepository extends ConcurrentHashMap<Class<?>, List<FieldPair>> implements EntityRepository<Class<?>, List<FieldPair> > {

        @Override
        public void add(Class<?> cla) {
            return ;
        }

        @Override
        public List<FieldPair> getValue(Class<?> key, Object... objs) {
            Object obj = objs[0];
            Class<?> cla = obj.getClass();

            List<FieldPair> fieldPairList = super.get(key);
            if(fieldPairList == null) {
                try {
                    fieldPairList = listField(obj, cla);
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw new RuntimeException("类（" + cla.getName() + "）获取域异常：" + e.getMessage());
                }
                super.put(key, fieldPairList);
            }
            return fieldPairList;
        }
    }

    /**
     * 返回所有的域描述符
     *
     * @param obj
     * @param cla
     * @return
     * @throws Exception
     */
    public static List<FieldPair> listField(Object obj, Class<?> cla) throws Exception {
        List<FieldPair> fields = new ArrayList<FieldPair>(); // .get(cla);
        PropertyDescriptor[] tmpFields = PropertyUtils.getPropertyDescriptors(cla);

        boolean isEscap = false;
        fields = new ArrayList<FieldPair>();
        for (PropertyDescriptor pd : tmpFields) {
            isEscap = false; // 是否已经被过滤.true表示已经被过滤，false表示没有被过滤。
            for (String escap : escaps) {
                if (escap.equals(pd.getName())) {
                    isEscap = true;
                }
            }

            if (isEscap) { // 如果出现已经被过滤，则直接进入下一个域
                continue;
            }

            FieldPair pair = new FieldPair();
            pair.setCla(cla);
            pair.setPd(pd);
            pair.setField(cla.getDeclaredField(pd.getName()));

            fields.add(pair);
        }

        return fields;
    }
}
