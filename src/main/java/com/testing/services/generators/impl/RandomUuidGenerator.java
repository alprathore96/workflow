package com.testing.services.generators.impl;

import com.testing.services.generators.Generator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RandomUuidGenerator implements Generator {
    private static final Logger LOGGER = Logger.getLogger(RandomUuidGenerator.class);
    @Override
    public Object generate() {
        LOGGER.info("generating uuid...");
        return UUID.randomUUID();
    }
}
