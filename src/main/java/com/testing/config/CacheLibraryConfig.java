package com.testing.config;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CacheLibraryConfig {
    private static final Logger LOGGER = Logger.getLogger(CacheLibraryConfig.class);

    @Cacheable("classData")
    public Class forName(String path) throws ClassNotFoundException {
        LOGGER.debug(String.format("Getting class for path %s.", path));
        return Class.forName(path);
    }

    @Cacheable("methodFromClass")
    public Optional<Method> methodFromClass(Class clazz, String methodName) {
        return Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.getName()
                .equals(methodName)).findAny();
    }
}
