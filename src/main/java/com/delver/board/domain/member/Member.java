package com.delver.board.domain.member;

import com.delver.board.domain.post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String picture;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JoinRoot joinRoot; // LOCAL, GOOGLE, NAVER

    @Column(nullable = false)
    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();


    /**
     * 생성 매서드
     */
    @Builder(builderClassName = "createMember", builderMethodName = "createMember")
    public Member(String userName, String email, String picture, Role role, JoinRoot joinRoot) {
        Assert.hasText(userName, "이름이 없습니다.");
        Assert.hasText(email, "이메일이 없습니다.");
        Assert.hasText(picture, "사진이 없습니다.");
        Assert.notNull(role, "신분이 없습니다.");
        Assert.notNull(joinRoot, "가입 경로가 없습니다.");

        this.userName = userName;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.joinRoot = joinRoot;
        joinDate = LocalDateTime.now();
    }
    /**
     * 비즈니스 로직
     */
    public void update(String userName, String email, String picture) {
        this.userName = userName;
        this.email = email;
        this.picture = picture;
    }


}