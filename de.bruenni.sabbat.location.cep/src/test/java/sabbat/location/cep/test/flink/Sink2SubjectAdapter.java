package sabbat.location.cep.test.flink;

import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import rx.Observable;
import rx.Observer;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

/**
 * Created by bruenni on 11.01.17.
 */
public class Sink2SubjectAdapter<IN> implements SinkFunction<IN> {

  private rx.subjects.Subject<IN, IN> subject = ReplaySubject.create();

  @Override
  public void invoke(IN in) throws Exception {
    subject.onNext(in);
  }

  /**
   *
   * @return
   */
  public Observable<IN> getObservable() {
      return subject;
  }
}
