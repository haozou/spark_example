package interview;

import apple.laf.JRSUIUtils;

import java.util.*;

/**
 * Created by Hao on 2/23/16.
 */
public class QuestionForTree {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val) {this.val = val;}
    }
    public QuestionForTree() {
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List level = new ArrayList<>();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(level);
        }
        return result;
    }
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();
        s1.push(root);
        boolean flag = true;
        while (!s1.isEmpty()) {
            int size = s1.size();
            List<Integer> level = new ArrayList<>();
            while (size-- > 0) {
                TreeNode node = s1.pop();
                level.add(node.val);
                if (flag) {
                    if (node.left != null) {
                        s2.push(node.left);
                    }
                    if (node.right != null) {
                        s2.push(node.right);
                    }
                } else {
                    if (node.right != null) {
                        s2.push(node.right);
                    }
                    if (node.left != null) {
                        s2.push(node.left);
                    }
                }
            }
            Stack tmp = s1;
            s1 = s2;
            s2 = tmp;
            result.add(level);
            flag = !flag;
        }
        return result;
    }
}
