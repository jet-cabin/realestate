package com.jet.realestate.house.service.impl;

import com.github.pagehelper.PageHelper;
import com.jet.realestate.house.common.HouseParam;
import com.jet.realestate.house.mapper.HouseMapper;
import com.jet.realestate.house.model.House;
import com.jet.realestate.house.model.Rent;
import com.jet.realestate.house.service.HouseService;
import com.jet.realestate.house.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private RentService rentService;

    @Autowired
    private HouseMapper mapper;

    @Override
    public House create(House house) {
        int ret= mapper.insert(house);

         return house;
    }

    @Override
    public List<House> queryAvailableHouses(HouseParam houseParam) {
        PageHelper.startPage(houseParam.getPageNum(), houseParam.getPageSize());return mapper.queryAvailableHouses(houseParam);
    }

    @Override
    public int queryAvailableHousesCount(HouseParam houseParam) {
        return mapper.queryAvailableHousesCount(houseParam);
    }

    @Override
    public House queryHouse(Long id) {
        return mapper.queryHouse(id);
    }

    @Override
    public List<House> queryHouses(List<Long> ids) {
        return mapper.queryHouses(ids);
    }

    @Override
    public void publish(Long id, Long vendorId) {
        mapper.publish(id,vendorId);
    }

    /**
     * 房屋租用
     * @param house
     */
    @Override
    public void rent(House house) {
        mapper.rent(house.getVendeeId(), house.getId());

        //生成租金记录
        Rent rent = new Rent();
        rent.setPrice(house.getPrice());
        rent.setVendeeId(house.getVendeeId());
        rent.setHouseId(house.getId());
        rentService.create(rent);
    }

    @Override
    public void audit(Long id, String phone) {
        mapper.audit(id,phone);
    }

    @Override
    public void update(House house) {
        mapper.update(house);
    }

    @Override
    public void delete(Long houseId) {
        mapper.delete(houseId);
    }
}
