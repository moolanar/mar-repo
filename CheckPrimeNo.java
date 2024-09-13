import java.math.BigInteger;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class CheckPrimeNo {
	
	public static void main(String s[]) { 
		
		BigInteger ONE = new BigInteger("1");
		BigInteger INC = new BigInteger("1000000");  // iM * 10 threads
		
		System.out.println(LocalDateTime.now()); 
		
		//BigInteger start = new BigInteger("10000000001");
		
		//
		BigInteger factorarg = new BigInteger("10000000").multiply(new BigInteger(s[0])) ;  // 10 M per process 
		BigInteger start = new BigInteger("10000000000").add(factorarg);
		start = start.add(ONE);
		BigInteger end = null;
		
		
		
		for(int i = 0; i< 10; i++ ){			
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
	StringBuilder sb = new StringBuilder();
	
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
			
			System.out.print(sb.toString());
			sb.delete(0, sb.length());
			c=0;
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
		
		sb.append(bi + "\n");
		++p;

		
		return true;
	}

}
