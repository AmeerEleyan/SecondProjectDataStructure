package lists;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
         LinkedStacks<Integer> l = new LinkedStacks<>();
        l.push(1);
        l.push(2);
        l.push(3);
        l.displayStacks();

        LinkedQueues<Integer> p = new LinkedQueues<>();
        p.enqueue(l.pop());
        p.enqueue(l.pop());
        p.enqueue(l.pop());
        p.displayQueue();

        l.fillFromQueue(p.getFirst());
        l.displayStacks();

    }
}
