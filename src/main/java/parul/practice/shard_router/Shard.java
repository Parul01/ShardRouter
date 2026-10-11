package parul.practice.shard_router;

import java.util.concurrent.atomic.AtomicInteger;

public class Shard {

//    when you hash key - you will get a shard

    boolean readFlag = true;
    AtomicInteger readCounter = new AtomicInteger();


}
