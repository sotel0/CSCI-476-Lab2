/*
Bradley White and Isaac Sotelo
CSCI 476: Lab 2
February 01, 2017
 */

//https://crackstation.net/buy-crackstation-wordlist-password-cracking-dictionary.htm
// Most of these lists were from https://github.com/danielmiessler/SecLists

import java.io.*;
import java.util.*;
import java.security.MessageDigest;

public class Main {

    static String dictPath = "./actualpasswords.txt";
    //static String dictPath = "./rockyou.txt"; // contains 181003, 41167
    //static String dictPath = "./.txt";
    //static String dictPath = "./Top1M.txt"; // contains 181003, 41167, lion8888
    //static String dictPath = "./md5decryptor.uk.txt"; // contains 181003, 41167, wakemeupwhenseptemberends
    static HashMap<String, String> dict = new HashMap<>();
    static String[] hashCode = {
            "6f047ccaa1ed3e8e05cde1c7ebc7d958", // 181003
            "275a5602cd91a468a0e10c226a03a39c", // 41167
            "b4ba93170358df216e8648734ac2d539", // QINGFANG
            "dc1c6ca00763a1821c5af993e0b6f60a", // lion8888
            "8cd9f1b962128bd3d3ede2f5f101f4fc", // victorboy
            "554532464e066aba23aee72b95f18ba2"}; // wakemeupwhenseptemberends

    public static void main(String[] args) {

        readIn();
        findValues();

    }

    private static void findValues() {
        //String[] results = null;
        for(String str : hashCode){
            if(dict.containsKey(str)){
                System.out.printf("The password for hash value %s is %s.%n", str, dict.get(str));
            }
        }
        //find in dictionary
        //keep track of time
        //System.out.printf("The password for hash value %s is %s, it takes %n the program %.4f sec to recover this password%n", hash, pass, time);
        //return results;
    }

    private static void readIn() {
        File dictFile = new File(dictPath);
        String str = null;

        try {
            BufferedReader in = new BufferedReader(new FileReader(dictFile));
            int counter = 0;
            while ((str = in.readLine()) != null) { //read in all the passwords

                //use md5 to convert it to hexadecimal
                //System.out.println(str);
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(str.getBytes());
                byte byteData[] = md.digest();

                //convert the byte to hexadecimal
                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < byteData.length; i++) {
                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                }

                //place inside hashMap
                //System.out.println(sb.toString());
                dict.put(sb.toString(), str);

                if (counter == 50) { //testing purposes
                    break;
                }
            }

            in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
