package tjv.server.service;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class AbstractService<E, K, R extends CrudRepository<E,K>> {
    protected final R repository;
    public AbstractService(R repository) {
        this.repository = repository;
    }

    public E create(E entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        return repository.save(entity);
    }

    public Optional<E> findById(K id) {
        return repository.findById(id);
    }

    public void deleteById(K id) {
        repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Record not found"));
        repository.deleteById(id);
    }

    public Iterable<E> findAll() {
        return repository.findAll();
    }
}
