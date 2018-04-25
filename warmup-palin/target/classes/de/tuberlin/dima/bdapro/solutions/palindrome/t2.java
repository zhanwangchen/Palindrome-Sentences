package de.tuberlin.dima.bdapro.solutions.palindrome;

import java.util.Set;

/**
 * Created by zhanwang on 29/10/17.
 */
public class t2 {
    public static void main(String[] args) throws Exception {
        Set<String> res = new PalindromeTaskImpl().solve("");
        for (String temp : res){
            System.out.println(temp);
        }

    }
}
