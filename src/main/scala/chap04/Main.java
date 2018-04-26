package chap04;

public class Main {

    public static void main(String[] args) {

        System.out.println( sum(1) );

        System.out.println( sum(1, 2) );

        System.out.println( sum(1, 2, 3) );

//        System.out.println( sum(Arrays.asList(1,2,3,4,5)) );
    }



    public static int sum(int ... ar) {
        int result = 0;
        for(int i : ar) {
            result += i;
        }

        return result;
    }
}
