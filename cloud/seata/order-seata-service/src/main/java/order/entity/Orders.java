package order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Oliver
 * @date 2023年02月18日 17:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Orders implements Serializable {

    @TableId
    private Long id;

    private Long productId;

    private Integer total;

    private Integer status;
}
