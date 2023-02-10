package basic.sequential;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oliver
 * @date 2023年02月10日 16:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private Long id;
    private String name;

    public static List<Order> build() {
        List<Order> orders = new ArrayList<>();
        orders.add(Order.builder().id(1039L).name("创建").build());
        orders.add(Order.builder().id(1065L).name("创建").build());
        orders.add(Order.builder().id(1039L).name("付款").build());
        orders.add(Order.builder().id(7235L).name("创建").build());
        orders.add(Order.builder().id(1065L).name("付款").build());
        orders.add(Order.builder().id(7235L).name("付款").build());
        orders.add(Order.builder().id(1065L).name("完成").build());
        orders.add(Order.builder().id(7235L).name("完成").build());
        orders.add(Order.builder().id(1039L).name("完成").build());
        return orders;
    }
}
