package cn.denghanxi.p914;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 邓晗熙 on 2019/12/29
 */
public class Solution {
    public boolean hasGroupsSizeX(int[] deck) {
        if (deck.length < 2) return false;

        int[] counter = new int[10000];
        for (int i : deck) {
            counter[i]++;
        }
        List<Integer> checker = null;
        for (int i = 0; i < counter.length; i++) {
            int count = counter[i];
            if (count == 1) return false;
            if (count > 1 && checker == null) {
                checker = new ArrayList<>();
                for (int j = 2; j <= count && j <= 100; j++) {
                    if (count % j == 0) {
                        checker.add(j);
                    }
                }
            } else if (count > 1) {
                Iterator<Integer> iterator = checker.iterator();
                while (iterator.hasNext()) {
                    int checkNum = iterator.next();
                    if (count % checkNum != 0) {
                        iterator.remove();
                    }
                }
                if (checker.size() == 0) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] deck = {1,2,3,4,4,3,2,1,6};
        if (!new Solution().hasGroupsSizeX(deck)) throw new RuntimeException("not right");

    }
}
