package sabbat.location.cep.test.flink;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import rx.Observable;
import rx.Observer;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

/**
 * Created by bruenni on 11.01.17.
 */
public class Sink2SubjectAdapter<IN> extends RichSinkFunction<IN> {

  private transient rx.subjects.Subject<IN, IN> subject = ReplaySubject.create();

  @Override
  public void invoke(IN in) throws Exception {
    subject.onNext(in);
  }

  @Override
  public void open(Configuration parameters) throws Exception {
  }

  /**
   *
   * @return
   */
  public Observable<IN> getObservable() {
      return subject;
  }
}
