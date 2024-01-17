package com.xinlitan.common.container.logging;

import com.xinlitan.common.domain.logging.DomainLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DomainLoggerImpl implements DomainLogger {
    private static final Logger logger = LoggerFactory.getLogger(DomainLoggerImpl.class);

    @Override
    public void info(String domain, String message) {
        logger.info("{} {}", domain, message);
    }

    @Override
    public void debug(String domain, String message) {
        logger.debug("{} {}", domain, message);
    }

    @Override
    public void warn(String domain, String message) {
        logger.warn("{} {}", domain, message);
    }

    @Override
    public void error(String domain, String message) {
        logger.error("{} {}", domain, message);
    }
}
