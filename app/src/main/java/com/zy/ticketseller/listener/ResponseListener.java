package com.zy.ticketseller.listener;

public interface ResponseListener<T> {


    /**
     * Returns the success of the call of type T.
     * 
     * @param object Response object, can be any number of object types, depending on the protocol/capability/etc
     */
    void onSuccess(T object);

    void onFailed(String message);

    /**
     *
     * @return 返回具体的泛型类型
     */
    Class<T> getEntityClass();
}
