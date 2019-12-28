package com.jet.realestate.biz.controller;

import com.jet.realestate.biz.client.HouseClient;
import com.jet.realestate.biz.client.UserClient;
import com.jet.realestate.biz.common.HouseParam;
import com.jet.realestate.biz.model.House;
//import com.jet.realestate.biz.service.HouseService;
import com.jet.realestate.biz.model.User;
import com.jet.realestate.biz.service.HouseService;
import com.jet.realestate.common.utils.ConvertField;
import com.jet.realestate.common.utils.DefaultObjectConverter;
import com.jet.realestate.common.utils.ObjectConverter;
import com.jet.realestate.common.api.CommonPage;
import com.jet.realestate.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Api(tags = "HouseController", value = "房屋管理")
@RestController
@RequestMapping("/v1/house")
@CrossOrigin
public class HouseController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private HouseClient houseClient;

    @Autowired
    private HouseService houseService;

    @ApiOperation("添加房屋信息")
    @PostMapping
    public CommonResult<House> addHouse(@RequestBody House house) {
        return houseClient.addHouse(house);
    }

    @ApiOperation("查询房屋列表")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<House>> qureyHouses(@RequestBody HouseParam param) {
//        List<House> houses = houseClient.queryHouses(param);
//        Long total = (long) houseClient.queryHousesCount(param);
//        CommonPage<House> pageInfo = CommonPage.restPage(houses);
//        pageInfo.setTotal(total);
//        return CommonResult.success(pageInfo);
        return houseClient.queryHouses(param);
    }

    @ApiOperation("查询房屋详情")
    @GetMapping("/{id}")
    @ResponseBody
    public CommonResult<com.jet.realestate.biz.vo.House> qureyHouse(@PathVariable("id") Long id) {
        House house = houseClient.queryHouse(id).getData();
        com.jet.realestate.biz.vo.House result = new com.jet.realestate.biz.vo.House();
        convert(house, result);//data object转换view object
        return CommonResult.success(result);
    }

    private void convert(House house, com.jet.realestate.biz.vo.House result) {
        List<Long> userIds = new ArrayList<>();
        if (null != house.getVendeeId()) {
            userIds.add(house.getVendeeId());
        }
        userIds.add(house.getVendorId());
        Map<Long, User> users = userClient.queryUsers(userIds)
                .stream().collect(Collectors.toMap(User::getId, u -> u));
        ObjectConverter<House, com.jet.realestate.biz.vo.House> converter = new DefaultObjectConverter<>();

        Map<String, ConvertField> specialFields = new HashMap<>();

        Function<Long, User> fun = (userId) -> {
            return users.get(userId);
        };
        ConvertField<Long, User> cf = new ConvertField<>();
        cf.setFieldName("vendor");
        cf.setProcessMethod(fun);
        specialFields.put("vendorId", cf);

        ConvertField<Long, User> field = new ConvertField<>();
        field.setFieldName("vendee");
        field.setProcessMethod(fun);
        specialFields.put("vendeeId", field);
        converter.convert(house, result, specialFields);
    }

    @PutMapping("/{id}/{vendorId};publish")
    public CommonResult<?> publish(@PathVariable("id") Long id, @PathVariable("vendorId") Long vendorId) {
        houseClient.publish(id, vendorId);
        return CommonResult.success(null);
    }

    @RequestMapping(value = "/{id}/{phone};audit", method = RequestMethod.PUT)
    public CommonResult<?> audit(@PathVariable("id") Long id, @PathVariable("phone") String phone) {
        houseClient.audit(id, phone);
        return CommonResult.success(null);
    }

    @ApiOperation("租用房屋")
    @PutMapping("/{id};rent")
    public CommonResult<?> rent(@ApiParam("房屋实体") @PathVariable("id") Long id, @RequestBody House house) {
        houseService.rent(id,house);
        return CommonResult.success(null);
    }

    @PutMapping("/{id}")
    public CommonResult<?> update(@RequestBody House house) {
        houseClient.update(house);
        return CommonResult.success(null);
    }

    @DeleteMapping("/{id}")
    public CommonResult<?> delete(@PathVariable("id") Long id) {
        houseClient.delete(id);
        return CommonResult.success(null);
    }

    @GetMapping("/m")
    public String q(@MatrixVariable("act") String act, @MatrixVariable("id") Long id) {
        return String.format("%s....%d", act, id);
    }
}
