package com.jet.realestate.common.utils;

import lombok.Builder;
import lombok.Data;

import java.util.function.Function;

@Data
//@Builder
public class ConvertField<T,R> {
    String fieldName;
    Class<?> fieldType;
    Function<T,R> processMethod;
}
