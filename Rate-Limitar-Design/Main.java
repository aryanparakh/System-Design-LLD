package ratelimiter;

import ratelimiter.algorithm.FixedWindowAlgorithm;
import ratelimiter.algorithm.SlidingWindowAlgorithm;
import ratelimiter.config.RateLimitConfig;
import ratelimiter.core.RateLimiter;
import ratelimiter.key.CustomerKeyResolver;
import ratelimiter.key.TenantKeyResolver;
import ratelimiter.service.ExternalResourceClient;
import ratelimiter.service.InternalService;

import java.util.concurrent.TimeUnit;

/**
 * Main entry point — demonstrates the Pluggable Rate Limiting System.
 *
 * Scenario 1: Fixed Window — tenant T1 allowed 3 calls/minute
 *  - First 3 calls → allowed
 *  - Call 4         → denied
 *  - Call with needsExternal=false → skips rate limiter entirely
 *
 * Scenario 2: Switch to Sliding Window — same tenant, same limit
 *  - Shows algorithm swap without changing InternalService at all.
 *
 * Scenario 3: Per-Customer key resolver
 *  - Two different customers share the same limit independently.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        ExternalResourceClient externalClient = new ExternalResourceClient("PaidDataAPI");

        // ─────────────────────────────────────────────────────────────
        // SCENARIO 1: Fixed Window — 3 calls per minute, keyed by tenant
        // ─────────────────────────────────────────────────────────────
        banner("Scenario 1: Fixed Window Counter (limit=3 per minute, keyed by tenant)");

        RateLimitConfig config = new RateLimitConfig(3, 1, TimeUnit.MINUTES);

        RateLimiter fixedWindowLimiter = new RateLimiter(
                new FixedWindowAlgorithm(config),
                new TenantKeyResolver()
        );

        InternalService service = new InternalService(fixedWindowLimiter, externalClient);

        // These 3 should be allowed
        service.handleRequest("T1", true,  "req-A");
        service.handleRequest("T1", true,  "req-B");
        service.handleRequest("T1", true,  "req-C");

        // 4th call → denied
        service.handleRequest("T1", true,  "req-D");

        // No external call needed — rate limiter is never touched
        service.handleRequest("T1", false, "req-E");

        // ─────────────────────────────────────────────────────────────
        // SCENARIO 2: Swap to Sliding Window — zero changes to InternalService
        // ─────────────────────────────────────────────────────────────
        banner("Scenario 2: Switching to Sliding Window (same limit=3 per minute)");

        RateLimiter slidingWindowLimiter = new RateLimiter(
                new SlidingWindowAlgorithm(config),   // ← only this line changes
                new TenantKeyResolver()
        );

        InternalService serviceWithSliding = new InternalService(slidingWindowLimiter, externalClient);

        serviceWithSliding.handleRequest("T1", true, "req-F");
        serviceWithSliding.handleRequest("T1", true, "req-G");
        serviceWithSliding.handleRequest("T1", true, "req-H");
        serviceWithSliding.handleRequest("T1", true, "req-I");   // denied

        // ─────────────────────────────────────────────────────────────
        // SCENARIO 3: Per-Customer key resolver — each customer is independent
        // ─────────────────────────────────────────────────────────────
        banner("Scenario 3: Per-Customer limiter — customer-A and customer-B independent");

        RateLimitConfig tightConfig = new RateLimitConfig(2, 1, TimeUnit.MINUTES);

        RateLimiter customerLimiter = new RateLimiter(
                new FixedWindowAlgorithm(tightConfig),
                new CustomerKeyResolver()             // ← different resolver
        );

        InternalService customerService = new InternalService(customerLimiter, externalClient);

        // customer-A uses up their 2-call quota
        customerService.handleRequest("customer-A", true, "A-req-1");
        customerService.handleRequest("customer-A", true, "A-req-2");
        customerService.handleRequest("customer-A", true, "A-req-3");   // denied

        // customer-B has their own independent quota — not affected
        customerService.handleRequest("customer-B", true, "B-req-1");
        customerService.handleRequest("customer-B", true, "B-req-2");

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║            DEMO COMPLETE                  ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    private static void banner(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.printf( "║  %-56s  ║%n", title);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
}
