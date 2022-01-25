
import java.io.File;
//@author Sumantra
public class FirstBoot {

    public FirstBoot()
    {
        
    }

    private void txtchecker() {
        
                         File file1 = new File("AdminPassword.txt");
                         File file2 = new File("Server.xml");
                         if(!file1.exists())
                         {
                             new SetPassword(file1,file2);
                         }
                         
                         else
                             new AdminLogin();
                         
    }
    
public static void main(String srgs[])
{
    new FirstBoot().txtchecker();
}
}