package com.lira.automobileLira.interfaces;

import java.util.List;

public interface IConvertData {
    <T> T getData(String json, Class<T> classType);

    <T> List<T> getDataList(String json, Class<T> classType);
}
