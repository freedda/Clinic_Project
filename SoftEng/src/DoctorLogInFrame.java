import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;




public class DoctorLogInFrame extends JFrame {
    private JPanel panel1,panel2, central_panel;
    private JTextField amField;
    private JTextField codeField;
    private JLabel label;
    private JButton move;
	private db conn;
	
   
	
    public DoctorLogInFrame(db connection) {
    	conn=connection;
    	central_panel = new JPanel(new BorderLayout());
		panel1 = new JPanel();
		panel2 = new JPanel();
		label = new JLabel("Doctor's Log in");
		amField = new JTextField("Input RN");
		move = new JButton("Continue");
		
		panel1.add(label);
		panel1.add(amField);
		//G_Panel.add(codeField);
		panel1.add(move);
	

		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		panel1.add(lb);
		
		lb.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
		    {	 
		    	setVisible(false);
		        new GlobalHomeFrame(conn);           
		    }
		});
		
		
		ButtonListener listener = new ButtonListener();
		move.addActionListener(listener);
	
		central_panel.add(panel1, BorderLayout.CENTER);
		central_panel.add(panel2, BorderLayout.SOUTH);
		this.setContentPane(central_panel);
		this.setVisible(true);
		this.setSize(530, 250);
		this.setTitle("������");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    

	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == move) {
				String AM = amField.getText();
				int NumberOfDocs;
				String password;
				
				Boolean flag=true;
		    	// ----------SEARCH IF THE DOCTOR ALREADY EXISTS------------
				NumberOfDocs = conn.getNumberOfEntriesWithCondition("doctor", "RN", AM);
				
	  			if (NumberOfDocs == -1)
	  			{
					 JOptionPane.showMessageDialog(panel1, "An unexpected error has occurred.");

	  			}
	  			else if (NumberOfDocs == 1 )
	  		    {
	  		 		// Search if the doctor has created his/her password
	  				password = conn.returnDoctorPassword(AM);
	  				if (password == null) // the password hasn't been created. CREATE NOW
	  				{
	  					JPasswordField create_password = new JPasswordField(10);
	  					JPasswordField check_password = new JPasswordField(10);
	  					JLabel label1 = new JLabel(" Create a Password");
	  					JLabel label2 = new JLabel(" Confirm Password");
	  					JButton confirm1 = new JButton("Done");
	  					
	  					panel2.removeAll();
	  					panel2.add(label1);
	  					panel2.add(create_password);
	  					panel2.add(label2 );
	  					panel2.add(check_password);
	  					panel2.add(confirm1);
	  					
	  					confirm1.addActionListener(new ActionListener()
	  					{	
	  						public void actionPerformed(ActionEvent e)
	  						{
	  							String pass1 = create_password.getText();
	  							String pass2 = check_password.getText();
	  							if(pass1.equals(pass2)) {
	  								
	  								//--------SAVE PASSWORD TO DATABASE-------
	  								conn.saveFieldDoctor("Password", pass1, AM);
	  								
	  								
	  								setVisible(false);
	  								new DoctorPreferenceFrame(conn, AM);
	  							}
	  							else {
	  								JOptionPane.showMessageDialog(panel2, "Passwords don't match. Try again!");
	  							}
	  						}
				     });
	  					pack();
	  				}
	  				else // The password has been set. LOG IN
	  				{
	  					JPasswordField give_password = new JPasswordField(10);
	  					JLabel label1 = new JLabel("Give your Password");
	  					JButton confirm2 = new JButton("Log In");
	  					
	  					panel2.removeAll();
	  					panel2.add(label1);
	  					panel2.add(give_password);
	  					panel2.add(confirm2);
	  					
	  					confirm2.addActionListener(new ActionListener()
	  					{	
	  						public void actionPerformed(ActionEvent e)
	  						{
	  							String read_pass = give_password.getText();
	  							if(password.equals(read_pass)) {
	  								// check if the doctor has already stated his/her preferences
	  								String pref = conn.returnDoctorTimetable(AM);
	  								
	  								if (pref == null)// he/she hasn't set his/her preferences. DO IT NOW
	  								{
		  								setVisible(false);
		  								new DoctorPreferenceFrame(conn, AM);
	  								}
	  								else // go to the doctor's home page. there are alredy preferences set
	  								{
		  								setVisible(false);	
	  									new DoctorHomePageFrame(conn, AM);
	  								}
	  							}
	  							else {
	  								//If you Can't remember your password?
	  								JOptionPane.showMessageDialog(panel2, "Wrong Password!");
	
	  							}
	  						}
				     });
	  				
	  					pack();
	  				}
	  		    }
	  			else
	  		    {
	  				// show that the doctor with the given AM doesn't exist
	  				JOptionPane.showMessageDialog(panel1, "The doctor with the RN " + AM + " doesn't exist");
	  		    }	
			}
			
		}
	}
}
