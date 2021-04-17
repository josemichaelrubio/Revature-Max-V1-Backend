package com.revaturemax.repositories;

import com.revaturemax.models.TopicTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicTagRepository extends JpaRepository<TopicTag, Long> {
    TopicTag findByName(String name);
}
