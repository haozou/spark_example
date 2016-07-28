package interview;

/**
 * Created by Hao on 2/19/16.
 */
public class Solution {
    public static void main(String[] args) {
        QuestionForStringAndArray question = new QuestionForStringAndArray();

        char[] chars = new char[]{'a','b','c', 'c', 'b', 'a'};
        question.reverseArray(chars);
        System.out.println(chars);

        question.removeDuplicates2(chars);
        System.out.println(chars);

        System.out.println(question.replaceFun(new char[]{'a', ' ', 'c', 'd', ' ', 'e'}));

        int[][] matrix = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12}, {13,14,15,16}};
        question.rotateImage(matrix);
        printMatrix(matrix);

    }

    public static void printArray(Object[] arrays) {
        for (Object obj : arrays) {
            System.out.print(obj);
        }
        System.out.println();
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }
}
