package parul.practice.shard_router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShardRouterTest {
    public static void main(String[] args) {
        // 3 shards, 3 nodes, 1 primary + 1 replica each

        Shard s1 = new Shard(0, 1);
        Shard s2 = new Shard(1, 1);
        Shard s3 = new Shard(2, 1);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);

        Map<Integer, List<Node>> shardToNodes = new HashMap<>(); // first node is the primary
        Map<Integer, Shard> shardMap = new HashMap<>();

        shardMap.put(0, s1);
        shardMap.put(1, s2);
        shardMap.put(2, s3);



        List<Node> s1Nodes = new ArrayList<>();
        s1Nodes.add(n1);
        s1Nodes.add(n2);

        shardToNodes.put(0, s1Nodes);

        List<Node> s2Nodes = new ArrayList<>();
        s2Nodes.add(n2);
        s2Nodes.add(n3);

        shardToNodes.put(1, s2Nodes);

        List<Node> s3Nodes = new ArrayList<>();
        s3Nodes.add(n3);
        s3Nodes.add(n1);

        shardToNodes.put(2, s3Nodes);

        ShardRouter router = new ShardRouter(shardToNodes, shardMap);

        router.writeToShard("name", "April");
        router.writeToShard("location", "Delhi");
        router.writeToShard("age", "21");
        router.writeToShard("hobby", "music");
        router.writeToShard("movie", "DDLJ");
        router.writeToShard("cartoon", "Doremon");
        router.writeToShard("color", "orange");
        router.writeToShard("sweet", "kaju katli");
        router.writeToShard("book", "The code");
        router.writeToShard("game", "sudoku");
        router.writeToShard("chocolate", "Lindt");
        router.writeToShard("series", "Friends");


        for(int i =0;i<10;i++){
            router.readFromShard("location");
        }

        router.readFromShard("name");
        router.readFromShard("age");

        router.readFromShard("hobby");
        router.readFromShard("movie");
        router.readFromShard("cartoon");
        router.readFromShard("color");
        router.readFromShard("sweet");
        router.readFromShard("book");
        router.readFromShard("game");
        router.readFromShard("chocolate");
        router.readFromShard("series");







    }
}