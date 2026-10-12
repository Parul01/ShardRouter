# ShardRouter

### Problem Statement
- build a simplified version of shard router akin to what elasticsearch has

### distribution
- 3 nodes
- 3 shard (0-2)
- 2 copies (1 primary 1 replica)
- set of keys that are written to shards based on our routing hash 
- hash used Murmur

```
Hashing.murmur3_32_fixed().hashString(key, StandardCharsets.UTF_8).asInt();

```
- routing
```
key → shard: floorMod(murmur3(key), numShards). Fixed once the index exists.
shard → node: lookup in the cluster state map. Writes go to the primary (index 0), reads round-robin across copies.
```
- reason to choose Murmur over java HashCode()- it is fast, uniformly redistributes keys
- String.hashCode() spreads similar keys poorly. Murmur3 mixes the bits well, and it’s what ES, Cassandra use, so the same key routes the same way on any client.

### Simplified vs. Elasticsearch
1. Hash input encoding: ES hashes the routing string as UTF-16 code units;
   this uses UTF-8, so shard IDs won't match real ES for the same key.
2. No routing_num_shards / routing factor: ES computes
   floorMod(hash, routingNumShards) / routingFactor so indices can be _split.
   Here it's floorMod(hash, numShards) only.
3. Static cluster state: ES's master maintains and publishes ClusterState;
   here shardToNodes is hardcoded at startup.
4. No shard allocation/rebalancing: ES uses BalancedShardsAllocator +
   allocation deciders (disk watermarks, awareness, same-shard rule).
5. No failure handling: isAlive exists but dead nodes aren't skipped.
6. No primary terms / sequence numbers: ES uses primary term as a fencing
   token to reject writes from a stale primary.
7. Reads use simple round-robin; ES uses Adaptive Replica Selection (ARS).
8. numShard is hardcoded, not derived from or validated against shardToNodes.


### Sample output
```

ShardRouter
Problem Statement
build a simplified version of shard router akin to what elasticsearch has
distribution
3 nodes
3 shard (0-2)
2 copies (1 primary 1 replica)
set of keys that are written to shards based on our routing hash
hash used Murmur
Hashing.murmur3_32_fixed().hashString(key, StandardCharsets.UTF_8).asInt();

routing
key → shard: floorMod(murmur3(key), numShards). Fixed once the index exists.
shard → node: lookup in the cluster state map. Writes go to the primary (index 0), reads round-robin across copies.
reason to choose Murmur over java HashCode()- it is fast, uniformly redistributes keys
String.hashCode() spreads similar keys poorly. Murmur3 mixes the bits well, and it’s what ES, Cassandra use, so the same key routes the same way on any client.
Simplified vs. Elasticsearch
Hash input encoding: ES hashes the routing string as UTF-16 code units; this uses UTF-8, so shard IDs won't match real ES for the same key.
No routing_num_shards / routing factor: ES computes floorMod(hash, routingNumShards) / routingFactor so indices can be _split. Here it's floorMod(hash, numShards) only.
Static cluster state: ES's master maintains and publishes ClusterState; here shardToNodes is hardcoded at startup.
No shard allocation/rebalancing: ES uses BalancedShardsAllocator + allocation deciders (disk watermarks, awareness, same-shard rule).
No failure handling: isAlive exists but dead nodes aren't skipped.
No primary terms / sequence numbers: ES uses primary term as a fencing token to reject writes from a stale primary.
Reads use simple round-robin; ES uses Adaptive Replica Selection (ARS).
numShard is hardcoded, not derived from or validated against shardToNodes.

```
