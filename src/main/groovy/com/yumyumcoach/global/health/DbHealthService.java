package com.yumyumcoach.global.health;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DbHealthService {
    private final DbHealthMapper dbHealthMapper;
    public boolean isDbUp() {
        try {
            String now = dbHealthMapper.selectNow();
            return now != null;
        } catch (Exception e) {
            log.error("DB health check failed", e);
            return false;
        }
    }
}
