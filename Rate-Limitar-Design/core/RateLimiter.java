package ratelimiter.core;

import ratelimiter.algorithm.RateLimitAlgorithm;
import ratelimiter.key.KeyResolver;

/**
 * RateLimiter — the central facade of the system.
 *
 * Responsibilities:
 *  - Resolves the rate-limiting key via the injected KeyResolver.
 *  - Delegates the allow/deny decision to the injected RateLimitAlgorithm.
 *  - Exposes a single simple method: isAllowed(context).
 *
 * Both the algorithm and key resolver are pluggable via constructor injection:
 *  - Swap Fixed Window ↔ Sliding Window without touching business logic.
 *  - Swap TenantKeyResolver ↔ CustomerKeyResolver independently.
 *
 * Design Patterns:
 *  - Facade:   Single, simple API for internal services.
 *  - Strategy: Algorithm and key resolver are both pluggable strategies.
 *
 * Thread-Safety:
 *  - This class itself is stateless (no mutable fields).
 *  - Thread-safety is entirely delegated to the algorithm implementations.
 */
public class RateLimiter {

    private final RateLimitAlgorithm algorithm;
    private final KeyResolver keyResolver;

    public RateLimiter(RateLimitAlgorithm algorithm, KeyResolver keyResolver) {
        this.algorithm = algorithm;
        this.keyResolver = keyResolver;
    }

    /**
     * Decide whether an external resource call is allowed.
     *
     * @param context  Identity context (tenant ID, customer ID, API key, …).
     * @return         true → call is allowed; false → quota exhausted, call denied.
     */
    public boolean isAllowed(String context) {
        String key = keyResolver.resolve(context);
        return algorithm.isAllowed(key);
    }

    /**
     * Allow hot-swapping the algorithm at runtime without rebuilding the limiter.
     * Useful for gradual rollouts (e.g., move from Fixed to Sliding Window).
     */
    public RateLimiter withAlgorithm(RateLimitAlgorithm newAlgorithm) {
        return new RateLimiter(newAlgorithm, this.keyResolver);
    }
}
