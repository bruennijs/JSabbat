package sabbat.location.cep.flink.evictor;

import infrastructure.util.IterableUtils;
import org.apache.flink.streaming.api.windowing.evictors.Evictor;
import org.apache.flink.streaming.api.windowing.windows.Window;
import org.apache.flink.streaming.runtime.operators.windowing.TimestampedValue;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Created by bruenni on 17.07.17.
 * if shallBeRemoved predicate returns true, will be removed.
 * Starts regarding of evictBefore flag.
 */
public class LambdaEvictor<T> implements Evictor<T, Window> {

    private Predicate<? super T> shallBeRemoved;
    private boolean evictBefore;

    /**
     * Constructor
     * @param shallBeRemoved predicate for remove?
     * @param evictBefore
     */
    public LambdaEvictor(Predicate<? super T> shallBeRemoved, boolean evictBefore) {
        this.shallBeRemoved = shallBeRemoved;
        this.evictBefore = evictBefore;
    }

    @Override
    public void evictBefore(Iterable<TimestampedValue<T>> elements, int size, Window window, EvictorContext evictorContext) {

        if (evictBefore)
        {
            evict(elements);
        }
    }

    private void evict(Iterable<TimestampedValue<T>> elements) {
        for (Iterator<TimestampedValue<T>> iterator = elements.iterator(); iterator.hasNext();)
        {
            iterator.next();
            if (this.shallBeRemoved.test(iterator.next().getValue()))
            {
                iterator.remove();
            }
        }
    }

    @Override
    public void evictAfter(Iterable<TimestampedValue<T>> elements, int size, Window window, EvictorContext evictorContext) {
        if (!evictBefore)
        {
            evict(elements);
        }
    }

    public static <T> Evictor<T, ? extends Window> of(Predicate<? super T> shallBeRemoved)
    {
        return new LambdaEvictor<>(shallBeRemoved, true);
    }
}
