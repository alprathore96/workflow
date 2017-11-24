package com.testing.config;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheLibraryConfig {
    private static final Logger LOGGER = Logger.getLogger(CacheLibraryConfig.class);

    @Cacheable("classData")
    public Class forName(String path) throws ClassNotFoundException {
        LOGGER.debug(String.format("Getting class for path %s.", path));
        return Class.forName(path);
    }
}
