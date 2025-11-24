package web.website.repository;

import web.website.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 아이디로 회원 정보 찾기 (로그인할 때, 중복 체크할 때 사용)
    Optional<User> findByUserId(String userId);
}
