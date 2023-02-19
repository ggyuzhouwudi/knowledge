package stock.service;

import stock.entity.Stock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author User
* @description 针对表【stock】的数据库操作Service
* @createDate 2023-02-18 17:52:27
*/
public interface StockService extends IService<Stock> {

    Boolean reduct(Long productId);

}
