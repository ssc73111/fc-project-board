package com.fc.projectboard.service;

import com.fc.projectboard.domain.Hashtag;
import com.fc.projectboard.repository.HashtagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    public HashtagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    public Set<String> parseHashtagNames(String content) {
        if(content == null) {
            return Set.of();
        }

        Pattern pattern = Pattern.compile("#[\\w가-힣]+");
        Matcher matcher= pattern.matcher(content.strip());
        Set<String> result = new HashSet<>();

        while (matcher.find()) {
            result.add(matcher.group().replace("#", ""));
        }
        return Set.copyOf(result);
    }

    @Transactional(readOnly = true)
    public Set<Hashtag> findHashtagsByNames(Set<String> expectedHashtagNames) {
        return new HashSet<>(hashtagRepository.findByHashtagNameIn(expectedHashtagNames));
    }

    public void deleteHashtagWithoutArticles(Long hashtagId) {
        Hashtag hashtag = hashtagRepository.getReferenceById(hashtagId);
        if(hashtag.getArticles().isEmpty()) {
            hashtagRepository.delete(hashtag);
        }

    }
}
