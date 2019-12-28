package com.jet.realestate.biz.client;

import com.jet.realestate.biz.common.RentParam;
import com.jet.realestate.biz.model.Rent;
import com.jet.realestate.common.api.CommonPage;
import com.jet.realestate.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(serviceId = "house-service", path = "/v1/rent")
public interface RentClient {
    @RequestMapping(method = POST)
    @ResponseBody
    CommonResult<Rent> rent(@RequestBody Rent data);

    /**
     * 统计查询租金日志
     *
     * @param param 租金查询条件参数
     * @return
     */

    @RequestMapping(method = GET)
    @ResponseBody
    CommonResult<CommonPage<Rent>> queryRents(@RequestBody RentParam param);
}
