package sabbat.location.cep.test.flink;

import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import rx.Observable;
import rx.Observer;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruenni on 11.01.17.
 */
public class RxSink<IN> extends RichSinkFunction<IN> implements Serializable {

  private static final long serialVersionUID = 1L;

  private transient rx.subjects.Subject<IN, IN> subject;


  @Override
  public void invoke(IN in) throws Exception {
    subject.onNext(in);
  }


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        this.subject = ReplaySubject.create();
    }

    /**
   * Returns observable without any subscribers
   * @return
   */
  public Observable<IN> getObservable() {
      return subject;
  }

  /**
   * Takes and timeout after x seconds
   * @param takeCount
   * @return
   */
  public Observable<IN> getObservable(int takeCount)
  {
    return getObservable()
            .take(takeCount)
            .timeout(5, TimeUnit.SECONDS);
  }
}
