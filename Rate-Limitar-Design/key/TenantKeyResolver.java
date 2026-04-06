package ratelimiter.key;

/**
 * Resolves the rate-limiting key as a per-tenant identifier.
 *
 * Example: context = "acme-corp"  →  key = "tenant:acme-corp"
 *
 * Design Pattern: Strategy (implements KeyResolver)
 */
public class TenantKeyResolver implements KeyResolver {

    @Override
    public String resolve(String context) {
        return "tenant:" + context;
    }
}
