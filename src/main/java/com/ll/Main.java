package com.ll;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Map<Integer,Model > map = new HashMap<>();
    static int mapCount=1;
    public static void main(String[] args) {
        int a = 0;
        // 입력을 받아오기 위한 Scanner
        Scanner sc = new Scanner(System.in);
        Main main =new Main();
        main.load();


        System.out.println("=== 명 언 앱 ===");
        // APP 실행 !
        while (true) {
            System.out.print("명령어) ");
            String inputData = sc.nextLine();

            // 등록
            if (inputData.equals("등록")) {

                Model model = new Model();
                System.out.print("명언 : ");
                String saying = sc.nextLine();
                model.saying = saying;
                System.out.print("작가 : ");
                String author = sc.nextLine();
                model.author = author;
                String[] set = {saying, author};
                map.put(mapCount, model);
                System.out.println(mapCount + "번 명언이 등록되었습니다.");
                mapCount++;
            }

            // 목록
            else if (inputData.equals("목록")) {
                if (map.isEmpty()) {
                    System.out.println("등록된 명언이 없습니다.");
                } else {
                    System.out.printf("\t번호\t/\t작가\t/\t명언%n");
                    System.out.println("----------------------------------------");

                    for (Integer key: map.keySet()) {
                        try {
                            System.out.printf("\t%1$d\t\t/ \t%2$s\t / \t%3$s\n", key, map.get(key).author, map.get(key).saying);

                        }catch (NullPointerException e){
                            continue;
                        }
                    }
//                    for (int i = count - 1; i > 0; i--) {
//                        try {
//                            System.out.printf("\t%1$d\t\t/ \t%2$s\t / \t%3$s", i, map.get(i)[1], map.get(i)[0]);
//                            System.out.println();
//                        } catch (NullPointerException e) {
//                            continue;
//                        }
//                    }
                }
            }

            // 종료
            else if (inputData.equals("종료")) {
                System.out.println("프로그램이 종료됩니다.");
                main.save();
                return;
            }

            // 삭제
            else if (inputData.contains("삭제?id=")) {
                int rm = Integer.parseInt(inputData.replaceAll("\\D", ""));
                if (map.containsKey(rm)) {
                    map.remove(rm);
                    System.out.println(rm + "번 명언이 삭제되었습니다.");
                } else {
                    System.out.println(rm + "번 명언은 존재하지 않습니다.");
                }

            }

            // 수정
            else if (inputData.contains("수정?id=")) {
                int change = Integer.parseInt(inputData.replaceAll("\\D", ""));
                System.out.println("명언(기존) : " + map.get(change).saying);
                System.out.print("명언 : ");
                map.get(change).saying = sc.nextLine();
                System.out.println("작가(기존) : " + map.get(change).author);
                System.out.print("작가 : ");
                map.get(change).author = sc.nextLine();
            }
            else if (inputData.equals("빌드")) {
                main.json();
            }
        }

    }

    public void save() {
        try {
            BufferedWriter bW = new BufferedWriter(new FileWriter("Test.txt"));
            for (Integer key : map.keySet()) {
                String objectText=String.format("%1$d/%2$s/%3$s", key, map.get(key).author, map.get(key).saying);
                System.out.println("objectText = "+objectText);
                bW.write(objectText);
                bW.newLine();
            }
            bW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(){
        try {
            BufferedReader bR = new BufferedReader(new FileReader("Test.txt"));
            String line ="";
            while ((line=bR.readLine())!=null){
                String[] wiseSet =line.split("/");
                System.out.println("세이브 파일 로드 중...");
                if (wiseSet.length==3){
                    int count_wiseSet= Integer.parseInt(wiseSet[0]);
                    String loadAuthor=wiseSet[1];
                    String loadSaying=wiseSet[2];

                    Model model =new Model();
                    model.setAuthor(loadAuthor);
                    model.setSaying(loadSaying);
                    map.put(count_wiseSet, model);
                    mapCount=count_wiseSet+1;
                }
            }
        }catch (IOException e){
            System.out.println("저장된 데이터가 없습니다.");

        }
    }
    public void json(){
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

