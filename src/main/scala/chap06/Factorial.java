package chap06;

public class Factorial {
    public static long factorial(long l) {
        long result = 1L;
        for(long j = 2L; j <= l; j++) {
            result *= j;
        }

        return result;
    }

    public static void main(String[] args) {
        for(long l = 1L; l <= 10; l++) {
            System.out.println(l +"\t" + factorial(l));
        }
    }
}
