import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by bruenni on 28.05.16.
 */
public class ThreadTests {
    @Test
    public void When_threadpool_expect_n_submits_lead_to_n_threads() throws Exception {
        String n = "1";

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("tid=" + Thread.currentThread().getId());
            return String.valueOf(n);
        };

        Future<String> f1 = executorService.submit(callable);
        Future<String> f2 = executorService.submit(callable);

        System.out.println("main tid=" + Thread.currentThread().getId());
        String actual = f1.get(2000, TimeUnit.MILLISECONDS);
        Assert.assertEquals(n, actual);
    }
}
