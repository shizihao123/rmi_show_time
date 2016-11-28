import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.rmi.Naming;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class clientui extends JFrame
{
    public Label authcode=new Label("授权码");
    public Label ip =new Label("服务器ip:port");
    public TextField txtauthcode =new TextField();
    public TextField txtip =new TextField();
    public Button btok=new Button("获取时间");
    public Label timelabel= new Label("当前时间：");
    public boolean flag = true;
    public IHello hello = null;
    public  InetAddress ia=null;

    public class ThreadMain extends Thread {
        @Override
        public void run() {
            super.run();
            while(flag){
                try {
                    String res = hello.showTime(ia.getHostAddress().toString());
                    System.out.println(res);
                    timelabel.setText(res);
                    try
                    {
                        Thread.currentThread().sleep(1000);//毫秒
                    }
                    catch(Exception e){}
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public clientui()
    {
        setTitle("基于RPC时间获取系统");
        setLayout(null);

        setResizable(false);
        setSize(500,350);

        Dimension scr=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frm=this.getSize();
        setLocation((scr.width-frm.width)/2,(scr.height-frm.height)/2-18);
        txtauthcode.setEchoChar('*');
        authcode.setBounds(30, 50, 80, 27);
        ip.setBounds(30, 30, 80, 27);
        txtauthcode.setBounds(200, 60, 120, 27);
        txtip.setBounds(200, 30, 120, 27);
        btok.setBounds(100,100,100,28);
        timelabel.setBounds(200,200,200,28);
        add(authcode);
        add(txtauthcode);
        add(ip);
        add(txtip);
        add(btok);
        add(timelabel);
        setVisible(true);
        timelabel.setVisible(false);
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                flag = false;
                super.windowClosing(e);    //To change body of overridden methods use File | Settings | File Templates.
                System.out.println("windowClosing");
                System.exit(0);
            }
        });

        btok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                    try {
                         System.out.println(txtip.getText().toString());
                         hello = (IHello) Naming.lookup("rmi://" + txtip.getText().toString()+"/hello");
                    }catch (Exception e){
                        txtip.setText("错误的ip:port，请重新输入！");
                        return;
                    }

                String localname="";
                String localip="127.0.0.1";
                try {
                    try {
                        ia=ia.getLocalHost();
                        localname=ia.getHostName();
                        localip=ia.getHostAddress();
                        System.out.println("本机名称是："+ localname);
                        System.out.println("本机的ip是 ："+localip);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    boolean isValid = hello.authorization(txtauthcode.getText().toString(), ia.getHostAddress().toString());
                    if(isValid) {
                        clientui.this.remove(authcode);
                        clientui.this.remove(txtauthcode);
                        clientui.this.remove(ip);
                        clientui.this.remove(btok);
                        clientui.this.remove(txtip);
                        timelabel.setVisible(true);
                        timelabel.setBounds(50,0,200,28);
                        clientui.this.setSize(250,40);
                        ThreadMain m1 = new ThreadMain();
                        m1.start();

                    }else{
                        txtip.setText("错误的授权码，请重新输入！");
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public static void main(String args[])
    {
        clientui dl=new clientui();
    }
}