package sabbat.location.cep.test.flink;

import java.time.Instant;

/**
 * Created by bruenni on 09.01.17.
 */
public class TimestampVector2<T1, T2> extends TimestampVector1<T1> {
  private T2 t2;

  public TimestampVector2(T1 value, T2 value2, Instant value0) {
    super(value, value0);
    this.t2 = value2;
  }

  public T2 getT2() {
    return t2;
  }
}
