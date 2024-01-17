package mu.tutorial.learnspringboot.repository.custom.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import mu.tutorial.learnspringboot.entity.QUser;
import mu.tutorial.learnspringboot.entity.User;
import mu.tutorial.learnspringboot.model.UserDto;
import mu.tutorial.learnspringboot.repository.custom.UserCustomRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    QUser user = QUser.user;

    @Override
    public List<UserDto> findAllUsersByEqualIgnoreCase(@NonNull String name) {
        JPAQuery<User> userJPAQuery = new JPAQuery<>(entityManager);

        return userJPAQuery.select(Projections.fields(UserDto.class,
                        user.id,
                        user.name,
                        user.username,
                        user.address))
                .from(user)
                .fetch();
    }
}
