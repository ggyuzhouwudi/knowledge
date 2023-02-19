package stock.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stock.entity.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author Oliver
 * @description 针对表【stock】的数据库操作Mapper
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {

    Boolean reduct(@Param("productId") Long productId);
}




