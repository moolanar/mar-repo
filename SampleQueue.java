import java.util.*;

public class SampleQueue {
	
 
	//main_asList
	public static void main_asList(String[] s) {
		
		String[] n = { "5","3","2","1" };
		
		Arrays.sort(n); 
		
		List l = Arrays.asList(n);
		
		System.out.println(l);
		
		System.out.println(Arrays.binarySearch(n, "3"));
		 
		
	}
	
	public static void mainarry(String[] s) {
		
		String[] n = { "5","3","2","1" };
		
		Arrays.sort(n); 
		
		for(int i=0;i<n.length;i++) {
			System.out.println(n[i]);
		}
	}
	
 
	
	public static void mainq(String s[]) {
		
		Queue<Order> q = new PriorityQueue<>();	 
		
		q.add(new Order("A",3));
		q.add(new Order("B",2));
		q.add(new Order("C",1));
		
		while(!q.isEmpty()) {
		System.out.println(q.poll().id);
		}
		
		
		
	}
	
	

	public static void main1(String s[]){
		
		
		
		LinkedList<String> ll = new LinkedList<>();
		ll.add("C");
		ll.add("A");
		ll.add("B");		
		
		
	
		
		System.out.println(ll);
		
		Collections.sort(ll);
		
		
		System.out.println(ll);
		
		LinkedList<String> qll= new LinkedList<>();
		
		qll.push("1");
		qll.push("3");
		qll.push("2");
		
		
		
		System.out.println(qll);
		Collections.sort(qll);
		System.out.println(qll);
		
		qll.push("5");
		qll.push("4");
		System.out.println(qll);
		qll.pop();
		System.out.println(qll);
		qll.add("8");
		System.out.println(qll);
		qll.push("9");System.out.println(qll);
		
		qll.offer("0");System.out.println(qll);
		qll.poll();System.out.println(qll);
	}
	
}

class Order implements Comparable{
	
	String id;
	int amount;
	
	public Order(String id, int amount){
		this.id = id;
		this.amount = amount;
	}
	
	@Override
	public int compareTo(Object old) {	
		System.out.println(this.amount + " " + ((Order) old).amount);
		return this.amount < ((Order) old).amount ? 1: -1; 
	}

	 
}
