package com.gy.feign.fallback;

import com.gy.feign.api.OrderServiceClient;
import com.gy.domain.vo.req.OrderNoReqVO;
import com.gy.domain.vo.res.OrderNoResVO;
import com.gy.enums.StatusEnum;
import com.gy.domain.BaseResponse;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2017/9/3 20:52
 * @since JDK 1.8
 */
public class OrderServiceFallBack implements OrderServiceClient {
    @Override
    public BaseResponse<OrderNoResVO> getOrderNo(@RequestBody OrderNoReqVO orderNoReq) {
        BaseResponse<OrderNoResVO> baseResponse = new BaseResponse<>() ;
        OrderNoResVO vo = new OrderNoResVO() ;
        vo.setOrderId(123456L);
        baseResponse.setDataBody(vo);
        baseResponse.setMessage(StatusEnum.FALLBACK.getMessage());
        baseResponse.setCode(StatusEnum.FALLBACK.getCode());
        return baseResponse;
    }

}
