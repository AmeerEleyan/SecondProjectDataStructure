package lists;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        LinkedQueues<Integer> list = new LinkedQueues<>() ;
       list.enqueue(4);
        /*list.enqueue(2);
        list.enqueue(3);*/
       // list.dequeue();
        list.displayQueue();
//        System.out.println(list.first());
//
//        LinkedQueues<Integer> list2 = new LinkedQueues<>();
//        /*list2.enqueue(4);
//        list2.enqueue(5);
//        list2.enqueue(6);*/
//
//        System.out.println(list2);
//
//        list2.merge(list.getFirst());
//        System.out.println(list2);
//        LinkedStacks<Integer> linkedStacks = new LinkedStacks<>();
//        linkedStacks.push(1);
//        linkedStacks.push(2);
//        System.out.println(linkedStacks);
//
        LinkedStacks<Integer> linkedStacks2 = new LinkedStacks<>();
        linkedStacks2.push(1);
        linkedStacks2.push(2);
        linkedStacks2.push(3);
//        linkedStacks2.pop();

        linkedStacks2.displayStacks();

        list.fillFromStacks(linkedStacks2.getTopItem());
        list.displayQueue();

       /* Node<Integer> c = linkedStacks2.getTopItem();
        list.clear();
        while (c != null){
            list.enqueue(c.getData());
            c=c.getNext();
        }
        list.displayQueue();

      /*
       Node<Integer> current = list.getFirst();
        linkedStacks2.clear();
        while (current != null){
            linkedStacks2.push(current.getData());
            current=current.getNext();
        }
        linkedStacks2.displayStacks();*/
//
//        linkedStacks.merge(linkedStacks2.getTopItem());
//        System.out.println(linkedStacks);
      //  System.out.println("ammer".equals("apmer"));


    }
}
