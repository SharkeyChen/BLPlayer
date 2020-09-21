//import java.awt.*;
//import java.util.Comparator;
//
////弹幕类
//public class Barrage{
//    String str;
//    Font font;
//    Color color;
//    int x;
//    int y;
//    float stamp;
//    int width;
//    int height;
//    public Barrage(){
//        this.font = new Font("宋体",Font.BOLD, 20);
//        this.color = Color.RED;
//        this.str = "这是空弹幕";
//        this.x = 874;
//        this.y = 400;
//    }
//    public Barrage(String k,int y,int x, float stamp){
//        this.str = k;
//        this.color = Color.RED;
//        this.font = new Font("宋体",Font.BOLD, 20);
//        this.x = x;
//        this.y = y;
//        this.height = 0;
//        this.width = 0;
//        this.stamp = stamp;
//    }
//    public boolean Border_Judge(){
//        if(this.x <= -this.width){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
//
//}
