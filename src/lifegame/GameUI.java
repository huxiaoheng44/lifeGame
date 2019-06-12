package lifegame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameUI extends JFrame implements ActionListener {
	public GameUI() {
	}
	private static GameUI frame;
	private JPanel backPanel,centerPanel,buttonPanel;
	private JButton btnNextGeneration, btnStart,btnExit;
	private JButton[][] btnBlock;
	private int row,col;
	private GameRule rule;
	private boolean[][] isSelected;
	private boolean isRunning;
	private Thread thread;
	private boolean isOver;
	
	
	public void init() {
		row=22;
		col=32;
		rule = new GameRule(row,col);
		
		//初始化Panel
		 backPanel = new JPanel(new BorderLayout());
		 centerPanel = new JPanel(new GridLayout(row-1,col-1));
		 buttonPanel = new JPanel();
		 
		 btnBlock = new JButton[row][col];
		 btnStart = new JButton("Start");
		 btnExit = new JButton("Exit");
		 btnNextGeneration = new JButton("下一代");
		 isSelected = new boolean[row][col];
		 
		 //设置panel
		 this.setContentPane(backPanel);
		 backPanel.add(centerPanel,"Center");
		 backPanel.add(buttonPanel,"South");
		 
		 for(int i=1;i<=row-1;i++)
			 for(int k=1;k<=col-1;k++) {
				 btnBlock[i][k]=new JButton("");
				 btnBlock[i][k].setBackground(Color.white);
				 centerPanel.add(btnBlock[i][k]);
			 }
		 
		 //添加按钮
		 buttonPanel.add(btnNextGeneration);
		 buttonPanel.add(btnStart);
		 buttonPanel.add(btnExit);
		 
		 //设置窗口
		 this.setSize(900,700);
		 this.setLocationRelativeTo(null);
		 this.setResizable(false);
		 this.setVisible(true);
		 
		 btnNextGeneration.addActionListener(this);
		 btnStart.addActionListener(this);
		 btnExit.addActionListener(this);
		 
		 for (int i = 1; i < row-1; i++) 
				for (int j = 1; j < col-1; j++) {
					btnBlock[i][j].addActionListener(this);
				}  
	}
	
	 public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnNextGeneration) {
				nextGeneration();
			} else if (e.getSource() == btnStart) {
				isRunning = true;
				thread = new Thread(new Runnable() {
					@Override
					public void run() {
						while (isRunning) {
							nextGeneration();
							try {
								Thread.sleep(100);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}

							isOver = true;
							// 遍历所有点，看是否还有存活的细胞
							for (int i = 1; i< row-1; i++) {
								for (int j = 1; j < col-1; j++) {
									if (rule.getCurrent()[i][j] != 0) {
										isOver = false;
										break;
									}
								}
								if (!isOver) {
									break;
								}
							}
							if (isOver) {
								JOptionPane.showMessageDialog(null, "细胞全部死亡，无法进行繁殖了！");
								isRunning = false;
								thread = null;
							}
						}
					}
				});
				// 启动线程
				thread.start();
			} else if (e.getSource() == btnExit) {
				System.exit(0);
			} else {
				// 选择活细胞的位置
				// 调用游戏控制类的细胞原始状态
				int[][] grid = rule.getCurrent();
				// 遍历所有网格，并确定玩家是否点击了该位置，点击后则将该位置的状态变为原来的反状态
				for (int i = 1; i < row-1; i++) {
					for (int j = 1; j < col-1; j++) {
						if (e.getSource() == btnBlock[i][j]) {
							isSelected[i][j] = !isSelected[i][j];
							if (isSelected[i][j]) {
								btnBlock[i][j].setBackground(Color.BLACK);
								grid[i][j] = 1;
							} else {
								btnBlock[i][j].setBackground(Color.white);
								grid[i][j] = 0;
							}
							break;
						}
					}
				}
				// 将用户设定的细胞状态信息保存到游戏控制类中
				rule.setMap(grid);
			}
	}
	 
	 
	private void nextGeneration(){
		 rule.generate();
		 for(int i=1;i<row-1;i++)
			 for(int j=1;j<col-1;j++) {
				 if(rule.getCurrent()[i][j]==1)
					 btnBlock[i][j].setBackground(Color.black);
				 else
					 btnBlock[i][j].setBackground(Color.white);
			 }
	 }
	
	 
}
