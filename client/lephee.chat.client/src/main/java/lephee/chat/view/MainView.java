package lephee.chat.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import io.netty.channel.Channel;
import lephee.chat.client.ChatClient;
import lephee.chat.client.msg.ChatMsg1003;
import lephee.chat.client.msg.LoginMsg1001;

public class MainView extends JFrame {
	
	Channel channel;
	
	JPanel panel1;
	
	JPanel panel2;
	
	JTextField usrnmView;
	JTextField pwdView;
	
	JButton commitBtn;
	
	JScrollPane scrollPane;
	JTextPane chatPane;
	JTextField msgView;
	JButton sendBtn;
	
	int roleId;
	
	public MainView(Channel channel) {
		this.channel = channel;
		
		setTitle("LePheeChat");
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		initComponents();
		
		add(panel1, BorderLayout.CENTER);
		panel1.setLayout(new FlowLayout());
		panel1.add(usrnmView, BorderLayout.CENTER);
		panel1.add(pwdView, BorderLayout.CENTER);
		add(commitBtn, BorderLayout.SOUTH);
		
		commitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usrnmView.getText();
				String password = pwdView.getText();
				if (username.trim().isEmpty() || password.trim().isEmpty()) {
					System.out.println("ERROR");
					return;
				}
				MainView.this.channel.writeAndFlush(new LoginMsg1001(username, password));
			}
		});
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public void changeView(){
		panel1.remove(usrnmView);
		panel1.remove(pwdView);
		remove(commitBtn);
		
		add(panel2, BorderLayout.SOUTH);
		add(scrollPane, BorderLayout.CENTER);
		panel2.add(msgView, BorderLayout.SOUTH);
		panel2.add(sendBtn, BorderLayout.SOUTH);
		
		msgView.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					String content = msgView.getText();
					if (content.isEmpty()) {
						return;
					}
					channel.writeAndFlush(new ChatMsg1003(roleId, content));
					msgView.setText("");
				}
			}
			
			public void keyReleased(KeyEvent e) {
				
			}
			
			public void keyPressed(KeyEvent e) {
				
			}
		});
		
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String content = msgView.getText();
				if (content.isEmpty()) {
					return;
				}
				channel.writeAndFlush(new ChatMsg1003(roleId, content));
				msgView.setText("");
			}
		});
		
		validate();
		repaint();
		panel2.validate();
		panel2.repaint();
	}
	
	public void receiveChatMsg(String nickname, String content) {
		chatPane.setText(chatPane.getText() + "\n" + nickname + ": " + content);
		scrollPane.validate();
		scrollPane.repaint();
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()-1);
	}
	
	public void initComponents() {
		usrnmView = new JTextField(10);
		pwdView = new JTextField(10);
		commitBtn = new JButton("Login");
		chatPane = new JTextPane();
		sendBtn = new JButton("send");
		msgView = new JTextField(15);
		panel1 = new JPanel();
		panel2 = new JPanel();
		scrollPane = new JScrollPane(chatPane);
	}
	
	
	public static void main(String[] args) {
		new ChatClient().run();
	}

}
