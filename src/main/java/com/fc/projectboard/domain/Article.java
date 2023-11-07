package com.fc.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true) // super Class 까지 들어가서 toString 찍는다는 설정
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
}) // 위의 인덱스로 빠른 서칭 가능하도 록 함.
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자동으로 부여하는 값으로 사용자가 수정 하지 못하도록 한다. (JPA Persistence Context 가 영속화를 할때 자동으로 부여해주는 고유번호)

    @Setter
    @ManyToOne(optional = false)
    private UserAccount userAccount; // 유저 정보 (ID)

    @Setter
    @Column(nullable = false)
    private String title; // 제목

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 본문

    // 옵셔널
    @Setter
    private String hashtag; // 해시태그

    @OrderBy("createdAt DESC") // 시간순정렬
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude // cascade 결합도가 높기 때문에(양방향 바인딩), 운영시 키 설정 하지 않는다. (순환참조때문에, toString 을 모든 인스턴스에서 실행하기 때문에
    private final Set<ArticleComment> articleCommentSet = new LinkedHashSet<>();

    protected Article() {
    }

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
