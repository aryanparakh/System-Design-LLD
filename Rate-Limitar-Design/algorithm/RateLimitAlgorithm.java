package ratelimiter.algorithm;

/**
 * Strategy interface for rate limiting algorithms.
 *
 * Each implementation encapsulates a specific algorithm (Fixed Window,
 * Sliding Window, Token Bucket, Leaky Bucket, etc.).
 *
 * Design Pattern: Strategy
 *
 * All implementations must be thread-safe, as multiple threads may
 * consult the limiter concurrently for the same key.
 */
public interface RateLimitAlgorithm {

    /**
     * Attempt to consume one unit of quota for the given key.
     *
     * @param key   The rate-limiting key (tenant ID, customer ID, API key, …).
     * @return      true  → request is allowed (quota available).
     *              false → request is denied (quota exhausted).
     */
    boolean isAllowed(String key);
}
