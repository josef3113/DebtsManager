package com.example.debtsmanager.interfaces;

public interface RequestListener <T>
{
    void onComplete(T t);
    void onError(String msg);

}
