package com.jet.realestate.common.utils;


//import com.jet.realestate.biz.model.Rent;
//import com.jet.realestate.biz.vo.House;
//import com.jet.realestate.security.model.User;

import org.springframework.util.CollectionUtils;
import org.springframework.util.TypeUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DefaultObjectConverter<S, T> implements ObjectConverter<S, T> {

    public T convert(S source, Class<T> targetClass, Map<String, ConvertField> specialFields) {
        try {
            T target = targetClass.getConstructor().newInstance();

            convert(source, target, specialFields);

            return target;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void convert(S source, T target, Map<String, ConvertField> specialFields) {

        Class<?> srcClazz = source.getClass();
        Class<?> targetClazz = target.getClass();


        Field[] srcFields = srcClazz.getDeclaredFields();
//        Map<String, Method> methodMap = Arrays.stream(srcClazz.getMethods()).collect(Collectors.toMap(Method::getName, m -> m));
//.filter(f -> f.getModifiers() == Member.PUBLIC)
        Arrays.stream(srcFields).forEach(srcField -> {

            String name = srcField.getName();
            Class<?> fieldType = srcField.getType();

            try {
                PropertyDescriptor srcPd = new PropertyDescriptor(name, srcClazz);
                PropertyDescriptor targetPd;
                ConvertField cf = null;
                if (!CollectionUtils.isEmpty(specialFields) && specialFields.containsKey(name)) {
                    cf = specialFields.get(name);
                    String fn = cf.getFieldName();
                    targetPd = new PropertyDescriptor(fn, targetClazz);
                } else {
                    targetPd = new PropertyDescriptor(name, targetClazz);
                }

                Object val = srcPd.getReadMethod().invoke(source);
                Method targetSetter = targetPd.getWriteMethod();

                if (cf != null && cf.getProcessMethod() != null) {
                    Function fun = cf.getProcessMethod();

                    Object v = fun.apply(val);
                    targetSetter.invoke(target, v);
                } else {

                    Class[] targetParamTypes = targetSetter.getParameterTypes();
                    if (targetParamTypes.length == 1 && targetParamTypes[0].equals(fieldType)) {
                        targetSetter.invoke(target, val);
                    }
                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

//    public static void main(String[] args) {
//        Rent srcRent=new Rent();
//        srcRent.setId(1L);
//        srcRent.setHouseId(6L);
//        srcRent.setCreateTime(LocalDateTime.now());
//        srcRent.setPrice(13);
//        srcRent.setVendeeId(1L);
//
//        com.jet.realestate.biz.vo.Rent targetRent= new com.jet.realestate.biz.vo.Rent();
//
//        ObjectConverter<Rent,com.jet.realestate.biz.vo.Rent> converter=new DefaultObjectConverter<>();
//
//        Map<String,ConvertField> specialMap=new HashMap<>();
//
////        specialMap.put("houseId", ConvertField.builder().fieldName("house").fieldType(House.class).processMethod((houseId)->{
////            House house=House.builder().id((long)houseId).newHouse(true).address("海淀").area(1111).audit(true).build();
////
////            return house;
////        }).build());
////
////        specialMap.put("vendeeId", ConvertField.builder().fieldName("vendee").fieldType(User.class).processMethod((vendeeId)->{
////            User vendee=new User();
////            vendee.setId((long)vendeeId);
////            vendee.setUsername("jet");
////            vendee.setEmail("1@163.com");
////            vendee.setNickname("nick");
////            vendee.setPhone("15801689640");
////            return vendee;
////        }).build());
//
//        converter.convert(srcRent,targetRent,specialMap);
//
//        System.out.println(targetRent.getVendee().getUsername());
//    }
}