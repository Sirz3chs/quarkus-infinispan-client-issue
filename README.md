# quarkus-infinispan-client-issue

Small reproduction of two issues:
- when using `@CacheResult` annotation on methods returning `unis`.
- with quarkus dev only, we can't load infinispan configuration from `configuration-uri`
