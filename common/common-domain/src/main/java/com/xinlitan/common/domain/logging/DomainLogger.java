package com.xinlitan.common.domain.logging;

public interface DomainLogger {
    void info(String domain, String message);
    void debug(String domain, String message);
    void warn(String domain, String message);
    void error(String domain, String message);
}
