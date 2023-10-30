package com.ll.domain.quotation;

import com.ll.base.App;
import com.ll.base.Rq;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QuotationController {
    private Scanner sc;
    private static Map<Integer, Model> map;
    static int mapCount;

    public QuotationController(Scanner sc){
        this.sc=sc;
        mapCount=1;
        map=new HashMap<>();
    }

    void write(String content, String author){
        Model model =new Model(content,author);
        map.put(mapCount,model);
        mapCount++;
    }
    public void actionWrite(){
        System.out.print("명언 : ");
        String saying = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();
        System.out.printf("명언 : %s\t작가 : %s\n",saying,author);
        write(saying,author);


//        Model model = new Model();
//        model.saying = saying;
//        model.author = author;

//        map.put(mapCount, model);
//        System.out.println(mapCount + "번 명언이 등록되었습니다.");
//        mapCount++;
    }
    public void actionList(){
        System.out.printf("\t%s\t/\t%s\t/\t%s%n","순번","작가","명언");
        System.out.println("----------------------------------------");
        if (map.isEmpty()) {
            System.out.println("등록된 명언이 없습니다.");
        } else {
            for (Integer key: map.keySet()) {
                try {
                    System.out.printf("\t%1$d\t\t/ \t%2$s\t / \t%3$s\n", key, map.get(key).author, map.get(key).saying);
                }catch (NullPointerException e){
                }
            }
        }
    }
    public void actionRemove(Rq rq){
        int id = rq.getParamAsInt("id",0);
        if (id==0) {
            System.out.println("id를 정확히 입력해주세요.");
            return;
//            map.remove(id);
//            System.out.println(id + "번 명언이 삭제되었습니다.");
        }
        int index = findQuotationIndexById(id);
        if (index ==-1){
            System.out.printf("해당 %d 번의 명언은 존재하지 않습니다.\n",id);
            return;
        }
        map.remove(id);
        System.out.printf("%d번의 명언을 삭제 했습니다.\n",id);
    }
    public void actionmodify(Rq rq){
        int id = rq.getParamAsInt("id",0);

        if (id == 0){
            System.out.println("id를 정확히 입력해주세요.");
            return;
        }
        int index = findQuotationIndexById(id);
        if (index ==-1){
            System.out.printf("해당 %d 번의 명언은 존재하지 않습니다.\n",id);
            return;
        }
        System.out.println("명언(기존) : " + map.get(id).saying);
        System.out.print("명언 : ");
        map.get(id).saying = sc.nextLine();
        System.out.println("작가(기존) : " + map.get(id).author);
        System.out.print("작가 : ");
        map.get(id).author = sc.nextLine();
    }
    //    int removeString(String inputData){
//        int num= Integer.parseInt(inputData.replaceAll("\\D", ""));
//        return num;
//    }
    int findQuotationIndexById(int id) {
        int result = map.containsKey(id) ? id :  -1 ;
        return  result;
    }

    public void actionsave() {
        try {
            BufferedWriter bW = new BufferedWriter(new FileWriter("Test.txt"));
            for (Integer key : map.keySet()) {
                String objectText = String.format("%1$d/%2$s/%3$s", key, map.get(key).author, map.get(key).saying);
                System.out.println("objectText = " + objectText);
                bW.write(objectText);
                bW.newLine();
            }
            bW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionload(){
        try {
            BufferedReader bR = new BufferedReader(new FileReader("Test.txt"));
            String line ="";
            while ((line=bR.readLine())!=null){
                String[] wiseSet =line.split("/");
                System.out.println("세이브 파일 로드 중...");
                if (wiseSet.length==3){
                    int mapCount_load= Integer.parseInt(wiseSet[0]);
                    String loadAuthor=wiseSet[1];
                    String loadSaying=wiseSet[2];

//                    Model model =new Model();
//                    model.setAuthor(loadAuthor);
//                    model.setSaying(loadSaying);
                    Model model= new Model(loadSaying,loadAuthor);
                    map.put(mapCount_load, model);
                    mapCount=mapCount_load+1;
                }
            }
        }catch (IOException e){
            System.out.println("저장된 데이터가 없습니다.");

        }
    }
    public void actionjson(){
        JSONArray jsonArray= new JSONArray();

        for (Integer key : map.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",key);
            jsonObject.put("content",map.get(key).saying);
            jsonObject.put("author",map.get(key).author);

            jsonArray.add(jsonObject);
        }

        String json =jsonArray.toJSONString();
        Writer writer = null;
        try {
            writer = new FileWriter("jsontest.json", Charset.forName("UTF-8"));
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        System.out.println("jsontest.json 파일의 내영이 갱신되었습니다");

    }
}
