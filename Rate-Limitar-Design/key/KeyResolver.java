package ratelimiter.key;

/**
 * Strategy interface for generating rate-limiting keys.
 *
 * Decouples key generation from the rate limiter itself.
 * Different implementations can key by tenant, customer, API key, etc.
 *
 * Design Pattern: Strategy
 *
 * Example implementations:
 *  - TenantKeyResolver   → "tenant:acme-corp"
 *  - CustomerKeyResolver → "customer:user-42"
 *  - ApiKeyResolver      → "apikey:abc123"
 */
public interface KeyResolver {

    /**
     * Derive the rate-limiting key from the given context.
     *
     * @param context An arbitrary string carrying identity info (tenant ID,
     *                customer ID, API key, etc.). The resolver decides what to
     *                extract or prefix.
     * @return The key under which quota is tracked.
     */
    String resolve(String context);
}
