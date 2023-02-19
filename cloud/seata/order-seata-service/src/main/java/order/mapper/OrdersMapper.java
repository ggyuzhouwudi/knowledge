package order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import order.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Oliver
* @description 针对表【order】的数据库操作Mapper
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




