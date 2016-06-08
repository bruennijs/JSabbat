package domain.aggregates;

/**
 * Created by bruenni on 08.06.16.
 */
public class Entity<T> {
    private T id;

    public Entity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
