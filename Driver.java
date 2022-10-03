import java.io.BufferedReader;
import java.util.HashMap;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Driver { 
    public static void main(String [] args) { 
        Polynomial p = new Polynomial(); 
        System.out.println(p.evaluate(3)); 
        double [] c1 = {6,5}; 
        int [] e1 = {0,3};
        Polynomial p1 = new Polynomial(c1,e1); 
        double [] c2 = {-2,-9}; 
        int [] e2 = {1,4};
        Polynomial p2 = new Polynomial(c2, e2); 
        Polynomial s = p1.add(p2); 
        System.out.println("s(0.1) = " + s.evaluate(0.1)); 
        if(s.hasRoot(1)) 
            System.out.println("1 is a root of s"); 
        else 
            System.out.println("1 is not a root of s"); 

        Polynomial x = p1.add2(p1,p2);
        System.out.println("x(0.1) = " + x.evaluate(0.1)); 

        Polynomial z = p1.multiply(p2);
        System.out.println("z(1) = " + z.evaluate(1));



        File text = new File("/Users/miraj/B07lab1/new.txt");


        Polynomial asd = new Polynomial(text);

        asd.saveToFile("testing.txt");
    } 
}
