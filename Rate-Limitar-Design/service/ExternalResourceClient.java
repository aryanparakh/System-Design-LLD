package ratelimiter.service;

import ratelimiter.core.RateLimiter;

/**
 * ExternalResourceClient — simulates the actual paid external API call.
 *
 * In production this would be an HTTP client, SDK call, etc.
 * Here it just prints a message so the demo stays self-contained.
 *
 * This class knows nothing about rate limiting — it simply executes the call.
 * The decision of whether to call it lives in InternalService.
 */
public class ExternalResourceClient {

    private final String resourceName;

    public ExternalResourceClient(String resourceName) {
        this.resourceName = resourceName;
    }

    /** Simulate the actual external API invocation. */
    public String call(String payload) {
        System.out.printf("  [ExternalResource:%s] ✅ Called with payload='%s'%n",
                resourceName, payload);
        return "response_for_" + payload;
    }
}
