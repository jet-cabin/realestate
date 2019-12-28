package com.jet.realestate.house.controller;


import com.jet.realestate.common.api.CommonPage;
import com.jet.realestate.common.api.CommonResult;
import com.jet.realestate.common.utils.ConvertField;
import com.jet.realestate.common.utils.DefaultObjectConverter;
import com.jet.realestate.common.utils.ObjectConverter;

import com.jet.realestate.house.common.RentParam;
import com.jet.realestate.house.model.House;
import com.jet.realestate.house.model.Rent;
import com.jet.realestate.house.model.User;
import com.jet.realestate.house.service.HouseService;
import com.jet.realestate.house.service.RentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Api("租金控制器")
@RestController
@RequestMapping("/v1/rent")
public class RentController {

    @Autowired
    private RentService service;

    @Autowired
    private HouseService houseService;

    @ApiOperation("租用")
    @PostMapping
    @ResponseBody
    public CommonResult<Rent> rent(@RequestBody Rent data) {
        service.create(data);

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
    public CommonResult<CommonPage<Rent>> queryRents(@RequestBody RentParam param) {
        //获取租金列表
        List<Rent> rents = service.queryRents(param);

        //封装view object，由以上数据构造，转化
        List<Rent> result = rents;

        CommonPage<Rent> page = CommonPage.restPage(result);
        long total = service.queryRentsCount(param);
        page.setTotal(total);

        return CommonResult.success(page);
    }

    private void convert(House house, House result, Map<Long, User> users) {

        ObjectConverter<House, House> converter = new DefaultObjectConverter<>();

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
