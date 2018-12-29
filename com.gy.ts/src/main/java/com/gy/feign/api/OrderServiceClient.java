package com.gy.feign.api;

import com.gy.service.api.OrderService;
import com.gy.domain.vo.req.OrderNoReqVO;
import com.gy.domain.vo.res.OrderNoResVO;
import com.gy.domain.BaseResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2017/7/16 19:00
 * @since JDK 1.8
 */
@RequestMapping(value="/orderService")
@FeignClient(name="sbc-order"
        //// FIXME: 26/04/2018 为了方便测试，先把降级关掉
        //fallbackFactory = OrderServiceFallbackFactory.class,
        // FIXME: 2017/9/4 如果配置了 fallback 那么 fallbackFactory 将会无效
        //fallback = OrderServiceFallBack.class,
        //configuration = OrderConfig.class
)
@RibbonClient
public interface OrderServiceClient extends OrderService{


    /**
     * 获取订单号
     * @param orderNoReq
     * @return
     */
    @Override
    @ApiOperation("获取订单号")
    @RequestMapping(value = "/getOrderNo", method = RequestMethod.POST)
    BaseResponse<OrderNoResVO> getOrderNo(@RequestBody OrderNoReqVO orderNoReq) ;

}
