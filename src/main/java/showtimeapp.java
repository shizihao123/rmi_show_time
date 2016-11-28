
import java.awt.*;
import javax.swing.*;
import java.lang.Thread;

public class showtimeapp {
    JFrame frame;
    Container contentPane;
    JPanel panel;
    JLabel label;
    public showtimeapp(String time){
        frame = new JFrame("current time");
        contentPane = frame.getContentPane();
        contentPane.setBackground(Color.CYAN); // 将JFrame实例背景设置为蓝绿色
        panel = new JPanel(); // 创建一个JPanel的实例
        panel.setBackground(Color.WHITE); // 将JPanel的实例背景设置为蓝色
        label = new JLabel(time);
        panel.add(label); // 将JButton实例添加到JPanel中
        contentPane.add(panel, BorderLayout.CENTER); // 将JPanel实例添加到JFrame的南侧
        frame.setSize(200, 30);
        frame.setVisible(true);
    }

    public void run(String time){
        label.setText(time);
    }

    public static void main(String[] args) {
       showtimeapp sh = new  showtimeapp("hello");
        Integer i = 0;
        while(true) {
            try {
                Thread.currentThread().sleep(1000);//毫秒
            } catch (Exception e) {
            }
            sh.run(i.toString());
            i += 1;
        }

    }
}