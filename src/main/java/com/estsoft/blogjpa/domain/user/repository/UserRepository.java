package com.estsoft.blogjpa.domain.user.repository;

import com.estsoft.blogjpa.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //Spring Data JPA는 메소드 규칙에 맞춰 메소드를 선언하면 이름을 분석해 자동으로 쿼리를 생성해 준다.
    /*
    SELECT *
    FROM users
    WHERE email = #{email}
     */
    Optional<User> findByEmail(String email); //email로 사용자의 정보를 가져온다
}
