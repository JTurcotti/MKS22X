import java.util.Arrays;
import java.util.Random;

public class Merge {
     static void copy(int[] from, int[] to) {
	for (int i=0; i<from.length; i++)
	    to[i] = from[i];
    }

    static void mergeInto(int start, int middle, int end, int[] from, int[] to) {
	int i = start;
	int j = middle;

	for (int k=start; k<end; k++) {
	    if (i<middle && (j==end || from[i] <= from[j]))
		to[k] = from[i++];
	    else
		to[k] = from[j++];
	}
    }

    static int mergeAt(int[] parts, int num, int[] from, int[] to) {
	int j = 0;
	int i = 0;
	for (; i+2<num; i+=2) {
	    mergeInto(parts[i], parts[i+1], parts[i+2], from, to);
	    parts[j++] = parts[i];
	}
	int k = parts[i];
	while (k<from.length) {
	    to[k] = from[k];
	    parts[j++] = parts[k++];
	}
	parts[j++] = parts[k++];
	return j;
	    
    }

    static void sortTop(int start, int end, int[] arr, int[] temp) {
	if (end-start==1)
	    return;
	int middle = (start+end)/2;
	sortTop(start, middle, temp, arr);
	sortTop(middle, end, temp, arr);
	mergeInto(start, middle, end, temp, arr);
    }

    static void mergeSortTop(int[] arr) {
	int[] out = new int[arr.length];
	sortTop(0, arr.length, out, arr);

	for (int i=0; i<arr.length; i++)
	    arr[i] = out[i];
    }
    
    static void mergeSortBottom(int[] arr) {
	int[] temp = new int[arr.length];
	int i=0;
	for (int width = 1; width<arr.length; width*=2) {
	    if (i++%2==0)
		makePass(width, arr, temp);
	    else
		makePass(width, temp, arr);
	}
	if (i%2==1)
	    for (int j = 0; j<arr.length; j++)
		arr[j] = temp[j];
		

    }

    static void makePass(int width, int[] from, int[] to) {
	int i;
	int tw = 2*width;
	for (i=0; i+tw<=from.length; i+=tw)
	    mergeInto(i, i+width, i+tw, from, to);
	mergeInto(i, Math.min(i+width, from.length), Math.min(i+tw, from.length), from, to);
    }

    static void mergePreserve(int[] arr) {
	int len = arr.length;
	int[] from = arr;
	int[] to = new int[len];
	int[] parts = new int[len+1];
	int num = len+1;
	for (int i=0; i<=len; parts[i]=i++);

	while (num>2) {
	    num = mergeAt(parts, num, from, to);
	    
	    int[] temp = from;
	    from = to;
	    to = temp;
	}

	for (int i=0; i<len; i++)
	    arr[i] = from[i];
    }

    static void mergeNatural(int[] arr) {
	int len = arr.length;
	int[] from = arr;
	int[] to = new int[len];
	int[] parts = new int[len+1];
	int num=0;
	parts[num++]=0;
	for (int k=1; k<len; k++)
	    if (arr[k]<arr[k-1])
		parts[num++] = k;

	
	while (num>2) {
	    num = mergeAt(parts, num, from, to);
	    
	    int[] temp = from;
	    from = to;
	    to = temp;
	}

	for (int i=0; i<len; i++)
	    arr[i] = 10 - i;
    }

    static boolean isSorted(int[] arr) {
	for (int i=0; i+1<arr.length; i++)
	    if (arr[i]>arr[i+1])
		return false;
	return true;
    }

    
    static int[] ranArr(int len, Random r) {
	int[] test = new int[len];
	for (int i=0; i<len; i++)
	    test[i] = r.nextInt();
	return test;
    }

    static long time() {
	return System.currentTimeMillis();
    }

       
    public static void main(String[] args) {
	System.out.println(new Integer(5).compareTo(new Integer(6)));
    }
	/*
	int num = Integer.parseInt(args[0]);
 	int len = Integer.parseInt(args[1]);
	Random r = new Random();
	for (int i = 0; i<num; i++) {
	    int[] test = ranArr(len, r);
	    int[] temp = new int[len];
	    
	    copy(test, temp);
	    long time = time();
	    mergeSortTop(temp);
	    time = time() - time;
	    if (isSorted(temp))
		System.out.print("Top Down in " + time + "ms ");
	    else
		System.out.print("Top Down failure ");
	    
	    copy(test, temp);
	    time = time();
	    mergeSortBottom(temp);
	    time = time() - time;
	    if (isSorted(temp))
		System.out.print("Bottom Up in " + time + "ms ");
	    else
		System.out.print("Bottom Up failure ");

	    
	    copy(test, temp);
	    time = time();
	    mergePreserve(temp);
	    time = time() - time;
	    if (isSorted(temp))
		System.out.print("Preserve in " + time + "ms ");
	    else
		System.out.print("Preserve failure ");

	    
	    /*	    copy(test, temp);
	    time = time();
	    mergeNatural(temp);
	    time = time() - time;
	    if (isSorted(temp))
		System.out.print("Natural in " + time + "ms ");
	    else
		System.out.print("Natural failure ");
	    
	    System.out.print('\n');
	}
    }
*/
}	    
