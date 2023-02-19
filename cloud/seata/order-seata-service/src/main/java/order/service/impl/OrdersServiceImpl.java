package order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import order.entity.Orders;
import order.feign.StockService;
import order.mapper.OrdersMapper;
import order.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Oliver
 * @description 针对表【order】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
        implements OrdersService {
    private final StockService stockService;

    @Override
    @GlobalTransactional
    public Object add() {
        Orders orders = Orders.builder().productId(1L).total(1).status(1).build();
        this.baseMapper.insert(orders);
        stockService.reduct(orders.getProductId());
        return orders.getId();
    }
}




