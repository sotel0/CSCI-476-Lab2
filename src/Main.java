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
    static HashMap<String, String> dict = new HashMap<>();
    static long startTime, endTime;
    static List<String> hashCode = new ArrayList<String>(Arrays.asList(
            "6f047ccaa1ed3e8e05cde1c7ebc7d958", // 181003
            "275a5602cd91a468a0e10c226a03a39c", // 41167
            "b4ba93170358df216e8648734ac2d539", // QINGFANG
            "dc1c6ca00763a1821c5af993e0b6f60a", // lion8888
            "8cd9f1b962128bd3d3ede2f5f101f4fc", // victorboy
            "554532464e066aba23aee72b95f18ba2")); // wakemeupwhenseptemberends
    //static List<Float> results = new ArrayList<Float>();;
    static float[] results = new float[hashCode.size()];

    public static void main(String[] args) {
        readIn();
        findValues();
        System.out.println();
        method2();
    }

    private static void findValues() {
        for (String str : hashCode) {
            startTime = System.nanoTime();
            if (dict.containsKey(str)) {
                endTime = System.nanoTime();
                //System.out.println(endTime-startTime);
                System.out.printf("The password for hash value %s is %s, it takes the program %.3f milliseconds to recover this password.%n", str, dict.get(str), (float) (endTime - startTime) / 1000000.0);
            }
        }
    }

    private static void readIn() {
        File dictFile = new File(dictPath);
        String str = null;

        try {
            BufferedReader in = new BufferedReader(new FileReader(dictFile));
            MessageDigest md = MessageDigest.getInstance("MD5");
            startTime = System.currentTimeMillis();
            while ((str = in.readLine()) != null) { //read in all the passwords

                //use md5 to convert it to hexadecimal
                //System.out.println(str);
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
            }
            endTime = System.currentTimeMillis();
            in.close();
            System.out.printf("It took the program %.3f seconds to populate the data structure, and it contains %d entries.%n", (float) (endTime - startTime) / 1000.0, dict.size());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void method2() {
        File dictFile = new File(dictPath);
        String str = null;
        int found = 0;

        try {
            BufferedReader in = new BufferedReader(new FileReader(dictFile));
            MessageDigest md = MessageDigest.getInstance("MD5");
            startTime = System.currentTimeMillis();
            while ((str = in.readLine()) != null) { //read in all the passwords

                //use md5 to convert it to hexadecimal
                //System.out.println(str);

                md.update(str.getBytes());
                byte byteData[] = md.digest();

                //convert the byte to hexadecimal
                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < byteData.length; i++) {
                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                }

                String temp = sb.toString();
                //place inside hashMap
                //System.out.println(sb.toString());
                for (String hash : hashCode) {
                    if (temp.equals(hash)) {
                        endTime = System.currentTimeMillis();
                        //System.out.println(endTime-startTime);
                        float result = (float) ((endTime - startTime) / 1000.0);
                        System.out.printf("The password for hash value %s is %s, it takes the program %.3f seconds to recover this password.%n", hash, str, result);
                        hashCode.remove(hash);
                        results[found] = result;
                        found++;
                        //System.out.println(hashCode.size());
                        startTime = System.currentTimeMillis();
                        break;
                    }
                }

            }
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
