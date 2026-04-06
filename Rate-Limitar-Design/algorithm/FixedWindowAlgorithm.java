package ratelimiter.algorithm;

import ratelimiter.config.RateLimitConfig;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Fixed Window Counter algorithm.
 *
 * How it works:
 *  - Time is divided into fixed windows (e.g. 0–60s, 60–120s …).
 *  - Each key gets a counter that resets at the start of every window.
 *  - If the counter is below the limit, the request is allowed and the counter increments.
 *
 * Trade-offs vs Sliding Window:
 *  + Very memory-efficient (one counter + one timestamp per key).
 *  + Simple and fast.
 *  - Burst problem: a client can double the allowed rate at a window boundary
 *    (e.g., 100 calls in the last second of window N and 100 in the first second of N+1).
 *
 * Thread-Safety:
 *  - ConcurrentHashMap ensures safe concurrent key creation.
 *  - AtomicInteger.incrementAndGet() ensures atomic counter updates.
 *  - Window reset uses synchronized to prevent a race on the (counter + timestamp) pair.
 *
 * Design Pattern: Strategy (implements RateLimitAlgorithm)
 */
public class FixedWindowAlgorithm implements RateLimitAlgorithm {

    private final RateLimitConfig config;

    // Holds the state for a single key's current window.
    private static class WindowState {
        AtomicInteger count = new AtomicInteger(0);
        long windowStart;

        WindowState(long windowStart) {
            this.windowStart = windowStart;
        }
    }

    private final ConcurrentHashMap<String, WindowState> windowMap;

    public FixedWindowAlgorithm(RateLimitConfig config) {
        this.config = config;
        this.windowMap = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isAllowed(String key) {
        long now = System.currentTimeMillis();

        // computeIfAbsent is atomic — safe initial window creation
        WindowState state = windowMap.computeIfAbsent(key, k -> new WindowState(now));

        // Synchronized on state to atomically check + reset the window
        synchronized (state) {
            if (now - state.windowStart >= config.getWindowSizeMs()) {
                // Window has expired — reset counter and start fresh
                state.windowStart = now;
                state.count.set(0);
            }

            int currentCount = state.count.incrementAndGet();
            return currentCount <= config.getLimit();
        }
    }
}
