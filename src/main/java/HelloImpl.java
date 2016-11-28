/**
 * Created by jun on 16-11-28.
 *实现远程接口（服务端就在次远程接口的实现类中）
 */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.net.*;
import java.util.Scanner;

public class HelloImpl extends UnicastRemoteObject implements IHello {
    HashSet<String> authIds = new HashSet<String>();
    protected HelloImpl()throws RemoteException{
        super();
    }

    /*说明清楚此属性业务含义*/
    private static final long serialVersionUID = 4077329331699640331L;

    public boolean authorization(String token, String ip) throws RemoteException {
        if(token.equalsIgnoreCase("666")) {
            authIds.add(ip);
            return true;
        }
        return false;
    }

    public String showTime(String ip) throws RemoteException {
        if(!authIds.contains(ip)) {
            return "permission denied";
        }else{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String str = df.format(new Date());// new Date()为获取当前
            return str;
        }
    }

    public static void  main(String[] args){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("输入rpc服务端端口:");
            String port = sc.next();
            IHello hello = new HelloImpl();
            LocateRegistry.createRegistry(Integer.parseInt(port)); //加上此程序，就可以不要在控制台上开启RMI的注册程序，1099是RMI服务监视的默认端口
            InetAddress ia=null;
            ia=ia.getLocalHost();
            String localname=ia.getHostName();
            String localip=ia.getHostAddress();
            java.rmi.Naming.rebind("rmi://"+localip + ":" + port+"/hello", hello);
            System.out.println("Server ip is: " + localip);
            System.out.println("Server name is: " + localname);
            System.out.println("Ready...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
