import java.math.BigInteger;

public class CheckPrimeNo10K {

        static long l = 0;


        public static void main(String s[]) {

                BigInteger bi1 =  new BigInteger("36746043666799590428244633799627952632279158164343087642676032283815739666511279233373417143396810270092798736000000");
          //BigInteger bi2 =  new BigInteger("33478071698956898786044169848212690817704794983713768568912431388982883793878002000000000000000000000000000000000010");
                BigInteger bi2 =  new BigInteger("36746043666799590428244633799627952632279158164343087642676032283815739666511279233373417143396810270092798736308920");

                BigInteger ONE = new BigInteger("1");
                BigInteger ZERO = new BigInteger("0");

                boolean f = false;

                System.out.println("Compare result is ");
                System.out.println( bi1.compareTo(bi2) );




                while( (bi1.compareTo(bi2)) != 0) {
                        bi1 = bi1.add(ONE);

                        if(isEven(bi1)) {
                                continue;
                        }

                        isPrime(bi1);
                }
        }


        static boolean isEven(BigInteger bi) {

                BigInteger TWO = new BigInteger("2");
                BigInteger ZERO = new BigInteger("0");

                BigInteger r = bi.mod(TWO);

                if(r.compareTo(ZERO) == 0 ) {
                        return true;
                }

                return false;

        }


        static boolean isPrime(BigInteger bi) {

                long n = 10000L;
                BigInteger d = new BigInteger("1");
                BigInteger TWO = new BigInteger("2");
                BigInteger ZERO = new BigInteger("0");
                BigInteger r = null;
                for ( long i=3; i<n; i=i+2 ) {
                        d = d.add(TWO);
                        r=bi.mod(d);
                        if((r.compareTo(ZERO)) == 0) {
                                //System.out.println("Not Prime " + bi  + " / " + r  + " / " + d);
                                return false;
                        }

                }

                System.out.println("Possible Prime .. " + bi);
                return true;
        }


        static boolean isPrime(long bi) {
                return true;
        }
}
