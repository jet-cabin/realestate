package com.jet.realestate.common.utils;

import java.util.Map;

public interface ObjectConverter<S, T> {
    void convert(S source, T target, Map<String, ConvertField> specialFields);

    T convert(S source, Class<T> targetClass, Map<String, ConvertField> specialFields);
}
