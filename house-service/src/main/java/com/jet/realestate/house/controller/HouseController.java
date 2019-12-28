package com.jet.realestate.house.controller;

import com.jet.realestate.house.common.HouseParam;
import com.jet.realestate.house.model.House;
import com.jet.realestate.house.service.HouseService;
import com.jet.realestate.common.api.CommonPage;
import com.jet.realestate.common.api.CommonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "HouseController", value = "房屋管理")
@RestController
@RequestMapping("/v1/house")
@CrossOrigin
public class HouseController {


    @Autowired
    private HouseService houseService;

    @ApiOperation("添加房屋信息")
    @PostMapping
    public CommonResult<House> addHouse(@RequestBody House house) {
        House hs = houseService.create(house);

        return CommonResult.success(house);
    }

    @ApiOperation("查询房屋列表")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<House>> qureyHouses(@RequestBody HouseParam param) {
        List<House> houses = houseService.queryAvailableHouses(param);
        Long total = (long) houseService.queryAvailableHousesCount(param);
        CommonPage<House> pageInfo = CommonPage.restPage(houses);
        pageInfo.setTotal(total);
        return CommonResult.success(pageInfo);
    }

    @RequestMapping("/list/count")
    @ResponseBody
    public CommonResult<Integer> qureyHousesCount(@RequestBody HouseParam param){
        int count=houseService.queryAvailableHousesCount(param);

        return CommonResult.success(count);
    }

    @ApiOperation("查询房屋详情")
    @GetMapping("/{id}")
    @ResponseBody
    public CommonResult<House> qureyHouse(@PathVariable("id") Long id) {
        House house = houseService.queryHouse(id);
//        House result = new House();
//        convert(house, result);//data object转换view object
        return CommonResult.success(house);
    }

    @PutMapping("/{id}/{vendorId};publish")
    public CommonResult<?> publish(@PathVariable("id") Long id, @PathVariable("vendorId") Long vendorId) {
        houseService.publish(id, vendorId);
        return CommonResult.success(null);
    }

    @RequestMapping(value = "/{id}/{phone};audit", method = RequestMethod.PUT)
    public CommonResult<?> audit(@PathVariable("id") Long id, @PathVariable("phone") String phone) {
        houseService.audit(id, phone);
        return CommonResult.success(null);
    }

    @ApiOperation("租用房屋")
    @PutMapping("/{id};rent")
    public CommonResult<?> rent(@ApiParam("房屋实体") @PathVariable("id") Long id, @RequestBody House house) {
        houseService.rent(house);
        return CommonResult.success(null);
    }

    @PutMapping("/{id}")
    public CommonResult<?> update(@RequestBody House house) {
        houseService.update(house);
        return CommonResult.success(null);
    }

    @DeleteMapping("/{id}")
    public CommonResult<?> delete(@PathVariable("id") Long id) {
        houseService.delete(id);
        return CommonResult.success(null);
    }

    @GetMapping("/m")
    public String q(@MatrixVariable("act") String act, @MatrixVariable("id") Long id) {
        return String.format("%s....%d", act, id);
    }
}
