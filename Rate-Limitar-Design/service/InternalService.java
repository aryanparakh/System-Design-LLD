package ratelimiter.service;

import ratelimiter.core.RateLimiter;

/**
 * InternalService — the business logic layer that conditionally calls the external resource.
 *
 * Key design point from the problem statement:
 *  "Not every API request consumes quota."
 *  "Rate limiting is applied only at the point where the system is about to
 *   call the external resource."
 *
 * So the pattern is:
 *   1. Run business logic.
 *   2. If external call is needed → consult RateLimiter.
 *   3. If allowed  → call ExternalResourceClient.
 *      If denied   → return a graceful rejection message.
 *
 * The service knows WHICH tenant/customer is making the request
 * but does NOT know which algorithm the RateLimiter uses internally.
 */
public class InternalService {

    private final RateLimiter rateLimiter;
    private final ExternalResourceClient externalClient;

    public InternalService(RateLimiter rateLimiter, ExternalResourceClient externalClient) {
        this.rateLimiter = rateLimiter;
        this.externalClient = externalClient;
    }

    /**
     * Handle an incoming API request.
     *
     * @param tenantId       Who is making the call.
     * @param needsExternal  Whether this request requires the external resource.
     * @param payload        Data to send to the external resource (if called).
     * @return               Result string.
     */
    public String handleRequest(String tenantId, boolean needsExternal, String payload) {
        System.out.printf("%n[InternalService] Request from '%s' | needsExternal=%b%n",
                tenantId, needsExternal);

        // Business logic runs first
        String businessResult = runBusinessLogic(tenantId, payload);

        if (!needsExternal) {
            // No external call needed — rate limiter is never consulted
            System.out.println("  → No external call needed. Skipping rate limit check.");
            return businessResult;
        }

        // Rate limiting check happens ONLY here, just before the external call
        if (!rateLimiter.isAllowed(tenantId)) {
            System.out.printf("  ❌ Rate limit exceeded for tenant '%s'. Request denied.%n", tenantId);
            return "RATE_LIMITED: Too many requests for tenant " + tenantId;
        }

        // Allowed — proceed with external call
        return externalClient.call(payload);
    }

    /** Simulated business logic — always succeeds, may or may not need external resource. */
    private String runBusinessLogic(String tenantId, String payload) {
        return "business_result_for_" + payload;
    }
}
