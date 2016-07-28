package interview;

import sun.awt.image.ImageWatched;

/**
 * Created by Hao on 2/23/16.
 */
public class QuestionForLinkedList {
    static class LinkedNode {
        int val;
        LinkedNode next;
        public LinkedNode(int val){this.val = val;}
    }

    public QuestionForLinkedList() {
    }

    public LinkedNode reverse(LinkedNode node) {
        if (node == null) return node;
        LinkedNode cur = node;
        LinkedNode head = null;
        while (cur != null) {
            LinkedNode next = cur.next;
            cur.next = head;
            head = cur;
            cur = next;
        }
        return head;
    }

    public LinkedNode findMiddle(LinkedNode node) {
        if (node == null) return node;
        LinkedNode chaser = node;
        LinkedNode runner = node;

        while (runner != null && runner.next != null) {
            runner = runner.next.next;
            chaser = chaser.next;
        }
        return chaser;
    }

    public void printLinkedNode(LinkedNode node) {
        while (node != null) {
            if (node.next == null) {
                System.out.print(node.val);
            } else {
                System.out.print(",");
            }
            node = node.next;
        }
        System.out.println();
    }
}
