
public class MySort {
	
	public static void main(String s[]) {
		
		// bubblesort - compare adj a[j+1] and [j]
		// insertionsort -- reverse loop , card game oderring based on key , while loop
		// quicksort - divide and conquer
		
		int[] arr = {1,2,3,4,5,6,7};
		
		//insertionsort(arr);	
		
		//bubblesort(arr);
		
		//quicksort(arr, 0, arr.length-1);
		
		printarr(arr, "Result : ");
		
		System.out.println("Res index " + binarysearch(arr, 7));
		
	}
	
	static int binarysearch(int[] a, int x) { 
		
		int min =0;
		int max = a.length-1;
		
		while(min <= max) {
			int mid = (min+max)/2;
			
			if(a[mid] == x) {
				return mid;
			}else if(a[mid] < x) {
				min = mid+1;
			}else if(a[mid] > x) {
				max = mid-1;
			}
		}
		
		return -1;
	}
	
	static void quicksort(int[] a, int low , int high ) {
		
		if(low < high) {
			int pi = partition(a, low, high);
			quicksort(a, low, pi-1);
			quicksort(a, pi+1, high);
		}
	}
	
	static int partition(int[] a, int low , int high ) {
		
		int pivot = a[high];
		int i = low-1;
		
		for(int j = low ; j<high; j++) {
			if(a[j] < pivot) {
				i++;
				swap(a, i, j);				
			}
		}
		
		
		swap(a, i+1, high);
		return i+1;
	}
	
	static void insertionsort(int[] a) { 
		
		int n = a.length;
		
		for (int i=1; i<n ; i++) {
			
			int last = a[i];
			int j = i-1;
			
			while( j>=0 && a[j] > last) {
				a[j+1] = a[j];
				j--;
			}
			a[j+1] = last;
		}
		
	}
	
	static void bubblesort(int[] a) { 
		// compare adj values 
		
		int n = a.length;
		
		for ( int i=0; i<n-1; i++) {
			boolean swapped = false;
			for(int j=0; j<n-i-1; j++) {
				if(a[j] > a[j+1]) {
					swap(a,j, j+1);
					swapped = true;
				}
			}
			if(!swapped) break;
		}
		
		
	}
	
	static void swap(int[] a, int i , int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	static void printarr(int[] arr , String s){
		if(s != null) System.out.print(s);
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}
