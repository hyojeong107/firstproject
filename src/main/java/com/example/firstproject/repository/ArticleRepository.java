package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
//ArticleRepository는 CrudRepository를 상속 받음
// CrudRepository: interface, 이 안에 save 있음

@Repository
public interface ArticleRepository
//CrudRepository를 상속 받았기 때문에 @Repository 어노테이션이 없어도 정상적으로 동작
        extends CrudRepository<Article, Long> {
}
