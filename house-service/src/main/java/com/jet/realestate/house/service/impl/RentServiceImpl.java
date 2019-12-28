package com.jet.realestate.house.service.impl;

import com.jet.realestate.house.common.RentParam;
import com.jet.realestate.house.mapper.RentMapper;
import com.jet.realestate.house.model.Rent;
import com.jet.realestate.house.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentServiceImpl implements RentService {

    @Autowired
    private RentMapper mapper;

    @Override
    public List<Rent> queryRents(RentParam param) {

        return mapper.queryRents(param);
    }

    @Override
    public Integer queryRentsCount(RentParam param) {
        return mapper.queryRentsCount(param);
    }

    @Override
    public Rent query(Long id) {
        return mapper.queryRent(id);
    }

    @Override
    public Rent create(Rent rent) {
        mapper.insert(rent);
        return rent;
    }

    @Override
    public void update(Rent rent) {
        mapper.update(rent);
    }
}
