package icons;
import javax.swing.*;
/**
 * Created by macbury on 10.11.14.
 */
public  class Utils {
  public static ImageIcon getIcon(String name) {

    return new ImageIcon(Utils.class.getResource("./"+name+".png"));
  }
}