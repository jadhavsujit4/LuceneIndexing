package com.lucene.indexandsearch;

import java.util.HashMap;

public class Test {

    public static void main(String[] args) {

        Tree t1 = new Tree();
        t1.x = 4;
        t1.l = null;
        t1.r = null;

        Tree t2 = new Tree();
        t2.x = 1;
        t2.l = t1;
        t2.r = null;

        Tree t3 = new Tree();
        t3.x = 1;
        t3.l = null;
        t3.r = null;

        Tree t4 = new Tree();
        t4.x = 2;
        t4.l = t3;
        t4.r = t2;

        Tree t5 = new Tree();
        t5.x = 1;
        t5.l = null;
        t5.r = t4;


        // Another Tree
        Tree t10 = new Tree();
        t10.x = 2;
        t10.l = null;
        t10.r = null;

        Tree t11 = new Tree();
        t11.x = 3;
        t11.l = t10;
        t11.r = null;

        Tree t12 = new Tree();
        t12.x = 6;
        t12.l = null;
        t12.r = null;

        Tree t13 = new Tree();
        t13.x = 2;
        t13.l = t11;
        t13.r = t12;


        Tree t14 = new Tree();
        t14.x = 5;
        t14.l = null;
        t14.r = null;

        Tree t15 = new Tree();
        t15.x = 6;
        t15.l = null;
        t15.r = null;

        Tree t16 = new Tree();
        t16.x = 1;
        t16.l = t14;
        t16.r = t15;

        Tree t17 = new Tree();
        t17.x = 3;
        t17.l = null;
        t17.r = null;

        Tree t18 = new Tree();
        t18.x = 3;
        t18.l = t17;
        t18.r = t16;

        //Main Root branch
        Tree t19 = new Tree();
        t19.x = 1;
        t19.l = t13;
        t19.r = t18;


        // Another tree which was not working on codility environment
        Tree t20 = new Tree();
        t20.x = 4;
        t20.l = null;
        t20.r = null;

        Tree t21 = new Tree();
        t21.x = 1;
        t21.l = t20;
        t21.r = null;

        Tree t22 = new Tree();
        t22.x = 1;
        t22.l = null;
        t22.r = null;

        Tree t23 = new Tree();
        t23.x = 2;
        t23.l = t22;
        t23.r = t21;

        Tree t24 = new Tree();
        t24.x = 1;
        t24.l = null;
        t24.r = t23;


        Test t = new Test();


        System.out.println(t.solution(t19));

    }

    public static int uniquePath(Tree T, HashMap<Integer,
            Integer> myHashMap) {
        if (T == null)
            return myHashMap.size();

        // put this node into hash
        if (myHashMap.containsKey(T.x)) {
            myHashMap.put(T.x, myHashMap.get(T.x) + 1);
            //return myHashMap.size();
        } else {
            myHashMap.put(T.x, 1);
        }

        int max_path = Math.max(uniquePath(T.l, myHashMap),
                uniquePath(T.r, myHashMap));

        if (myHashMap.containsKey(T.x)) {
            myHashMap.put(T.x, myHashMap.get(T.x) - 1);
        }

        if (myHashMap.get(T.x) == 0)
            myHashMap.remove(T.x);

        return max_path;
    }

    public int solution(Tree T) {
        int sizeOfTree = 0;
//        ArrayList<Integer> treeSizes = new ArrayList<Integer>();
//        if (T == null)
//            return sizeOfTree;
//        else {
//            ArrayList<Integer> ls = new ArrayList<>();
//            if (!ls.contains(T.x)) {
//                ls.add(T.x);
//                sizeOfTree++;
//            } else
//                return sizeOfTree;
//            sizeOfTree = sizeOfTree + solution(T.l);
//            sizeOfTree = sizeOfTree + solution(T.r);
//
//        }
//

        sizeOfTree = uniquePath(T, new HashMap<Integer, Integer>());
        return sizeOfTree;
    }


}

class Tree {
    public int x;
    public Tree l;
    public Tree r;
}