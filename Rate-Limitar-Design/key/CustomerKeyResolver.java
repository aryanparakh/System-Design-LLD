package ratelimiter.key;

/**
 * Resolves the rate-limiting key as a per-customer identifier.
 *
 * Example: context = "user-42"  →  key = "customer:user-42"
 *
 * Design Pattern: Strategy (implements KeyResolver)
 */
public class CustomerKeyResolver implements KeyResolver {

    @Override
    public String resolve(String context) {
        return "customer:" + context;
    }
}
