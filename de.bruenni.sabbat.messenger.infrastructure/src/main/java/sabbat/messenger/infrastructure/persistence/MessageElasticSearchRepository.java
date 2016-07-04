package sabbat.messenger.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;
import sabbat.messenger.core.domain.messenger.aggregates.Message;

import java.util.UUID;

/**
 * Created by bruenni on 21.06.16.
 */
public class MessageElasticSearchRepository implements CrudRepository<Message, UUID> {


    @Override
    public <S extends Message> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Message> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public Message findOne(UUID uuid) {
        return null;
    }

    @Override
    public boolean exists(UUID uuid) {
        return false;
    }

    @Override
    public Iterable<Message> findAll() {
        return null;
    }

    @Override
    public Iterable<Message> findAll(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public void delete(Message entity) {

    }

    @Override
    public void delete(Iterable<? extends Message> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
