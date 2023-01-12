package com.delver.board.domain.post;

import com.delver.board.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String category;

    private LocalDateTime createDate;

    /**
     * 연관 관계 매서드
     */
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    /**
     * 생성 매서드
     */
    @Builder(builderClassName = "createPost", builderMethodName = "createPost")
    public Post(String title, String content, Member member, String category) {
        Assert.hasText(title, "글 이름이 없습니다.");
        Assert.hasText(content, "글 내용이 없습니다.");
        Assert.notNull(member, "글쓴이가 없습니다.");
        Assert.hasText(category, "카테고리가 없습니다.");

        this.title = title;
        this.content = content;
        setMember(member);
        this.category = category;
        this.createDate = LocalDateTime.now();
    }
    /**
     * 비즈니스 로직
     */
    public void update(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }





}
