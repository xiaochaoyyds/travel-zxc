package com.baidu.travel.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityUtil {
    public static  String getR(String s,List<String> list) throws IOException {
      /*  InputStream resourceAsStream = CityUtil.class.getClass().getClassLoader().getResourceAsStream("city.txt");
        Reader reader=new InputStreamReader(resourceAsStream);
        BufferedReader bufferedReader=new BufferedReader(reader);
        String s5="";
        String a;
        while ((a=bufferedReader.readLine())!=null){
            s+=a;
        }

        String[] split = s.split("、");
        List<String> list=new ArrayList<>(999);
        for (String s1:split){
            list.add(s1);
        }*/
      //  String s1="我要去西安";
        List<String> strings=new ArrayList<String>();
        for (int i = 0; i <s.length()-1; i++) {
            String substring = s.substring(i, i + 2);
            if(i+3<=s.length()){
                String substring1 = s.substring(i, i + 3);

                strings.add(substring1);
            }
            if(i+4<=s.length()){
                String substring1 = s.substring(i, i + 4);

                strings.add(substring1);
            }

            strings.add(substring);
        }
        List<String> s2=new ArrayList<>();
        for (String string : strings) {
            if (list.contains(string)){
                s2.add(string);
            }
        }
        if (s2.size()==0){
            s2.add(0,"泸州");
        }

       // resourceAsStream.close();
       // reader.close();
      //  bufferedReader.close();
        return s2.get(0);
    }

}
