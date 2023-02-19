package order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import order.entity.Orders;

/**
* @author Oliver
* @description 针对表【order】的数据库操作Service
*/
public interface OrdersService extends IService<Orders> {

    Object add();
}
