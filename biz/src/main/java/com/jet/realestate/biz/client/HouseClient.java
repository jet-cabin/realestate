package com.jet.realestate.biz.client;

import com.jet.realestate.biz.common.HouseParam;
import com.jet.realestate.biz.model.House;
import com.jet.realestate.common.api.CommonPage;
import com.jet.realestate.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "houseClient",serviceId = "house-service", path = "/v1/house")
public interface HouseClient {

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    CommonResult<House> addHouse(@RequestBody House house);

    @RequestMapping("/list")
    @ResponseBody
    CommonResult<CommonPage<House>> queryHouses(@RequestBody HouseParam param);

    @RequestMapping("/list/count")
    @ResponseBody
    CommonResult<Integer> queryHousesCount(@RequestBody HouseParam param);

    @RequestMapping("/{id}")
    @ResponseBody
    CommonResult<House> queryHouse(@PathVariable("id") Long id);

    @RequestMapping(value = "/{id}/{vendorId};publish", method = RequestMethod.PUT)
    CommonResult<?> publish(@PathVariable("id") Long id, @PathVariable("vendorId") Long vendorId);

    @RequestMapping(value = "/{id}/{phone};audit", method = RequestMethod.PUT)
    CommonResult<?> audit(@PathVariable("id") Long id, @PathVariable("phone") String phone);

    @RequestMapping(value = "/{id};rent", method = RequestMethod.PUT)
    CommonResult<?> rent(@PathVariable("id") Long id, @RequestBody House house);

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    CommonResult<?> update(@RequestBody House house);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    CommonResult<?> delete(@PathVariable("id") Long id);

}
