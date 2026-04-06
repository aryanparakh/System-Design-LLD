package ratelimiter.algorithm;

import ratelimiter.config.RateLimitConfig;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Sliding Window Counter algorithm.
 *
 * How it works:
 *  - Keeps a queue of timestamps for each allowed request.
 *  - On every call, prunes timestamps older than (now - windowSize).
 *  - If the remaining (live) count is below the limit, the request is allowed.
 *
 * Trade-offs vs Fixed Window:
 *  + No burst problem — the window truly "slides" with time.
 *  + Much fairer distribution of allowed requests.
 *  - Higher memory use: O(limit) timestamps per key (bounded by limit, not unbounded).
 *  - Slightly slower due to queue pruning, but still O(limit) worst-case per call.
 *
 * Thread-Safety:
 *  - ConcurrentHashMap for safe concurrent key creation.
 *  - Each key's Deque is synchronized individually for fine-grained locking.
 *    Locking per-key (not globally) keeps concurrency high across different keys.
 *
 * Design Pattern: Strategy (implements RateLimitAlgorithm)
 */
public class SlidingWindowAlgorithm implements RateLimitAlgorithm {

    private final RateLimitConfig config;

    // Each key owns a deque of timestamps of allowed requests
    private final ConcurrentHashMap<String, Deque<Long>> requestLog;

    public SlidingWindowAlgorithm(RateLimitConfig config) {
        this.config = config;
        this.requestLog = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isAllowed(String key) {
        long now = System.currentTimeMillis();
        long windowStart = now - config.getWindowSizeMs();

        // computeIfAbsent is atomic — safe initial deque creation
        Deque<Long> timestamps = requestLog.computeIfAbsent(key, k -> new ArrayDeque<>());

        // Lock on the deque (per-key lock) — minimises contention across keys
        synchronized (timestamps) {
            // Prune timestamps that have fallen outside the sliding window
            while (!timestamps.isEmpty() && timestamps.peekFirst() <= windowStart) {
                timestamps.pollFirst();
            }

            if (timestamps.size() < config.getLimit()) {
                timestamps.addLast(now);   // record this allowed request
                return true;
            }

            return false;   // quota exhausted for this window
        }
    }
}
