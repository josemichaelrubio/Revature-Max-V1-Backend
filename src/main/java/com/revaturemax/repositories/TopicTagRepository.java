package com.revaturemax.repositories;

import com.revaturemax.model.TopicTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicTagRepository extends JpaRepository<TopicTag, Long> {
    TopicTag findByName(String name);
}
