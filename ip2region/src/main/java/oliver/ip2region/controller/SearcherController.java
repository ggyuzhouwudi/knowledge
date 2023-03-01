package oliver.ip2region.controller;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Oliver
 * @date 2023年03月01日 23:18
 */
@RestController
public class SearcherController {

    @Resource
    private Searcher searcher;

    @GetMapping("searcher")
    public String test(String ip) throws Exception {
        long sTime = System.nanoTime();
        String region = searcher.search(ip);
        long cost = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - sTime);
        System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        return region;
    }
}
