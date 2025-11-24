package web.website.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 번호 (1, 2, 3...)

    @Column(unique = true) // 아이디는 중복되면 안됨
    private String userId;

    private String password;
    private String name;
}