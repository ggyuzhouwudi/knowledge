package stock.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Oliver
 * @date 2023年02月18日 19:24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    @TableId
    private Long id;

    private Long productId;

    private Long count;
}
