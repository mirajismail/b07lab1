import java.io.BufferedReader;
import java.util.HashMap;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Polynomial {
    double [] coefficients;
    int [] powers;
    public Polynomial() {
        coefficients = new double[] {0.0};
        powers = new int[] {0};
    }
    public Polynomial(double [] coefficients, int [] powers) {
        this.coefficients = coefficients;
        this.powers = powers;
    }

    public Polynomial(File f) {
        try {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                data = "+" + data.replace("-", "+-");
                //System.out.println(data);
                
                String [] terms = data.split("\\+");

                for (int i = 0; i<terms.length; i++){
                    //System.out.println(terms[i]);
                }
                int arr_size = terms.length;
                //System.out.println(arr_size);
                double [] coeff = new double[arr_size-1];
                int [] exp = new int[arr_size-1];

                

                for (int i = 1; i < arr_size; i++) {

                    String [] temp = terms[i].split("x");
                    String [] temp2 = terms[i].split("");

                    coeff[i-1] = Double.parseDouble(temp[0]);

                    //System.out.println(Arrays.toString(temp2));

                    int count = 0;
                    String exp_str = "";
                    for(int j = 0; j < temp2.length; j++){
                        count += 1;
                        if (temp2[j].equals("x")) {
                            break;
                        }
                    }
                    if (count == temp2.length & !(temp2[temp2.length-1].equals("x"))){
                        exp[i-1] = 0;
                    }
                    else if (count == temp2.length & temp2[temp2.length-1].equals("x")){
                        exp[i-1] = 1;
                    }
                    else{
                        for (int k = count; k<temp2.length;k++){
                            exp_str+=temp2[k];
                        }
                        exp[i-1] = Integer.parseInt(exp_str);
                    }
                }
                this.coefficients = coeff;
                this.powers = exp;
                // for (int i = 0; i<coeff.length;i++){
                //     System.out.println("coef: "+coeff[i]);
                //     System.out.println("exp: "+exp[i]);
                // }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void saveToFile(String name) {
        try {
            File newFile = new File(name);
            if (!(newFile.createNewFile())) {
                System.out.println("File already exists.");
            }
            String poly = "";
            for (int i = 0; i<this.powers.length; i++){
                if (this.powers[i] == 0) {
                    if (i!=0 & this.coefficients[i] > 0.0){
                        poly+= "+" + Double.toString(this.coefficients[i]);
                    }
                    else{
                        poly += Double.toString(this.coefficients[i]);
                    }
                }
                else if (this.powers[i] == 1) {
                    if (i!=0 & this.coefficients[i] > 0.0){
                        poly = poly + "+" + Double.toString(this.coefficients[i]) + "x";
                    }
                    else{
                        poly = poly + Double.toString(this.coefficients[i]) + "x";
                    }
                }
                else {
                    if (i!=0 & this.coefficients[i] > 0.0){
                        poly = poly + "+" + Double.toString(this.coefficients[i]) + "x" + Integer.toString(this.powers[i]); 
                    }
                    else{
                        poly = poly + Double.toString(this.coefficients[i]) + "x" + Integer.toString(this.powers[i]);
                    }
                }
            }
            FileWriter writer= new FileWriter(name);
            writer.write(poly);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Polynomial add(Polynomial p) {
        Polynomial p2;
        HashMap<Integer, Double> poly_map = new HashMap<Integer, Double>();

        for (int i = 0; i<this.coefficients.length; i++) {
            poly_map.put(this.powers[i], this.coefficients[i]);
        }
        for (int i = 0; i<p.coefficients.length; i++) {
            if (poly_map.containsKey(p.powers[i])) {
                poly_map.put(p.powers[i], poly_map.get(p.powers[i]) + p.coefficients[i]);
            }
            else {
                poly_map.put(p.powers[i], p.coefficients[i]);
            }
        }
    
        double [] p2_coeffs = new double[poly_map.size()];
        int [] p2_exp = new int[poly_map.size()];
        
        int count = 0;
        for (int key: poly_map.keySet()) {
            p2_coeffs[count] = poly_map.get(key);
            p2_exp[count] = key;

            count++;
        }
        
        p2 = new Polynomial(p2_coeffs, p2_exp);

        return p2;
    }

    //Helper method for multiply method
    public Polynomial add2(Polynomial p1, Polynomial p2) {

        Polynomial p3;
        HashMap<Integer, Double> poly_map = new HashMap<Integer, Double>();

        for (int i = 0; i<p1.coefficients.length; i++) {
            poly_map.put(p1.powers[i], p1.coefficients[i]);
        }
        for (int i = 0; i<p2.coefficients.length; i++) {
            if (poly_map.containsKey(p2.powers[i])) {
                poly_map.put(p2.powers[i], poly_map.get(p2.powers[i]) + p2.coefficients[i]);
            }
            else {
                poly_map.put(p2.powers[i], p2.coefficients[i]);
            }
        }
    
        double [] p3_coeffs = new double[poly_map.size()];
        int [] p3_exp = new int[poly_map.size()];
        
        int count = 0;
        for (int key: poly_map.keySet()) {
            p3_coeffs[count] = poly_map.get(key);
            p3_exp[count] = key;

            count++;
        }
        
        p3 = new Polynomial(p3_coeffs, p3_exp);

        return p3;
    }


    public Polynomial multiply(Polynomial p) {
        double [][] coeff_1;
        int [][] exp_1;

        if (this.coefficients.length>p.coefficients.length) {
            coeff_1 = new double[this.coefficients.length][p.coefficients.length];
            exp_1 = new int[this.coefficients.length][p.coefficients.length];

            for (int i = 0; i < this.coefficients.length; i++) {
                double [] temp_coeff = new double[p.coefficients.length];
                int [] temp_exp = new int[p.coefficients.length];
                for (int j = 0; j < p.coefficients.length; j++) {
                    temp_coeff[j] = this.coefficients[i] * p.coefficients[j];
                    temp_exp[j] = this.powers[i] + p.powers[j];
                }
                coeff_1[i] = temp_coeff;
                exp_1[i] = temp_exp;
            }
        }
        else {
            coeff_1 = new double[p.coefficients.length][this.coefficients.length];
            exp_1 = new int[p.coefficients.length][this.coefficients.length];

            for (int i = 0; i < p.coefficients.length; i++) {
                double [] temp_coeff = new double[this.coefficients.length];
                int [] temp_exp = new int[this.coefficients.length];
                for (int j = 0; j < this.coefficients.length; j++) {
                    temp_coeff[j] = p.coefficients[i] * this.coefficients[j];
                    temp_exp[j] = p.powers[i] + this.powers[j];
                }
                coeff_1[i] = temp_coeff;
                exp_1[i] = temp_exp;
            }
        }

        Polynomial curr_sum = new Polynomial(coeff_1[0], exp_1[0]);

        for(int i = 1; i < coeff_1.length; i++){
            Polynomial temp_p = new Polynomial(coeff_1[i], exp_1[i]);
            curr_sum = add2(curr_sum, temp_p);
        }

        return curr_sum;
    }
    

    public double evaluate(double x) {
        double sum = 0.0;
        for (int i = 0; i < this.coefficients.length; i++) {
            sum += this.coefficients[i] * Math.pow(x, this.powers[i]);
        }
        return sum; 
    }

    public boolean hasRoot(double x) {
	    return evaluate(x) == 0.0; 
    }
}
