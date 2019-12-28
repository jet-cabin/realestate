package com.jet.realestate.house.mapper;

import com.jet.realestate.house.common.HouseParam;
import com.jet.realestate.house.model.House;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseMapper {
    /**
     * 插入记录返回主键，参数不能声明@Param注解，否则解析参数多了一个PARAM1  MAPPERMETHOD-> Object param = method.convertArgsToSqlCommandParam(args)
     * @param house
     * @return
     */
    int insert(House house);

    List<House> queryAvailableHouses(@Param("queryParams") HouseParam houseParam);

    int queryAvailableHousesCount(@Param("queryParams") HouseParam houseParam);

    List<House> queryHouses(@Param("ids") List<Long> ids);

    House queryHouse(@Param("houseId") Long id);

    void publish(@Param("houseId") Long id,@Param("vendorId") Long vendorId);

    void rent(@Param("vendeeId") Long vendeeId, @Param("houseId") Long id);

    void audit(@Param("houseId") Long id,@Param("phone")  String phone);

    void update(@Param("house") House house);

    void delete(@Param("houseId") Long houseId);
}
