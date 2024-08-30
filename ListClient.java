class Node {
	int data;
	Node next;
	
	public Node(int data){
		this.data = data;		
	}
}

class SingleLinkList {
	Node head;
	Node tail;
	
	public void add(int data){
		
		Node t = new Node(data);
		
		if(head == null) {
			head = t;
			tail = t;
		}else {
			tail.next = t;
			tail = t;
		}
	}
	
	public void addFirst(int data) {
		
		Node t = new Node(data);
		
		if(head == null) {
			head = t;
			tail = t;
		} else {
			t.next = head;
			head = t;
		}
		
	}
	
	public void add(int index , int data) {	
		
		
		
		if(index ==0) {
			addFirst(data);
			return;
		}
		
		Node t = new Node(data);
		Node curr = head;
		
		int count = -1; 
		boolean match = false;
		while(curr != null) {
			++count;
			if(count == index-1) {				
				t.next = curr.next;
				curr.next = t;				
				break;
			}else {
				curr = curr.next;
			}			
		} 
		
	}
	
	public void print(){
		
		Node curr = head;
		
		while (curr != null) {
			System.out.print(curr.data + " ");
			curr = curr.next;
		}
		System.out.println();		
	}
	
	public void printhead() {
		if(head != null ) {
			System.out.println(head.data);
		}
	}
	
	public void printtail(){
		if(tail != null) {
			System.out.println(tail.data);
		}
	}
	
	public void removehead() {
		if(head!=null) {		
			if(head.next == null) {
				head = null;
			}else {
				head = head.next;
			}
			
		}
	}
	
	public void removetail(){
		
	}
}

public class ListClient {
	
	public static void main(String s[]) {
		
		SingleLinkList sll = new SingleLinkList();
		
		sll.add(10);
		sll.add(20);
		sll.add(30);
	
		sll.print();
		 
		sll.add(1,13);		
		sll.print();
		
		sll.add(0,3);
		sll.print();
	}
}
