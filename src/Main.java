/*
Bradley White and Isaac Sotelo
CSCI 476: Lab 2
February 01, 2017
Dictionary URL: http://dazzlepod.com/site_media/txt/passwords.txt
 */

import java.io.*;
import java.util.*;
import java.security.MessageDigest;

public class Main {
    static String dictPath = "./passwords.txt";
    static long startTime, endTime;
    
    static List<String> hashCode = new ArrayList<String>(Arrays.asList(
            "6f047ccaa1ed3e8e05cde1c7ebc7d958", // 181003
            "275a5602cd91a468a0e10c226a03a39c", // 41167
            "b4ba93170358df216e8648734ac2d539", // QINGFANG
            "dc1c6ca00763a1821c5af993e0b6f60a", // lion8888
            "8cd9f1b962128bd3d3ede2f5f101f4fc", // victorboy
            "554532464e066aba23aee72b95f18ba2")); // wakemeupwhenseptemberends
    
    static float[] results = new float[hashCode.size()];

    public static void main(String[] args) {
        
        readIn();
    }

    private static void readIn() { //read in the password file and search for the passwords at the same time
        File dictFile = new File(dictPath);
        String str = null;
        int found = 0;

        try {
            BufferedReader in = new BufferedReader(new FileReader(dictFile));
            MessageDigest md = MessageDigest.getInstance("MD5");
            startTime = System.currentTimeMillis();
            while ((str = in.readLine()) != null) { //read in all the passwords

                //use md5 to convert it to hexadecimal
                md.update(str.getBytes());
                byte byteData[] = md.digest();

                //convert the byte to hexadecimal
                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < byteData.length; i++) {
                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                }
                
                //convert the hexadecimal into a string
                String temp = sb.toString();

                //check if the hexadecimal matches any of the desired hash values
                for (String hash : hashCode) {
                    if (temp.equals(hash)) {
                        
                        //keep track of the time taken to find the password
                        endTime = System.currentTimeMillis();
                        float result = (float) ((endTime - startTime) / 1000.0);
                        
                        System.out.printf("The password for hash value %s is %s, it takes the program %.3f seconds to recover this password.%n", hash, str, result);
                        
                        hashCode.remove(hash); //remove from searching lsit
                        
                        results[found] = result; //keep track of the resulting time
                        found++;

                        startTime = System.currentTimeMillis();
                        break;
                    }
                }

            }
            
            //calculate the total time taken
            float sum = 0;
            for(float time : results){
                sum += time;
            }

            System.out.printf("The total time taken to recover all passwords was %.4f seconds.%n",sum);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
