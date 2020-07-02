package sh.platform.micronaut;

import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Singleton
public class HeroService {

    private final EntityManager entityManager;

    public HeroService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @ReadOnly
    public Optional<Hero> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Hero.class, id));
    }

    @Transactional
    public Hero save(Hero hero) {
        entityManager.persist(hero);
        return hero;
    }

    @ReadOnly
    public List<Hero> findAll() {
        TypedQuery<Hero> query = entityManager.createQuery("SELECT h FROM Hero as h order by h.name", Hero.class);
        return query.getResultList();
    }

    @Transactional
    public void deleteById(@NotNull Long id) {
        findById(id).ifPresent(entityManager::remove);
    }

}
