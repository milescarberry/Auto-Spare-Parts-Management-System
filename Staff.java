import java.io.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.border.LineBorder;

class Staff extends JFrame implements ActionListener, ItemListener
{
	private JTabbedPane tabStaff;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private int iResult;
	private Vector vecColumn,vecRow,vecData;
	private int cols;
	
	private DefaultTableModel dTable;
	private JTable tableInfo;
	
	private Container Picture;
	private ImageIcon iPicture,iHome;
	
	private int iID,iPurchase,iSal;
	private Long liPhNo;
	private String sName,sAdd,sPost,sInput;
	private JLabel lIntro,lID,lName,lAdd,lPhNo,lPost,lSal,lPicture,lNote;
	private JTextField tID,tName,tAdd,tPhNo,tPost,tSal;
	private JComboBox cID;
	private ButtonGroup bg;
	private JRadioButton rAdd,rUpdate,rRemove;
	private Font f1,f2,f3;
	private JPanel pTable,pManage;
	
	private JSeparator sep,sep1;
	private JButton bRefresh,bSubmit,bClear,bHome;
	
	private Variables var;

	public Staff()
	{
		// CREATING OBJECT OF Variables
		var = new Variables();
		
		// SETTING FRAME ASPECTS
		setTitle("Staff");
		show();
		setResizable(false);
		setLocation(var.d.width/10-getWidth()/2,var.d.height/10-getHeight()/2);
		setSize(805,540);
		setJMenuBar(var.mb);
		
		// INITIALIZING and ADDING PICTURE
		Picture = getContentPane();
		getContentPane().setLayout(null);
		iPicture = new ImageIcon("C://b3//Auto Spares//Images//Horizon.jpg");
		lPicture = new JLabel(iPicture);
		
		Picture.add(lPicture);
		lPicture.setBounds(0,0,800,500);
		
		lIntro = new JLabel("Staff");
		lIntro.setFont(new Font("Times New Roman",Font.PLAIN,40));
		lIntro.setForeground(Color.WHITE);
		
		// DATE
		lPicture.add(var.lDate);
		var.lDate.setBounds(600,20,60,25);
		var.lDate.setForeground(Color.WHITE);
		
		lPicture.add(var.tDate);
		var.tDate.setBounds(665,20,110,25);
		
		// CONNECTING TO DATABASE
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:Auto spare","","");
			st = con.createStatement();
		}
		catch(SQLException se)
		{
			JOptionPane.showMessageDialog(this, "Sorry, unable to connect to database !!");
			dispose();
		}
		
