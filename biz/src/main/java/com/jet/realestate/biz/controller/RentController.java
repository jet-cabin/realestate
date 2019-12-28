package com.jet.realestate.biz.controller;

import com.jet.realestate.biz.client.HouseClient;
import com.jet.realestate.biz.client.RentClient;
import com.jet.realestate.biz.client.UserClient;
import com.jet.realestate.biz.common.HouseParam;
import com.jet.realestate.biz.common.RentParam;
import com.jet.realestate.biz.model.User;
import com.jet.realestate.common.utils.ConvertField;
import com.jet.realestate.common.utils.DefaultObjectConverter;
import com.jet.realestate.common.utils.ObjectConverter;
import com.jet.realestate.biz.vo.House;
import com.jet.realestate.biz.model.Rent;
import com.jet.realestate.common.api.CommonPage;
import com.jet.realestate.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Api("租金控制器")
@RestController
@RequestMapping("/v1/rent")
public class RentController {

    @Autowired
    private RentClient client;

    @Autowired
    private UserClient userClient;

    @Autowired
    private HouseClient houseClient;

    @ApiOperation("租用")
    @PostMapping
    @ResponseBody
    public CommonResult<Rent> rent(@RequestBody com.jet.realestate.biz.model.Rent data) {
        client.rent(data);

        return CommonResult.success(data);
    }

    /**
     * 统计查询租金日志
     *
     * @param param 租金查询条件参数
     * @return
     */
    @ApiOperation("获取租金列表")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<com.jet.realestate.biz.vo.Rent>> queryRents(@RequestBody RentParam param) {

        //获取租金列表
        CommonPage<Rent> rents = client.queryRents(param).getData();

        Set<Long> houseIds = rents.getList().stream().map(r -> r.getHouseId()).collect(Collectors.toSet());
        Set<Long> userIds = rents.getList().stream().map(r -> r.getVendeeId()).collect(Collectors.toSet());

        HouseParam houseParam = new HouseParam();
        houseParam.setIds(houseIds.stream().collect(Collectors.toSet()));
        //获取租金列表相关房屋信息
        List<com.jet.realestate.biz.model.House> houses = houseClient.queryHouses(houseParam).getData().getList();

        userIds.addAll(houses.stream().map(h -> h.getVendorId()).collect(Collectors.toSet()));
        userIds.addAll(houses.stream().filter(h -> h.getVendeeId() != null).map(h -> h.getVendeeId()).collect(Collectors.toSet()));

        //获取租金列表相关交易人员信息
        Map<Long, User> users = userClient.queryUsers(userIds).stream().collect(Collectors.toMap(User::getId, u -> u));

        Map<Long, House> hs = houses.stream().map(h -> {
            House house = new House();
            convert(h, house, users);
            return house;
        }).collect(Collectors.toMap(House::getId, h -> h));

        //准备data object 到 view object转化条件
        ObjectConverter<com.jet.realestate.biz.model.Rent, com.jet.realestate.biz.vo.Rent> converter = new DefaultObjectConverter<>();
        Map<String, ConvertField> specialMap = new HashMap<>();
        ConvertField<Long, House> cfh = new ConvertField();
        cfh.setFieldName("house");
        cfh.setProcessMethod((houseId) -> {
            return hs.get(houseId);
        });
        specialMap.put("houseId", cfh);

        ConvertField<Long, User> cfu = new ConvertField();
        cfu.setFieldName("vendee");
        cfu.setProcessMethod((userId) -> {
            return users.get(userId);
        });
        specialMap.put("vendeeId", cfu);


        //封装view object，由以上数据构造，转化
        List<com.jet.realestate.biz.vo.Rent> result = rents.getList().stream().map(r -> {
            com.jet.realestate.biz.vo.Rent rr = new com.jet.realestate.biz.vo.Rent();
            converter.convert(r, rr, specialMap);
            return rr;
        }).collect(Collectors.toList());

        CommonPage<com.jet.realestate.biz.vo.Rent> page = new CommonPage<>();
        page.setList(result);
        page.setTotal(rents.getTotal());
        return CommonResult.success(page);
    }

    private void convert(com.jet.realestate.biz.model.House house, com.jet.realestate.biz.vo.House result, Map<Long, User> users) {

        ObjectConverter<com.jet.realestate.biz.model.House, com.jet.realestate.biz.vo.House> converter = new DefaultObjectConverter<>();

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
}
