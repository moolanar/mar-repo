import java.math.BigInteger;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.*;
public class CheckPrimeNo {
	
	public static void main(String s[]) { 
		
		BigInteger ONE = new BigInteger("1");
		BigInteger TEN = new BigInteger("10");
		BigInteger INC = new BigInteger("200000000");  // 1M * 10 threads
		
		//32769132993266709549961988190834461413177642967992942539798288533
		//32769132993266709549961988190834461413177642967992942540000000001
		//32769132993266709549961988190834461413166610000000000000000000000
		//32769132993266709549961988190834461413177642967992942531000000000
		
		
		BigInteger no = new BigInteger("114381625757888867669235779976146612010218296721242362562561842935706935245733897830597123563958705058989075147599290026879543541");
		
		BigInteger input = new BigInteger("32769132993266709549961988190834461413177642967992942520000000000");
		
		System.out.println(LocalDateTime.now());  

		BigInteger factorarg = INC.multiply(new BigInteger(s[0])).multiply(TEN) ;  // 10 M per process 
		
		System.out.println(" input no      " + input);
		System.out.println(" factorarg no  " + factorarg);
		
		BigInteger start = input.add(factorarg);

		start = start.add(ONE);
		BigInteger end = null;
		
		
		
		for(int i = 0; i< 10; i++ ){			
			end = start.add(INC).subtract(ONE);		
			
            System.out.println(i + " start no  " + start);
            System.out.println(i + " end no    " + end);
			
			new MyThread(s[0] + "_" +i, start, end, no).start();			
			start = start.add(INC);			
		}
		
		
	}

}

class MyThread extends Thread {
	String s = null;
	BigInteger input =  null;
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
	FileWriter fw = null;
	
	MyThread(String s , BigInteger bi1, BigInteger bi2, BigInteger input ) {
		this.s = s;
		this.bi1 = bi1;
		this.bi2 = bi2;
		this.input = input;

        try {
            //fw = new FileWriter(new File(s+".txt"));
        }catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	public void run() { 
		
		while( (bi1.compareTo(bi2)) != 0) {			 	
			isPrime(bi1);
			bi1 = bi1.add(ONE);
		}

        try {
			if(sb.length() > 0) {
				if(fw == null) {
					fw = new FileWriter(new File(s+".txt"));
				}
				fw.write(sb.toString());
				fw.flush();
				sb.delete(0, sb.length());
			}			
			    
		}catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println(LocalDateTime.now());  
	}
	
	 boolean isPrime(BigInteger bi) {	
		
		 ++c;
		if(c % 100000 == 0 ) {
			
			//System.out.print(sb.toString() + " / " + sb.length());
            try {
				if(sb.length() > 0) {
					if(fw == null) {
						fw = new FileWriter(new File(s+".txt"));
					}
					fw.write(sb.toString());
					sb.delete(0, sb.length());
					c=0;
				}
                
            }catch(Exception e) {
                e.printStackTrace();
            }
			
			
		}

		
		r=bi.mod(TWO);
		if((r.compareTo(ZERO)) == 0) {
			//System.out.println("Not Prime " + bi  + " / " + r  + " / " + d);
				return false;
		}
		
		r = input.mod(bi);		
		if((r.compareTo(ZERO)) != 0) {
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
		
		//sb.append(bi1 +" / " +  bi2 + " - " +  bi + "\n");
		System.out.println("Possible prime is : " + bi );
        sb.append(  bi + "\n");
		++p;

		
		return true;
	}

}