		// INITIALIZING TABLE i.e. TAB "VIEW"
		bRefresh = new JButton("Refresh");
		bRefresh.setToolTipText("Refresh the above table");
		pTable = new JPanel();
		pTable.setLayout(null);
		try
		{
			st=con.createStatement();
			rs=st.executeQuery("select * from Staff");
			ResultSetMetaData md = rs.getMetaData();
			cols = md.getColumnCount();
			vecColumn = new Vector();
			vecData = new Vector();
			for(int i=1;i<=cols; i++)
            {
            	vecColumn.addElement(md.getColumnName(i));
            }
            while(rs.next())
            {
            	vecRow = new Vector(cols);
            	for (int i=1;i<=cols;i++)
                {
                	vecRow.addElement(rs.getObject(i));
                }
                vecData.addElement(vecRow);
            }
		}
		catch(SQLException sqle)
		{
			JOptionPane.showMessageDialog(this,"Unable to connect to the Database !");			
		}
		tableInfo = new JTable(vecData,vecColumn);
		tableInfo.setAutoscrolls(true);
        JScrollPane scrollBar = new JScrollPane(tableInfo,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		pTable.add(scrollBar);
		scrollBar.setBounds(0,0,694,350);
		pTable.add(bRefresh);
		bRefresh.setBounds(2,355,100,40);
		
		// INITIALIZING COMPONENTS OF TAB "MANAGE"
		f1 = new Font("Times New Roman",Font.PLAIN,20);
		f2 = new Font("Courier New",Font.PLAIN,15);
		f3 = new Font("Brush Script MT",Font.PLAIN,30);
		
		lID = new JLabel("ID");
		lID.setBounds(50,30,50,20);
		lID.setFont(f1);
		lName = new JLabel("Name");
		lName.setBounds(50,80,50,20);
		lName.setFont(f1);
		lAdd = new JLabel("Address");
		lAdd.setBounds(50,130,80,20);
		lAdd.setFont(f1);
		lPhNo = new JLabel("Phone no.");
		lPhNo.setBounds(50,180,120,20);
		lPhNo.setFont(f1);
		lPost = new JLabel("Staff post");
		lPost.setBounds(50,230,120,20);
		lPost.setFont(f1);
		lSal = new JLabel("Salary");
		lSal.setBounds(50,280,80,20);
		lSal.setFont(f1);
		
		lID.setEnabled(false);
		lName.setEnabled(false);
		lAdd.setEnabled(false);
		lPhNo.setEnabled(false);
		lPost.setEnabled(false);
		lSal.setEnabled(false);
		
		lNote = new JLabel("Please select an option from above !");
		lNote.setBounds(420,220,250,100);
		lNote.setFont(new Font("Times New Roman",Font.PLAIN,16));
		lNote.setForeground(Color.BLUE);
		
		tID = new JTextField();
		tID.setBounds(190,30,150,25);
		tID.setFont(f2);
		tName = new JTextField();
		tName.setBounds(190,80,150,25);
		tName.setFont(f2);
		tAdd = new JTextField();
		tAdd.setBounds(190,130,150,25);
		tAdd.setFont(f2);
		tPhNo = new JTextField();
		tPhNo.setBounds(190,180,150,25);
		tPhNo.setFont(f2);
		tPost = new JTextField();
		tPost.setBounds(190,230,150,25);
		tPost.setFont(f2);
		tSal = new JTextField();
		tSal.setBounds(190,280,150,25);
		tSal.setFont(f2);
		
		cID = new JComboBox();
		cID.setBounds(190,30,150,25);
		cID.setFont(f2);
		
		//Adding spare type to the Combo box
		try
		{	
			st=con.createStatement();
			rs=st.executeQuery("select * from Staff");
			while(rs.next())
			{
				cID.addItem(rs.getString("ID"));
			}
		}
		catch(SQLException sql)
		{
			JOptionPane.showMessageDialog(this,"Unable to connect to Database");
			System.out.print(sql);
		}
		
		tID.setEnabled(false);
		tName.setEnabled(false);
		tAdd.setEnabled(false);
		tPhNo.setEnabled(false);
		tPost.setEnabled(false);
		tSal.setEnabled(false);
		
		bg = new ButtonGroup();
		
		rAdd = new JRadioButton("Add new Staff");
		rAdd.setBounds(450,20,150,25);
		rAdd.setFont(f1);
		rUpdate = new JRadioButton("Update Staff");
		rUpdate.setBounds(450,65,150,25);
		rUpdate.setFont(f1);
		rRemove = new JRadioButton("Remove Staff");
		rRemove.setBounds(450,110,150,25);
		rRemove.setFont(f1);
		
		bg.add(rAdd);
		bg.add(rUpdate);
		bg.add(rRemove);
		
		// DEFINING BUTTONS
		bSubmit = new JButton("Submit");
		bSubmit.setBounds(150,345,150,40);
		bSubmit.setFont(f3);
		
		bClear = new JButton("Clear");
		bClear.setBounds(400,345,150,40);
		bClear.setFont(f3);
		
		// INITILIZING PANEL AND ADDING COMPONENTS TO IT
		pManage = new JPanel();
		pManage.setLayout(null);
		pManage.add(lID);	pManage.add(tID);	pManage.add(cID);
		pManage.add(lName);	pManage.add(tName);
		pManage.add(lAdd);	pManage.add(tAdd);
		pManage.add(lPhNo);	pManage.add(tPhNo);
		pManage.add(lPost);	pManage.add(tPost);
		pManage.add(lSal);	pManage.add(tSal);
		
		pManage.add(rAdd);
		pManage.add(rUpdate);
		pManage.add(rRemove);
		
		pManage.add(lNote);
		
		pManage.add(bSubmit);
		pManage.add(bClear);
		
		iHome = new ImageIcon("C://b3//Auto Spares//Images//Home icon.gif");
		bHome = new JButton(iHome);
		lPicture.add(bHome);
		bHome.setBounds(725,396,70,80);
		bHome.setToolTipText("Go to Home");
		
		sep = new JSeparator();
    	sep.setBounds(0,330,800,10);
 	    sep.setForeground(Color.LIGHT_GRAY);
		pManage.add(sep);
		
		sep1 = new JSeparator(JSeparator.VERTICAL);
		sep1.setBounds(400,0,1,330);
		sep1.setForeground(Color.LIGHT_GRAY);
		pManage.add(sep1);
		
		// INITIALIZING TABS
		tabStaff = new JTabbedPane();
		tabStaff.setBorder(new LineBorder(new Color(255,0,0),0));
		tabStaff.addTab("View",pTable);
		tabStaff.addTab("Manage",pManage);
		
		lPicture.add(tabStaff);
		tabStaff.setBounds(20,52,700,425);
		lPicture.add(lIntro);
		lIntro.setBounds(315,15,200,40);

		setLayout(null);
		
		// ADDING EVENT LISTENER
		rAdd.addItemListener(this);
		rUpdate.addItemListener(this);
		rRemove.addItemListener(this);
		
		bRefresh.addActionListener(this);
		bSubmit.addActionListener(this);
		bClear.addActionListener(this);
		cID.addActionListener(this);
		bHome.addActionListener(this);
		
		// MENU ITEMS
		var.miQuotInv.addActionListener(this);
		var.miOrder.addActionListener(this);
		var.miSpare.addActionListener(this);
		var.miStaff.addActionListener(this);
		var.miCustomer.addActionListener(this);
		var.miReports.addActionListener(this);
		var.miUser.addActionListener(this);
		var.miDistributor.addActionListener(this);
		var.miLogoff.addActionListener(this);
		var.miExit.addActionListener(this);
		
		addWindowListener(new WindowClass());
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		iResult=0;
		if(ae.getSource() == bRefresh)
		{
			dispose();
			new Staff();
		}
		if(ae.getSource() == bSubmit)
		{
			if(rAdd.isSelected())
			{
				try
				{
					iID = Integer.parseInt(tID.getText());
					sName = tName.getText();
					sAdd = tAdd.getText();
					liPhNo = Long.parseLong(tPhNo.getText());
					sPost = tPost.getText();
					iSal = Integer.parseInt(tSal.getText());
					boolean check = true, check1 = true;
					
					for(int i=0;i<sName.length();i++)
					{
						if(Character.isDigit(sName.charAt(i)))
						{
							check = false;
						}
					}
					
					for(int i=0;i<sPost.length();i++)
					{
						if(Character.isDigit(sPost.charAt(i)))
						{
							check1 = false;
						}
					}
					
					if((check == true) && (check1 == true))
					{
						if((sName.equals(""))||(sAdd.equals(""))||(liPhNo == 0)||(sPost.equals(""))||(iSal == 0))
						{
							JOptionPane.showMessageDialog(this,"Please enter the details properly !");
						}
						else
						{	
							//insert into Staff values('Chetan','Devlali',9066654287,'Manager',10000)
							iResult = st.executeUpdate("insert into Staff values("+iID+",'"+sName+"','"+sAdd+"',"+liPhNo+",'"+sPost+"',"+iSal+")");
							if(iResult==1)
							{
								// If Query succesful
								JOptionPane.showMessageDialog(this,"Record saved successfully");
								reset();												
							}
							else
							{
								//  Query failed
								JOptionPane.showMessageDialog(this,"Record not saved successfully");
								dispose();
							}
						}
					}
					else
					{
						if((check == false) && (check1 == true))
							JOptionPane.showMessageDialog(this,"Staff name should not contain any digits !!");
						else if((check == true) && (check1 == false))
							JOptionPane.showMessageDialog(this,"Staff post should not contain any digits !!");
						else
							JOptionPane.showMessageDialog(this,"Staff name and post should not contain any digits !!");
					}
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(this,"Please enter the valid data");
					clear();
				}
				catch(SQLException sql)
				{
					JOptionPane.showMessageDialog(this,"Unable to connect to Database");
					System.out.print("\n"+sql);			
				}
			}

			else if(rUpdate.isSelected())
			{
				try
				{
					iID = Integer.parseInt((String)cID.getSelectedItem());
					sName = tName.getText();
					sAdd = tAdd.getText();
					liPhNo = Long.parseLong(tPhNo.getText());
					sPost = tPost.getText();
					iSal = Integer.parseInt(tSal.getText());
					boolean check2 = true, check3 = true;
					
					for(int i=0;i<sName.length();i++)
					{
						if(Character.isDigit(sName.charAt(i)))
						{
							check2 = false;
						}
					}
					
					for(int i=0;i<sPost.length();i++)
					{
						if(Character.isDigit(sPost.charAt(i)))
						{
							check3 = false;
						}
					}

					if((check2 == true) && (check3 == true))
					{
						if((sName.equals(""))||(sAdd.equals(""))||(liPhNo == 0)||(sPost.equals(""))||(iSal == 0))
						{
							JOptionPane.showMessageDialog(this,"Please enter the details properly !");
						}
						else
						{
							//update Staff set Name='Harshal',Add='Artillery',Phone=2660788,Post='Assistant',Salary=7500 where ID=2
							iResult = st.executeUpdate("update Staff set Name='"+sName+"',Address='"+sAdd+"',Phone="+liPhNo+",Post='"+sPost+"',Salary="+iSal+" where ID="+iID+"");
							if(iResult==1)
							{
								// If Query succesful
								JOptionPane.showMessageDialog(this,"Record updated successfully");
								reset();
							}
							else
							{
								//  Query failed
								JOptionPane.showMessageDialog(this,"Record not saved successfully");
								clear();
							}
						}
					}
					else
					{
						if((check2 == false) && (check3 == true))
							JOptionPane.showMessageDialog(this,"Staff name should not contain any digits !!");
						else if((check2 == true) && (check3 == false))
							JOptionPane.showMessageDialog(this,"Staff post should not contain any digits !!");
						else
							JOptionPane.showMessageDialog(this,"Staff name and post should not contain any digits !!");
					}
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(this,"Please enter the valid data");
					clear();
				}
				catch(SQLException sql)
				{
					JOptionPane.showMessageDialog(this,"Unable to connect to Database");
					System.out.print(sql);
					clear();			
				}	
			}
			
			else if(rRemove.isSelected())
			{
				try
				{
					iID = Integer.parseInt(tID.getText());

					iResult = st.executeUpdate("delete * from Staff where ID="+iID+"");
					if(iResult==1)
					{
						// If Query succesful
						JOptionPane.showMessageDialog(this,"Record deleted successfully");
						reset();
					}
					else
					{
						//  Query failed
						JOptionPane.showMessageDialog(this,"The staff member with provided ID does not exist");
						clear();
					}
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(this,"Please enter the valid data");
					clear();
				}
				catch(SQLException sql)
				{
					JOptionPane.showMessageDialog(this,"Unable to connect to database");
					System.out.print(sql);
					clear();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Please select an option !");
			}
		}
		
		if(ae.getSource() == cID)
		{
			try
			{	
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs=st.executeQuery("select * from Staff where ID="+(cID.getSelectedItem()));
				rs.first();
				tName.setText(""+rs.getString("Name"));
				tAdd.setText(rs.getString("Address"));
				tPhNo.setText(""+rs.getInt("Phone"));
				tPost.setText(""+rs.getString("Post"));
				tSal.setText(""+rs.getInt("Salary"));
			}
			catch(SQLException sql)
			{
				System.out.print(sql);
				JOptionPane.showMessageDialog(this,"Unable to connect to Database");			
			}
		}

		if(ae.getSource() == bClear)
		{
			clear();
		}
		
		// MENU ITEMS
		if(ae.getSource() == var.miQuotInv)
		{
			new Quotation();
			dispose();
		}
		if(ae.getSource() == var.miOrder)
		{
			new Order();
			dispose();
		}
		if(ae.getSource() == var.miSpare)
		{
			new Spare();
			dispose();
		}
		if(ae.getSource() == var.miStaff)
		{
			new Staff();
			dispose();
		}
		if(ae.getSource() == var.miCustomer)
		{
			new Customer();
			dispose();
		}
		if(ae.getSource() == var.miReports)
		{
			new Reports();
			dispose();
		}
		if(ae.getSource() == var.miUser)
		{
			new User();
			dispose();
		}
		if(ae.getSource() == var.miDistributor)
		{
			new Distributor();
			dispose();
		}
		
		if(ae.getSource() == var.miLogoff)
		{
			int reply = JOptionPane.showConfirmDialog(null,"Are you sure you want to log off?","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				dispose();
				new Login();
			}
			else
			{
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
		if(ae.getSource() == var.miExit)
		{
			int reply = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				dispose();
			}
			else
			{
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
		
		
		if(ae.getSource() == bHome)
		{
			new Home();
			dispose();
		}
	}
	
	public void itemStateChanged(ItemEvent ie)
	{
		if(ie.getItem() == rAdd)
		{
			lNote.setText("<html>Please enter the details of the new<br>Staff member !</br></html>");
			clear();
			enable();
			tID.setEditable(false);
			tID.setVisible(true);
			cID.setVisible(false);
			try
			{	
				st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs=st.executeQuery("select * from Staff");
				rs.last();
				tID.setText(""+(1+rs.getInt("ID")));
			}
			catch(SQLException sql)
			{
				JOptionPane.showMessageDialog(this,"Unable to connect to Database");
				System.out.print(sql);			
			}
		}
		if(ie.getItem() == rUpdate)
		{
			lNote.setText("<html>Please enter the new details of the<br>Staff member to be updated !</br></html>");
			tID.setVisible(false);
			cID.setVisible(true);
			clear();
			enable();
		}
		if(ie.getItem() == rRemove)
		{
			lNote.setText("<html>Please enter the ID of the<br>Staff member to be removed !</br></html>");
			lID.setEnabled(true);	
			tID.setEnabled(true);
			tID.setEditable(true);
			tID.setVisible(true);
			cID.setVisible(false);
			clear();
			disable();
		}
	}
	
	public void enable()
	{
		lID.setEnabled(true);	
		lName.setEnabled(true);
		lAdd.setEnabled(true);
		lPhNo.setEnabled(true);
		lPost.setEnabled(true);
		lSal.setEnabled(true);
		
		tID.setEnabled(true);
		tID.setEditable(true);
		tName.setEnabled(true);
		tAdd.setEnabled(true);
		tPhNo.setEnabled(true);
		tPost.setEnabled(true);
		tSal.setEnabled(true);
	}
	
	public void disable()
	{
		lName.setEnabled(false);
		lAdd.setEnabled(false);
		lPhNo.setEnabled(false);
		lPost.setEnabled(false);
		lSal.setEnabled(false);
		
		tName.setEnabled(false);
		tAdd.setEnabled(false);
		tPhNo.setEnabled(false);
		tPost.setEnabled(false);
		tSal.setEnabled(false);
	}
	
	public void clear()
	{
		if(rAdd.isSelected())
		{
				try
				{	
					st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
					rs=st.executeQuery("select * from Staff");
					rs.last();
					tID.setText(""+(1+rs.getInt("ID")));
				}
				catch(SQLException sql)
				{
					JOptionPane.showMessageDialog(this,"Unable to connect to Database");
					System.out.print(sql);			
				}
		}
		else
		{
			tID.setText("");
		}
		tName.setText("");
		tAdd.setText("");
		tPhNo.setText("");
		tPost.setText("");
		tSal.setText("");
	}
	
	public void reset()
	{
		bg.clearSelection();
		lNote.setText("Please select an option from above !");
		lID.setEnabled(false);
		tID.setEnabled(false);
		disable();
		clear();
	}
	
	class WindowClass extends WindowAdapter
	{
		public void windowClosing(WindowEvent we)
		{
			int reply = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?","",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply == JOptionPane.YES_OPTION)
			{
				dispose();
			}
			else
			{
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
	}
	
	public static void main(String args[])
	{
		new Staff();
	}
	
}