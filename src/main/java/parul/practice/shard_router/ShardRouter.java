package parul.practice.shard_router;


import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ShardRouter {
    // given a key - route to a node

    Map<Integer, List<Node>> shardToNodes; // first node is the primary
    final int numShard = 3;
    Map<Integer, Shard> shardMap;

    public ShardRouter( Map<Integer, List<Node>> shardToNodes, Map<Integer, Shard> shardMap){
        this.shardToNodes = shardToNodes;
        this.shardMap = shardMap;
    }


    public int getShardID(String key){
        int shardId = Hashing.murmur3_32_fixed().hashString(key, StandardCharsets.UTF_8).asInt();
        shardId = (shardId & Integer.MAX_VALUE) % numShard;
        return shardId;

    }

    public Node writeToShard(String key, String value){
        int shardid = getShardID(key);
        List<Node> nodes = shardToNodes.get(shardid);
        if(nodes == null || nodes.isEmpty()){
            throw new IllegalStateException("No nodes for shard: " + shardid);
        }
        Node node = nodes.get(0);
        node.writeRequest(key, value);
        return nodes.get(0);
    }

    public Node readFromShard(String key){

        int shardId = getShardID(key);
        Shard shard = shardMap.get(shardId);
        List<Node> nodes = shardToNodes.get(shardId);
        if(nodes == null || nodes.isEmpty()){
            throw new IllegalStateException("No nodes for shard: " + shardId);
        }
        // This also reads from primary (index 0) in the rotation, which is fine
        // If you want replicas only: % (nodes.size() - 1) + 1
        int idx = shard.readCounter.incrementAndGet() % nodes.size();

        Node node = nodes.get(idx);
        node.readRequest(key);
        return node;
    }


}
