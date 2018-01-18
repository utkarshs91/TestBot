package ReadTextFile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.*;
import java.util.*;
import javax.swing.GroupLayout.Alignment;

public class DirectoryChooser extends JFrame implements ActionListener {
	
	private JPanel contentPane;
	JButton BrowserBtn;
	JButton btnExecuteTestSuite;
	String choosertitle;
	JFileChooser chooser;
	String DirPath;
	String SuitePath;
	JDialog SomethingWentWrong;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DirectoryChooser frame = new DirectoryChooser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public DirectoryChooser () {
	
		setResizable(false);
		setTitle("Appian Test BOT 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 977, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		final JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(SystemColor.menu);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		BrowserBtn = new JButton("Choose Test Suite Direcotry");
		BrowserBtn.addActionListener(this);
		
		btnExecuteTestSuite = new JButton("Execute Test Suite");
		btnExecuteTestSuite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				try {
					
					ReadingUserInputFile.RunTestSuite(SuitePath);
					btnExecuteTestSuite.setEnabled(false);
					
				} catch (InterruptedException | FileNotFoundException e) {
					
					JOptionPane.showMessageDialog(new JFrame(), "Something Went Wrong!!!!\nEither report is opened somewhere else or there was interruption\nPlease restart the application and try again", "OOPS!!!!",
					        JOptionPane.ERROR_MESSAGE);
					btnExecuteTestSuite.setEnabled(false);
				
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnExecuteTestSuite.setEnabled(false);
		GroupLayout gl_desktopPane = new GroupLayout(desktopPane);
		gl_desktopPane.setHorizontalGroup(
			gl_desktopPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addContainerGap(273, Short.MAX_VALUE)
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnExecuteTestSuite, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(BrowserBtn, GroupLayout.PREFERRED_SIZE, 417, GroupLayout.PREFERRED_SIZE))
					.addGap(271))
		);
		gl_desktopPane.setVerticalGroup(
			gl_desktopPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addGap(156)
					.addComponent(BrowserBtn, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addGap(53)
					.addComponent(btnExecuteTestSuite, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(206, Short.MAX_VALUE))
		);
		desktopPane.setLayout(gl_desktopPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		 int result;
	        
		    chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle(choosertitle);
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    //
		    // disable the "All files" option.
		    //
		    chooser.setAcceptAllFileFilterUsed(false);
		    //    
		    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
		      System.out.println("getCurrentDirectory(): " 
		         +  chooser.getCurrentDirectory());
		      
		      DirPath = chooser.getCurrentDirectory().toString();
		      
		      System.out.println("Directory Path - "+DirPath);
		      btnExecuteTestSuite.setEnabled(true);
		      
		      System.out.println("getSelectedFile() : " 
		         +  chooser.getSelectedFile());
		      
		      SuitePath = chooser.getSelectedFile().toString();
		      System.out.println("Test Suite Path - "+SuitePath);
		      }
		    else {
		      System.out.println("No Selection ");
		      }
		     }
	}