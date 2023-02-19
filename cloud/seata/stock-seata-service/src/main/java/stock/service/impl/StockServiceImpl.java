package stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import stock.entity.Stock;
import stock.service.StockService;
import stock.mapper.StockMapper;
import org.springframework.stereotype.Service;

/**
 * @author Oliver
 * @description 针对表【stock】的数据库操作Service实现
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock>
        implements StockService {

    @Override
    public Boolean reduct(Long productId) {
        return this.baseMapper.reduct(productId);
    }
}




