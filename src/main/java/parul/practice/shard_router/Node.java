package parul.practice.shard_router;

public class Node {

    int id;
    public Node(int id){
        this.id = id;
    }


    public void writeRequest(String key, String value){
        System.out.println("Node: "+ this +" write requested for key : " + " with value: " + value);
    }
    public void readRequest(String key){
        System.out.println("Node: "+ this + "read request for key: "+ key);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                '}';
    }
}
