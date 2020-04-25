package com.wxn.algorithm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 链表
 */
public class ListNode {
    /**
     * 单链表alone_linked.jpg
     */

    /**
     * 反转
     * 12345
     * i    newhead temp    head
     * 0.1          12345
     * 0.2                  2345
     * 0.3          1
     * 0.4  1
     * -    1       1       2345
     * -------------------------
     * 1.1          2345
     * 1.2                  345
     * 1.3          21
     * 1.4  21
     * -    21      21      345
     * -------------------------
     * 2.1          345
     * 2.2                  45
     * 2.3          321
     * 2.4  321
     * -    321     321     45
     * -------------------------
     * 3    4321    4321    4
     * 4    54321   54321
     * -------------------------
     */
    public static Node reverse(Node head) {
        Node newHead = null, temp;
        while(head != null) {
            temp = head;
            head = head.next;
            temp.next = newHead;
            newHead = temp;
        }
        return newHead;
    }

    public static void main(String[] args) {
//        Node l1 = new Node(1);
//        l1.next = new Node(2);
//        l1.next.next = new Node(5);
//        Node l2 = new Node(3);
//        l2.next = new Node(4);
//        l2.next.next = new Node(6);
//        Node l3 = mergeTwoLists(l1, l2);
//        System.out.println(l3.toString());
    }

    private static String setToString(Set<String> values) {
        StringBuffer buffer = new StringBuffer(",-1,");
        if(values == null || values.size() == 0)
            return buffer.toString();
        Iterator it = values.iterator();
        while(it.hasNext())
            buffer.append(it.next().toString() + ",");
        return buffer.toString();
    }

    /**
     * 合并两个有序链表
     * @param l1
     * @param l2
     * @return
     */
    public static Node mergeTwoLists(Node l1, Node l2) {
        if(l1 == null) { return l2; }
        if(l2 == null) { return l1; }
        Node temp = null;
        if(l1.val <= l2.val) {
            temp = l1;
            temp.next = mergeTwoLists(l1.next, l2);
        } else {
            temp = l2;
            temp.next = mergeTwoLists(l1, l2.next);
        }
        return temp;
    }

    static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            StringBuffer buff = new StringBuffer();
            Node node = this;
            while(node != null) {
                buff.append(node.val + ", ");
                node = node.next;
            }
            return buff.toString();
        }
    }

}
