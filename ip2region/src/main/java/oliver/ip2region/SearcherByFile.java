package oliver.ip2region;

import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 完全基于文件的查询
 * @author Oliver
 * @date 2023年03月01日 22:33
 */
public class SearcherByFile {

    public static void main(String[] args) throws IOException {
        // 1、创建 searcher 对象
        String dbPath = "E:\\workspace\\java\\ip2region\\data\\iptest.xdb";
        Searcher searcher;
        try {
            searcher = Searcher.newWithFileOnly(dbPath);
        } catch (IOException e) {
            System.out.printf("failed to create searcher with `%s`: %s\n", dbPath, e);
            return;
        }

        // 2、查询
        String ip = null;
        try {
            ip = "2.56.11.1";
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - sTime);
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            System.out.printf("failed to search(%s): %s\n", ip, e);
        }

        // 3、关闭资源
        searcher.close();

        // 备注：并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。
    }
}
