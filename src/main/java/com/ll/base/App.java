package com.ll.base;


import com.ll.domain.quotation.QuotationController;

import java.util.Scanner;

public class App {
    private Scanner sc ;

    public App(){
        sc=new Scanner(System.in);
    }
    public  void run() {
        App app =new App();
        QuotationController quotationController=new QuotationController(sc);
        quotationController.actionload();
        System.out.println("=== 명 언 앱 ===");
        // APP 실행 !
        while (true) {
            System.out.print("명령어 ) ");
            String cmd = sc.nextLine();
            Rq rq =new Rq(cmd);
            switch (rq.getAction()){
                case "등록":
                    quotationController.actionWrite();
                    break;
                case "목록":
                    quotationController.actionList();
                    break;
                case "수정":
                    quotationController.actionmodify(rq);
                    break;
                case "삭제":
                    quotationController.actionRemove(rq);
                    break;
                case "빌드":
                    quotationController.actionjson();
                    break;
                case "종료":
                    quotationController.actionsave();
                    return;
            }

//            // 등록
//            if (cmd.equals("등록")) {
//                actionWrite();
//            }
//
//            // 목록
//            else if (cmd.equals("목록")) {
//                actionlist();
//            }
//            // 종료
//            else if (cmd.equals("종료")) {
//                app.actionsave();
//                System.out.println("프로그램이 종료됩니다.");
//                return;
//            }
//            // 삭제
//            else if (cmd.contains("삭제?id=")) {
//                actionremove(cmd);
//            }
//            // 수정
//            else if (cmd.contains("수정?id=")) {
//                actionmodify(cmd);
//            }
//            else if (cmd.equals("빌드")) {
//                app.actionjson();
//            }
//            else {
//                System.out.println("명령어를 다르게 입력 했습니다.");
//                System.out.println("========목록========");
//
//            }

        }
    }



}


