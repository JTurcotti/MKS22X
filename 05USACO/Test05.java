import java.io.*;
import java.util.*;
public class Test05 {
    public static void main(String[]args) throws Exception{
	USACO u = new USACO();
	String number = args[0];

	if(args.length == 1){
	    String file = "makelake."+number;
	    int tot = 0;
	    if(u.bronze(file+".in")==new Scanner(new File(file+".out")).nextInt()){
		System.out.println("PASS case: "+file+".in +++++++++++++");
	    }else{
		System.out.println("Fail case: "+file+".in ------------");
	    }
	}else if(args.length ==2){
	    String file = "ctravel."+number;
	    if(u.silver(file+".in")==new Scanner(new File(file+".out")).nextInt()){
		System.out.println("PASS case: "+file+".in +++++++++++++");
	    }else{
		System.out.println("Fail case: "+file+".in ------------");
	    }
	}

    }
}
