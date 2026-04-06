classDiagram
    direction TB

    class RateLimitAlgorithm {
        <<interface>>
        +isAllowed(key String) boolean
    }

    class FixedWindowAlgorithm {
        -RateLimitConfig config
        -ConcurrentHashMap~String,WindowState~ windowMap
        +isAllowed(key String) boolean
    }

    class SlidingWindowAlgorithm {
        -RateLimitConfig config
        -ConcurrentHashMap~String,Deque~ requestLog
        +isAllowed(key String) boolean
    }

    RateLimitAlgorithm <|.. FixedWindowAlgorithm
    RateLimitAlgorithm <|.. SlidingWindowAlgorithm

    class RateLimitConfig {
        -int limit
        -long windowSizeMs
        +getLimit() int
        +getWindowSizeMs() long
    }

    FixedWindowAlgorithm --> RateLimitConfig : configured by
    SlidingWindowAlgorithm --> RateLimitConfig : configured by

    class KeyResolver {
        <<interface>>
        +resolve(context String) String
    }

    class TenantKeyResolver {
        +resolve(context String) String
    }

    class CustomerKeyResolver {
        +resolve(context String) String
    }

    KeyResolver <|.. TenantKeyResolver
    KeyResolver <|.. CustomerKeyResolver

    class RateLimiter {
        -RateLimitAlgorithm algorithm
        -KeyResolver keyResolver
        +isAllowed(context String) boolean
        +withAlgorithm(algorithm RateLimitAlgorithm) RateLimiter
    }

    RateLimiter --> RateLimitAlgorithm : delegates decision
    RateLimiter --> KeyResolver : resolves key via

    class ExternalResourceClient {
        -String resourceName
        +call(payload String) String
    }

    class InternalService {
        -RateLimiter rateLimiter
        -ExternalResourceClient externalClient
        +handleRequest(tenantId String, needsExternal boolean, payload String) String
    }

    InternalService --> RateLimiter : consults before external call
    InternalService --> ExternalResourceClient : calls if allowed
