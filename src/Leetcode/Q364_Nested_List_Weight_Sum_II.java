package Leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by rbhatnagar2 on 3/15/17.
 * <p>
 * Given a nested list of integers, return the sum of all integers in the list weighted by their depth.
 * <p>
 * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
 * <p>
 * Different from the previous question where weight is increasing from root to leaf,
 * now the weight is defined from bottom up.
 * i.e., the leaf level integers have weight 1,
 * and the root level integers have the largest weight.
 * <p>
 * Example 1:
 * Given the list [[1,1],2,[1,1]], return 8.
 * (four 1's at depth 1, one 2 at depth 2)
 * <p>
 * Example 2:
 * Given the list [1,[4,[6]]], return 17.
 * (one 1 at depth 3, one 4 at depth 2, and one 6 at depth 1; 1*3 + 4*2 + 6*1 = 17)
 */
public class Q364_Nested_List_Weight_Sum_II {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.size() == 0)
            return 0;

        HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

        //two stacks: one is for processing nested integer, the other is for tracking layers.
        Stack<NestedInteger> stack = new Stack<NestedInteger>();
        Stack<Integer> layers = new Stack<Integer>();

        //put all NestedIntegers to Stack and record its layer to be 1
        for (NestedInteger ni : nestedList) {
            stack.push(ni);
            layers.push(1);
        }

        int maxLayer = Integer.MIN_VALUE;

        while (!stack.isEmpty()) {
            NestedInteger top = stack.pop();
            int topLayer = layers.pop();

            maxLayer = Math.max(maxLayer, topLayer);

            if (top.isInteger()) {
                if (!map.containsKey(topLayer)) {
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    map.put(topLayer, list);
                }
                map.get(topLayer).add(top.getInteger());
            } else {
                for (NestedInteger ni : top.getList()) {
                    stack.push(ni);
                    layers.push(topLayer + 1);
                }
            }
        }

        // calculate sum
        int result = 0;
        for (int i = maxLayer; i >= 1; i--) {
            if (map.get(i) != null) {
                for (int v : map.get(i)) {
                    result += v * (maxLayer - i + 1);
                }
            }
        }

        return result;
    }

    class NestedInteger {

        public NestedInteger() {
        }

        public NestedInteger(int value) {
        }

        public boolean isInteger() {
            return true;
        }

        public Integer getInteger() {
            return 1;
        }

        public List<NestedInteger> getList() {
            return null;
        }
    }
}
