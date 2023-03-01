package oliver.ip2region.config;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Oliver
 * @date 2023年03月01日 23:16
 */
@Configuration
public class Ip2RegionSearcherConfig {

    @Bean
    public Searcher searcher() {
        String dbPath = "E:\\workspace\\java\\ip2region\\data\\iptest.xdb";

        // 1、从 dbPath 加载整个 xdb 到内存。
        byte[] cBuff;
        try {
            cBuff = Searcher.loadContentFromFile(dbPath);
        } catch (Exception e) {
            System.out.printf("failed to load content from `%s`: %s\n", dbPath, e);
            return null;
        }

        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        Searcher searcher;
        try {
            searcher = Searcher.newWithBuffer(cBuff);
            return searcher;
        } catch (Exception e) {
            System.out.printf("failed to create content cached searcher: %s\n", e);
            return null;
        }
    }
}
