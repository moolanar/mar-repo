import java.math.BigInteger;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class CheckPrimeNo {
	
	public static void main(String s[]) { 
		
		System.out.println(LocalDateTime.now()); 
		
		BigInteger start = new BigInteger("10000000001");
		BigInteger end = null;
		BigInteger ONE = new BigInteger("1");
		BigInteger INC = new BigInteger("100000");
		
		for(int i = 0; i< 2000; i++ ){			
			end = start.add(INC).subtract(ONE);			
			new MyThread("T"+i, start, end).start();			
			start = start.add(INC);			
		}
		
		System.out.println("end no  " + end);
	}

}

class MyThread extends Thread {
	String s = null;
	BigInteger bi1 =  null;
 	BigInteger bi2 =  null;
	BigInteger d = new BigInteger("1");
	BigInteger ONE = new BigInteger("1");
	BigInteger TWO = new BigInteger("2");
	BigInteger ZERO = new BigInteger("0");
	BigInteger r = null;
	long c = 0;
	long p = 0;
	
	long n = 10000L;
	ArrayList al = new ArrayList();
	
	MyThread(String s , BigInteger bi1, BigInteger bi2 ) {
		this.s = s;
		this.bi1 = bi1;
		this.bi2 = bi2;
	}
	
	public void run() { 
		
		while( (bi1.compareTo(bi2)) != 0) {			 	
			isPrime(bi1);
			bi1 = bi1.add(ONE);
		}
		
	}
	
	 boolean isPrime(BigInteger bi) {	
		
		 ++c;
		if(c % 5000 == 0 ) {
			System.out.println(LocalDateTime.now() + " : " + s + " : " + p + " : " + bi ); 
		}

		r = null;
		
		r=bi.mod(TWO);
		if((r.compareTo(ZERO)) == 0) {
			//System.out.println("Not Prime " + bi  + " / " + r  + " / " + d);
				return false;
		}
		
		d = new BigInteger("1");
		for ( long i=3; i<n; i=i+2 ) { 
			d = d.add(TWO);			
			r=bi.mod(d);
 			if((r.compareTo(ZERO)) == 0) {
				//System.out.println("Not Prime " + bi  + " / " + r  + " / " + d);
 				return false;
			}			
		}
		
		
		++p;

		
		return true;
	}

}
