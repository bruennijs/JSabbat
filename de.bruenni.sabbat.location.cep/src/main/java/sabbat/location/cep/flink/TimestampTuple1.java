package sabbat.location.cep.flink;

import org.apache.flink.api.java.tuple.Tuple1;

import java.time.Instant;

/**
 * Created by bruenni on 09.01.17.
 */
public class TimestampTuple1<T> extends Tuple1<Instant> {
  private T t1;

  public TimestampTuple1(T value, Instant value0) {
    super(value0);
    this.t1 = value;
  }

  public T getT1() {
    return t1;
  }
}
