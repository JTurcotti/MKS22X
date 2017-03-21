import java.util.*;

public class Quick {
    static int partition(int[] arr, int lo, int hi) {
	int pivot  = arr[hi];

	int current = lo;

	for (int i=lo; i<hi; i++)
	    if (arr[i]<pivot)
		swap(arr, i, current++);

	swap(arr, current, hi);

	return current;	
    }

    static void swap(int[] arr, int i, int j) {
	int temp = arr[i];
	arr[i] = arr[j];
	arr[j] = temp;
    }	

    static int quickselect(int[] arr, int k, int lo, int hi) {
	int pivot = partition(arr, lo, hi);
	if (k<pivot)
	    return quickselect(arr, k, lo, pivot-1);
	if (k>pivot)
	    return quickselect(arr, k, pivot+1, hi);
	return arr[k];
    } 

    static int quickselect(int[] arr, int k) {
	return quickselect(arr, k, 0, arr.length-1);
    }

    static void quicksort(int[] arr, int lo, int hi) {
	if (lo < hi) {
	    int pivot = partition(arr, lo, hi);
	    quicksort(arr, lo, pivot-1);
	    quicksort(arr, pivot + 1, hi);
	}
    }

    static void quicksort(int[] arr) {
	quicksort(arr, 0, arr.length-1);
    }


    public static void main(String[] args) {

	for (int k=0; k<6; k++) {
	    int[] ary = { 2, 10, 15, 23, 0, 5 };
	    System.out.println(quickselect(ary, k));
	}
    }
}
	    

	
