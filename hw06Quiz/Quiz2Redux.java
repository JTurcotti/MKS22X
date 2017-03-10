import java.util.*;
public class Quiz2Redux{  
    /*Returns an ArrayList<String> that contains all subsets of the
     *characters of String s. Assume s has no duplicate characters.
     *the characters should appear in the same order that they occur 
     *in the original string.
     */
    public static ArrayList<String> combinations(String str){
	ArrayList<String>words;
	String out;
	
	out = "";
	words = new ArrayList<String>();
	help( words , str, 0, "");
	for (String word: words)
	    out+=", " + word;
	System.out.println("[" + out.substring(2) + "]");

	out = "";
	words = new ArrayList<String>();
	help2( words , str, 0);
	for (String word: words)
	    out+=", " + word;
	System.out.println("[" + out.substring(2) + "]");

	out = "";
	words = new ArrayList<String>();
	helpP( words , str, 0);
	for (String word: words)
	    out+=", " + word;
	System.out.println("[" + out.substring(2) + "]");

	return words;
    }
  
    private static void help( ArrayList<String> words, String str, int pos, String prefix) {
	if (pos==str.length()) words.add(prefix);
	else {
	    help(words, str, pos+1, prefix);
	    help(words, str, pos+1, prefix + str.charAt(pos));
	}
    }

    private static void help2( ArrayList<String> words, String str, int pos) {
	if (pos==str.length()) words.add(str);
	else {
	    help2(words, str, pos+1);
	    help2(words, str.substring(0, pos) + str.substring(pos+1), pos);
	}
    }

    static String swap(String str, int a , int b) {
	char[] arr = str.toCharArray();

	char temp = arr[a];
	arr[a] = arr[b];
	arr[b] = temp;

	return new String(arr);
    }

    private static void helpP( ArrayList<String> words, String str, int pos) {
	if (pos == str.length()) words.add(str);
	else for (int i=pos; i<str.length(); i++)
	    helpP(words, swap(str, pos, i), pos+1);
    }


    public static void main(String[] args) {

	combinations(args[0]);

	/*
	List<String> combos = combinations(args[0]);
	String out = "";
	for (String combo: combos)
	    out+=", " + combo;
	    System.out.println("[" + out.substring(2) + "]");*/
    }
}
