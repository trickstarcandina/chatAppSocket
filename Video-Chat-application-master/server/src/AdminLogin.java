
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
//@author Sumantra
 
public class AdminLogin extends javax.swing.JFrame {
        //stores admin password
	private String password;
	//get the looks and feel for nimbus
        private UIManager.LookAndFeelInfo looks[];
        //constructor
        public AdminLogin() {
            //frame nomenclature
            super("Video Chat Server");
            //initialise components
            initComponents();
            try
            {
               //setting the icon
               String dir = System.getProperty("user.dir");
               Image img= ImageIO.read(new File(dir+"\\"+"server.jpg"));
               setIconImage(img);
            }
            catch(Exception e)
            {
                System.out.println("Error image: " + e.getMessage());
            }
                //change the required look and feel and get rest all default the look and feels
                UIManager.put("nimbusBase", new Color(115,164,209));
                UIManager.put("nimbusBlueGrey", new Color(200,200,200));
                UIManager.put("control", new Color(115,164,209));
		looks = UIManager.getInstalledLookAndFeels();
		//set Nimbus as the look and feel
		 try 
		 {
			 for(int i=0;i<looks.length;i++)
			 {
				 if(looks[i].getName().equalsIgnoreCase("Nimbus"))
				 {
					 UIManager.setLookAndFeel(
							 looks[ i ].getClassName() );
					 SwingUtilities.updateComponentTreeUI( this );
					 break;
				 }
			 }
		 }	
		 catch ( Exception exception ) 
		 {
			 exception.printStackTrace();
		 }
                //what to do when window closed
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                //setting the size
		setSize(250,200);
                //setting the location
		setLocation(500,225);
                //whether it can be maximized or not
                setResizable(false);
                //visible on the screen or not
                setVisible(true);      
    }
    //form generated codes
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("WELCOME ADMIN");

        jLabel2.setText("Password");

        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Change Password");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPasswordField1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //read the password from the file
        password = readFile("AdminPassword.txt");
        //if given password is equal to the password set up
        if(jPasswordField1.getText().equals(password))
        {
            //show the layout
            new Layout();
            //set the admin login frame as invisible
            this.setVisible(false);
        }
        else
        {
            //show a message
            JOptionPane.showMessageDialog(null,"Wrong Password!! TRY AGAIN");
            //set the text as null
            jPasswordField1.setText("");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //call the change password class on clicking the button
        new ChangePassword();
        //set visibility of this frame as false
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPasswordField1;
    // End of variables declaration//GEN-END:variables

        //to be used to access password
        public String getPassword()
	{
		return password;
	}
    //to read the file
    private String readFile(String filename)
    {
        String content = null;
        File file = new File(filename); 
   
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
            } 
            catch(IOException e) {
                    e.printStackTrace();
            }
        return content;
    }
}
	

