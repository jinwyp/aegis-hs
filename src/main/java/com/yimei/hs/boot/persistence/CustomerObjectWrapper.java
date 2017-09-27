package com.yimei.hs.boot.persistence;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;

import java.util.List;

/**
 * Created by xiangyang on 16/8/29.
 */
public class CustomerObjectWrapper implements ObjectWrapper {
    private Page page;
    private MetaObject metaObject;


    public CustomerObjectWrapper(MetaObject metaObject, Page page) {
        this.metaObject = metaObject;
        this.page = page;
    }


    @Override
    public Object get(PropertyTokenizer prop) {
        return null;
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {

    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        return null;
    }

    @Override
    public String[] getGetterNames() {
        return new String[0];
    }

    @Override
    public String[] getSetterNames() {
        return new String[0];
    }

    @Override
    public Class<?> getSetterType(String name) {
        return null;
    }

    @Override
    public Class<?> getGetterType(String name) {
        return null;
    }

    @Override
    public boolean hasSetter(String name) {
        return false;
    }

    @Override
    public boolean hasGetter(String name) {
        return false;
    }

    @Override
    public MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory) {
        return null;
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public void add(Object element) {

    }

    @Override
    public <E> void addAll(List<E> element) {
        try{
            BaseFilter pageParam = PageContext.getPageParam();
            page.setPageNo(pageParam.getPageNo());
            page.setPageSize(pageParam.getPageSize());
            page.setTotalPage(pageParam.getTotalPage());
            page.setTotalRecord(pageParam.getTotalRecord());
            page.setResults(element);
        }finally {
            PageContext.cleanPageParam();
        }
    }
}
