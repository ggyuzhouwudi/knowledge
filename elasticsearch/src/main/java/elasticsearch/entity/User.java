package elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Oliver
 * @date 2023年01月11日 17:30
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String name;
    private Integer age;

}
