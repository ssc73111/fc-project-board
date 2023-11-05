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
        @Index(columnList = "createBy")
}) // 위의 인덱스로 빠른 서칭 가능하도록 함.
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자동으로 부여하는 값으로 사용자가 수정 하지 못하도록 한다. (JPA Persistence Context 가 영속화를 할때 자동으로 부여해주는 고유번호)

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

//    // 메타데이터는 자동 세팅되므로 Setter 를 사용하지 않음.
//    @CreatedDate
//    @Column(nullable = false)
//    private LocalDateTime createdAt; // 생성일시
//    @CreatedBy
//    @Column(nullable = false, length = 100)
//    private String createBy; // 생성자
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modifiedAt; // 수정일시
//    @LastModifiedBy
//    @Column(nullable = false, length = 100)
//    private String modifiedBy; // 수정자

    protected Article() {
    }

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
