import java.math.BigInteger;

public class CheckPrimeNo {
	
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
 			if(f) {
				f=false;
				continue;
			}
			
			f = true;		
			isPrime(bi1);
			bi1 = bi1.add(ONE);
		}
	}
	
	
 
	
	static boolean isPrime(BigInteger bi) {
		
		boolean flag = true;
		long n = 10000001L;
		BigInteger d = new BigInteger("1");
		BigInteger ONE = new BigInteger("1");
		BigInteger ZERO = new BigInteger("0");
		BigInteger r = null;
		for ( long i=2; i<n; i++ ) { 
			d = d.add(ONE);			
			r=bi.mod(d);
 			if((r.compareTo(ZERO)) == 0) {
				//System.out.println("Not Prime " + bi  + " / " + r  + " / " + d);
 				return false;
			}
			
		}
		
		System.out.println("Possible Prime .. " + bi);
		return flag;
	}

	
	static boolean isPrime(long bi) {
		return true;
	}
}
