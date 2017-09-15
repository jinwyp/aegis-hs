package com.yimei.hs.boot.persistence;


import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.Collection;

/**
 * Created by xiangyang on 16/8/29.
 */
public class CustomerObjectFactory extends DefaultObjectFactory {

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type) || Page.class == type;
    }

    @Override
    public <T> T create(Class<T> type) {
        if (type == Page.class) {
            return (T) Page.createInstance();
        }
        return super.create(type, null, null);
    }

}
