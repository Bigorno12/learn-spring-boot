package mu.tutorial.learnspringboot.repository.custom.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import mu.tutorial.learnspringboot.entity.QUser;
import mu.tutorial.learnspringboot.entity.User;
import mu.tutorial.learnspringboot.repository.custom.UserCustomRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<User> findAllUsersByEqualIgnoreCase(@NonNull String name) {
        QUser user = QUser.user;

        return new JPAQuery<User>(entityManager)
                .select(user.id, user.name, user.username,user.address)
                .from(user)
                .where(user.name.equalsIgnoreCase(name))
                .fetch()
                .stream().map(tuple -> getBuild(tuple, user))
                .toList();
    }

    private static User getBuild(Tuple tuple, QUser user) {
        return User.builder()
                .id(tuple.get(user.id))
                .name(tuple.get(user.name))
                .username(tuple.get(user.username))
                .address(tuple.get(user.address))
                .build();
    }
}
