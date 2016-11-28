/**
 * Created by jun on 16-11-28.
 * 定义一个远程接口
 */
import java.rmi.Remote;
public interface  IHello extends Remote{
    public String showTime(String ip) throws java.rmi.RemoteException;
    public boolean authorization(String token, String ip) throws java.rmi.RemoteException;
}
