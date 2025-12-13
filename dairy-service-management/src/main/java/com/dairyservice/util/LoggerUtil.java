package com.dairyservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggerUtil {

    public void logInfo(String message) {
        log.info(message);
    }

    public void logError(String message, Exception e) {
        log.error(message, e);
    }

    public void logDebug(String message) {
        log.debug(message);
    }

    public void logWarn(String message) {
        log.warn(message);
    }
}
