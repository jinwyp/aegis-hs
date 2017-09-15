package com.yimei.hs.boot.persistence;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;

/**
 * Created by xiangyang on 16/8/29.
 */
public class CustomerObjectWrapperFactory extends DefaultObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        if (object.getClass() == Page.class) {
            return true;
        }
        return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        if (object instanceof Page) {
            return new CustomerObjectWrapper(metaObject,(Page) object);
        }
        throw new ReflectionException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
    }

}
