/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At:  7/4/2021 2:30 AM
 */

package lists;

public class LinkedStacks<T extends Comparable<T>> {

    /**
     * Attribute
     */
    private Node<T> topItem;


    /**
     * add new element to the top of stacks
     */
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(this.topItem);
        this.topItem = newNode;
    }

    /**
     * Removes and returns the top element
     */
    public T pop() {
        if (!isEmpty()) {
            T deleteTop = this.topItem.getData();
            this.topItem = this.topItem.getNext();
            return deleteTop;
        } else
            return null;
    }

    /**
     * Removes the top element
     */
    public T peek() {
        if (!isEmpty())
            return this.topItem.getData();
        return null;
    }


    /**
     * Clear stacks
     */
    public void clear() {
        this.topItem = null;
    }

    /**
     * Check if stacks empty or not
     */
    public boolean isEmpty() {
        return this.topItem == null;
    }

    /**
     * Return the length of the stacks
     */
    public int length() {
        if (!isEmpty()) {
            int length = 0; // # of nodes
            Node<T> current = this.topItem;
            while (current != null) {
                length++;
                current = current.getNext();
            }
            return length;
        } else
            return 0;
    }

    public void merge(Node<T> topItem) {
        if (topItem == null)
            return;
        else {
            merge(topItem.getNext());
            this.push(topItem.getData());
        }
    }

    /**
     * Return the element in the stacks as string
     */
    @Override
    public String toString() {
        if (!isEmpty()) { // stack has node
            StringBuilder str = new StringBuilder();
            Node<T> current = this.topItem; // to store tha data as a string
            while (current != null) {
                str.append(current).append(" ");
                current = current.getNext();
            }
            return str.toString();
        } else
            return "Stack empty";
    }

    /**
     * Display data in this stacks
     */
    public void displayStacks() {
        System.out.println(this);
    }

    /**
     * Fill this stacks from queue
     */
    public void fillFromQueue(Node<T> headQueue) {
        this.clear();
        // queue: 1 2 3 4 then stacks 4 3 2 1
        while (headQueue != null) {
            this.push(headQueue.getData());
            headQueue = headQueue.getNext();
        }
    }

    public void append(Node<T> topItem){
        if(topItem != null){
            this.push(topItem.getData());
            append(topItem.getNext());
        }

    }

    /**
     * return the top item as a node
     */
    public Node<T> getTopItem() {
        return this.topItem;
    }

    /**
     * Return the top item as a element
     */
    public T topItem() {
        if (!isEmpty())
            return this.topItem.getData();
        else
            return null;
    }

    /**
     * Set a new top item as a element
     */
    public void setTopItem(T topItem) {
        if (!isEmpty())
            this.topItem.setData(topItem);
    }

}

