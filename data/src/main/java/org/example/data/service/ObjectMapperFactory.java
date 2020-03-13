package org.example.data.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.TimeZone;

public enum ObjectMapperFactory {
    /**
     * objectmapper
     */


    OBJECTMAPPER {
        @Override
        public ObjectMapper getInstance() {
            return super.getInstance().registerModule(new JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    },
    OBJECTMAPPERAWALYS {
        @Override
        public ObjectMapper getInstance() {
            return super.getInstance().registerModule(new JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .setSerializationInclusion(JsonInclude.Include.ALWAYS);
        }
    },
    OBJECTMAPPERSORT {
        @Override
        public ObjectMapper getInstance() {
            return super.getInstance().registerModule(new JodaModule())
                    .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMapperFactory.class);
    private ObjectMapper objectMapper;

    ObjectMapperFactory() {
        this.objectMapper = new ObjectMapper().setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    public ObjectMapper getInstance() {
        return this.objectMapper;
    }

    public <T> T strToObject(String str, Class<T> tClass) {
        try {
            return getInstance().readValue(str, tClass);
        } catch (IOException e) {
            LOGGER.error("strToObj error {}", e);
            return null;
        }
    }

    public <T> T strToObject(String str, TypeReference valueTypeRef) {
        try {
            return getInstance().readValue(str, valueTypeRef);
        } catch (IOException e) {
            LOGGER.error("strToObj error {}", e);
            return null;
        }
    }

    public <T> Optional<String> objectToStrO(T value) {
        try {
            return Optional.of(getInstance().writeValueAsString(value));
        } catch (JsonProcessingException e) {
            LOGGER.error("strToObj error {}", e);
            return Optional.empty();
        }
    }

    public <T> String objectToStr(T value) {
        try {
            return getInstance().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            LOGGER.error("strToObj error {}", e);
            return "{}";
        }
    }
}
