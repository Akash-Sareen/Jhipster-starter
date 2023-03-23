package com.sky.myapp.service;

import com.sky.myapp.domain.Channels;
import com.sky.myapp.repository.ChannelsRepository;
import com.sky.myapp.security.TenantContext;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Channels}.
 */
@Service
@Transactional
public class ChannelsService {

    private final Logger log = LoggerFactory.getLogger(ChannelsService.class);

    private final ChannelsRepository channelsRepository;

    private final MongoTemplate mongoTemplate;

    public ChannelsService(ChannelsRepository channelsRepository, MongoTemplate mongoTemplate) {
        this.channelsRepository = channelsRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Save a channels.
     *
     * @param channels the entity to save.
     * @return the persisted entity.
     */
    public Channels save(Channels channels) {
        log.debug("Request to save Channels : {}", channels);
        return channelsRepository.save(channels);
    }

    /**
     * Get all the channels.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Channels> findAll() {
        log.debug("Request to get all Channels");
        Query query = new Query();
        query.addCriteria(Criteria.where("company.id").is(TenantContext.getCompanyId()));
        List<Channels> channels = mongoTemplate.find(query, Channels.class);
        return channels;
    }

    /**
     * Get one channels by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public List<Channels> findOne(String id) {
        log.debug("Request to get Channels : {}", id);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        // TODO: Add criteria for accountId
        return mongoTemplate.find(query, Channels.class);
    }

    /**
     * Delete the channels by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Channels : {}", id);
        channelsRepository.deleteById(id);
    }
}
