import org.junit.Test;

/**
 * Created by Administrator on 2016/10/30.
 */
public class MyAVLTree {
    private int value;
    private MyAVLTree leftnode;
    private MyAVLTree rightnode;
    private int height;
    public MyAVLTree(int i, MyAVLTree leftnode, MyAVLTree rightnode){
        this.value = i;
        this.leftnode = leftnode;
        this.rightnode = rightnode;
        this.height = 0;
    }
    public MyAVLTree insert(int inode, MyAVLTree avlTree){
        if(avlTree == null){
            return new MyAVLTree(inode,null,null);
        }
        if(inode > avlTree.value)
            avlTree.rightnode = insert(inode, avlTree.rightnode);
        if(inode < avlTree.value)
            avlTree.leftnode = insert(inode, avlTree.leftnode);
        return rebalance(avlTree);
    }

    private MyAVLTree rebalance(MyAVLTree avlTree) {
        return null;
    }
    @Test
    public void test(){
        System.out.println("a new test");
    }
    public static void main(String[] args){
        System.out.println("hello world");
    }
}



