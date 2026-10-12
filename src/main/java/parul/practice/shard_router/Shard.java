package parul.practice.shard_router;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Shard {

//    when you hash key - you will get a shard
    int id;
    int replicas;
//    List<Shard> replicaList;
    public Shard(int id, int replicas){
        this.id = id;
        this.replicas = replicas;
//        replicaList = new ArrayList<>();
//        for(replicas--){
//            replicaList.add
//        }
    }
    boolean readFlag = true;
    AtomicInteger readCounter = new AtomicInteger();


}
