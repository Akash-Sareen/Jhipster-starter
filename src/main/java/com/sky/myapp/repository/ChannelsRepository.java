package com.sky.myapp.repository;

import com.sky.myapp.domain.Channels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelsRepository extends MongoRepository<Channels, Long> {}
