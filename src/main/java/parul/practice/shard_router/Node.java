package parul.practice.shard_router;

public class Node {

    int id;
    public Node(int id){
        this.id = id;
    }



    public void writeRequest(String key, String value, int shardId){
        System.out.println("Node: "+ this +" write requested for key : " + key + " with value: " + value+ " shard id "+ shardId);
    }
    public void readRequest(String key, int shardId){
        System.out.println("Node: "+ this + "read request for key: "+ key + " shard id "+ shardId);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                '}';
    }
}
