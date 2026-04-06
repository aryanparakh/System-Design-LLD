package ratelimiter.config;

import java.util.concurrent.TimeUnit;

/**
 * Immutable configuration for the rate limiter.
 *
 * Holds two values:
 *  - limit       : max number of calls allowed in the window.
 *  - windowSizeMs: the rolling/fixed window duration in milliseconds.
 *
 * Using a dedicated config object keeps the algorithm classes clean and
 * makes it easy to change limits at runtime by swapping configs.
 */
public class RateLimitConfig {

    private final int limit;
    private final long windowSizeMs;

    public RateLimitConfig(int limit, long windowSize, TimeUnit unit) {
        this.limit = limit;
        this.windowSizeMs = unit.toMillis(windowSize);
    }

    public int getLimit() {
        return limit;
    }

    public long getWindowSizeMs() {
        return windowSizeMs;
    }

    @Override
    public String toString() {
        return String.format("RateLimitConfig{limit=%d, windowMs=%d}", limit, windowSizeMs);
    }
}
