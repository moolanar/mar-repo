
public class MyTree {
	
	public static void main(String s[]) {
	
	Tree tr = new Tree();
	
	int[] nodes = { 50, 30, 20, 10, 80, 70, 60 };
	
	for( int node : nodes) {
		tr.add(node);
	}

	tr.displayPreOrder(tr.root);
	
	}
}

class Node {
	
	int data;
	Node leftchild;
	Node rightchild;
	
	Node(int d) {
		data = d;		
	}
}

class Tree {
	Node root;
	
	void displayPreOrder(Node root) {
		if (root != null) {
			System.out.print (root.data + " ");
			displayPreOrder(root.leftchild);
			displayPreOrder(root.rightchild);
		}		
	}
	
	void add(int data){
		
		Node t = new Node(data);
		
		if(root == null) {
			root = t;
			return;
		}
		
		Node curr = root;
		Node parent = null;
		
		while(true) {
			parent = curr;
			if(data < parent.data) {
				
				curr = parent.leftchild;
				
				if(curr == null) {
					parent.leftchild = t;
					return ;
				}
				
				
			}else {
				
				curr = parent.rightchild;
						
				if(curr == null) {
					parent.rightchild = t;
					return ;
				}
			}
			
		}
		
	}
	
}
