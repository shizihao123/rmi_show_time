/**
 * Created by jun on 16-11-28.
 * 新建RMI客户端调用程序
 */
import java.rmi.Naming;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.net.*;
import java.util.Scanner;
import jline.ConsoleReader;
import java.lang.Thread;
//package datetime;
import  java.io.Console;


public class Hello_RMI_Client {

    public static void main(String[] args){
        try{
            IHello hello = (IHello)Naming.lookup("rmi://172.28.55.81:1097/hello");
            InetAddress ia=null;
            try {
                ia=ia.getLocalHost();
                String localname=ia.getHostName();
                String localip=ia.getHostAddress();
                System.out.println("本机名称是："+ localname);
                System.out.println("本机的ip是 ："+localip);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            clientui dl= new clientui();

            boolean isValid = false;
            while (!isValid){
                Scanner sc = new Scanner(System.in);
                System.out.println("输入授权token:");
                String token = sc.next();
                isValid = hello.authorization(token, ia.getHostAddress().toString());
                if(!isValid){
                    System.out.println("token不合法, 请重新输入：");
                }
            }

            showtimeapp sh = new showtimeapp("");
            while (isValid) {
                try {
                    String res = hello.showTime(ia.getHostAddress().toString());
                    sh.run(res);
                    try
                    {
                        Thread.currentThread().sleep(1000);//毫秒
                    }
                    catch(Exception e){}
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
