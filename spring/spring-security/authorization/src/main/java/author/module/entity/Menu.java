package author.module.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Oliver
 * @date 2023年01月06日 0:10
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    private Long id;
    private String pattern;
    private List<Role> roles;

}
