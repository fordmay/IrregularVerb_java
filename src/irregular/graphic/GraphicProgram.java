package irregular.graphic;

import resource.ResourceLoader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * @author   Dmitriy Zheliabin <d.u.zheliabin@gmail.com>
 * @version  1.0
 * @since 	 25.04.2015
 */
public abstract class GraphicProgram extends JFrame {
	private static final long serialVersionUID = 1988;
	private final static int screenWidth = 650;
	private final static int screenHeight = 350;
	protected JMenu controlMenu;
	protected JPanel panel;
	protected JPanel panel_2;
	protected JTextPane textPane;
	protected JTextField pastSimpleField;
	protected JTextField  pastParticipleField;
	protected JLabel forFile;
	protected JLabel checkLabel_1;
	protected JLabel checkLabel_2;
	protected JLabel correctNumberLabel;
	protected JLabel incorrectNumberLabel;
	protected JButton buttonOK;
	protected JButton buttonRandom;

	public GraphicProgram(){
		getImageIcon();

		setSize(screenWidth, screenHeight);
		//не дает изменять размеры окна
		setResizable(false);
		setTitle("IRREGULAR VERBS v1.0");
		//размещает окно по центру экрана
		setLocationRelativeTo(null);

		getMenu();

		panel = new JPanel();
		panel.setBackground(Color.GRAY.brighter());
		panel.setLayout(new GridBagLayout());//добавляет редактируемую сетку
		getGrid();
		getTextPane();
		getField();
		getButtons();
		getResult();
		add(panel, BorderLayout.CENTER);

		panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		forFile = new JLabel("0/0",SwingConstants.CENTER);
		forFile.setFont(new Font(null, Font.BOLD, 12));
		panel_2.add(forFile);
		add(panel_2, BorderLayout.SOUTH);

	}
	private void getImageIcon(){
		Image image = ResourceLoader.loadImage("United-Kingdom-icon.png");
		setIconImage(image);
	}
	//метод создания кнопок меню и подменю
	private void getMenu(){
		JMenuBar menuBar = new JMenuBar();
		//создаем кнопку меню "File"
		JMenu fileMenu = new JMenu(" File ");
		//клавиша быстрого доступа
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);
		//создаем подменю
		JMenuItem loadFile = new JMenuItem("Load from file...", 'L');
		JMenuItem selectColor = new JMenuItem("Selector color", 'S');
		JMenuItem exitProg = new JMenuItem("Exit", 'E');
		//создаем хот-кей для "Exit"
		exitProg.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
		fileMenu.add(loadFile);
		fileMenu.add(selectColor);
		fileMenu.addSeparator();//разделительная линия
		fileMenu.add(exitProg);
		//действия кнопок при нажатии
		loadFile.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				setFile();
			}
		});
		selectColor.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				setColor();
			}
		});		
		exitProg.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});

		//создаем кнопку меню "Control"
		controlMenu = new JMenu("Control");
		controlMenu.setEnabled(false);
		controlMenu.setMnemonic('C');
		menuBar.add(controlMenu);
		JMenuItem resetItem = new JMenuItem("Reset", 'R');
		resetItem.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
		JMenu subControlMenu = new JMenu("Mode");
		subControlMenu.setMnemonic('M');
		controlMenu.add(resetItem);
		controlMenu.add(subControlMenu);
		JRadioButtonMenuItem firstItem = new JRadioButtonMenuItem("first (random)");
		firstItem.setMnemonic('f');
		firstItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 1"));
		JRadioButtonMenuItem secondItem = new JRadioButtonMenuItem("second (from A to Z)");
		secondItem.setMnemonic('s');
		secondItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 2"));
		firstItem.setSelected(true);
		subControlMenu.add(firstItem);
		subControlMenu.add(secondItem);
		ButtonGroup group = new ButtonGroup();
		group.add(firstItem);
		group.add(secondItem);
		resetItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				useResetItem();
			}
		});
		firstItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				useFirstItem();
			}
		});
		secondItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				useSecondItem();;
			}
		});

		//создаем кнопку меню "Help"
		JMenu helpMenu = new JMenu(" Help ");
		helpMenu.setMnemonic('H');
		menuBar.add(helpMenu);
		JMenuItem aboutItem = new JMenuItem("About", 'A');
		//создаем пиктограмку
		aboutItem.setIcon(new ImageIcon(ResourceLoader.loadImage("Very-Basic-About-icon.png")));
		helpMenu.add(aboutItem);
		aboutItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				useAbout();
			}
		});
		setJMenuBar(menuBar);
	}
	//для разметки сетки
	private void getGrid(){
		for(int i=0;i<6;i++){
			panel.add(new JLabel(), new GridBagConstraints(i,0,1,1,1,0,
					GridBagConstraints.CENTER,GridBagConstraints.NONE,
					new Insets(1,1,1,1),0,0));	
		}
	}
	//панель экрана
	private void getTextPane(){
		textPane = new JTextPane();
		//убирает фокус с панели
		textPane.setFocusable(false);
		//не дает редактировать текст
		textPane.setEditable(false);
		//параметры шрифта
		textPane.setFont(new Font("Serif", Font.BOLD, 18));
		//делает текст по центру текстовой панели
		StyledDocument styledDoc = textPane.getStyledDocument();
		SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
		StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
		styledDoc.setParagraphAttributes(0, styledDoc.getLength(), centerAttribute, false);
		//добавляем скрол
		JScrollPane scroll = new JScrollPane(textPane);

		panel.add(scroll, new GridBagConstraints(0,1,6,1,1,1,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH,
				new Insets(15,1,20,1),0,0));
	}
	//название строки, строка для написания текста, и чекинг
	private void getField(){
		JLabel pastSimpleLabel = new JLabel("Past Simple: ");
		pastSimpleLabel.setFont(new Font("Serif", Font.BOLD, 15));
		pastSimpleField = new JTextField(12);
		pastSimpleField.setFont(new Font(null, Font.PLAIN, 15));
		checkLabel_1 = new JLabel("",SwingConstants.CENTER);
		checkLabel_1.setFont(new Font(null, Font.BOLD, 15));
		pastSimpleField.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent event){
				int key = event.getKeyCode();
				if(key == KeyEvent.VK_ENTER)
					pastSimpleField.transferFocus();
			}
		});
		JLabel pastParticipleLabel = new JLabel("Past Participle: ");
		pastParticipleLabel.setFont(new Font("Serif", Font.BOLD, 15));
		pastParticipleField = new JTextField(12);
		pastParticipleField.setFont(new Font(null, Font.PLAIN, 15));
		checkLabel_2 = new JLabel("",SwingConstants.CENTER);
		checkLabel_2.setFont(new Font(null, Font.BOLD, 15));
		pastParticipleField.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent event){
				int key = event.getKeyCode();
				if(key == KeyEvent.VK_ENTER)
					pastParticipleField.transferFocus();
			}
		});
		panel.add(pastSimpleLabel, new GridBagConstraints(0,2,2,1,1,0,
				GridBagConstraints.EAST,GridBagConstraints.NONE,
				new Insets(1,1,10,1),0,0));
		panel.add(pastSimpleField, new GridBagConstraints(2,2,3,1,1,0,
				GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
				new Insets(1,10,10,10),0,0));
		panel.add(checkLabel_1, new GridBagConstraints(5,2,1,1,1,0,
				GridBagConstraints.WEST,GridBagConstraints.NONE,
				new Insets(1,1,10,1),0,0));

		panel.add(pastParticipleLabel, new GridBagConstraints(0,3,2,1,1,0,
				GridBagConstraints.EAST,GridBagConstraints.NONE,
				new Insets(1,1,20,1),0,0));
		panel.add(pastParticipleField, new GridBagConstraints(2,3,3,1,1,0,
				GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
				new Insets(1,10,20,10),0,0));
		panel.add(checkLabel_2, new GridBagConstraints(5,3,1,1,1,0,
				GridBagConstraints.WEST,GridBagConstraints.NONE,
				new Insets(1,1,20,1),0,0));
	}
	//кнопки
	private void getButtons(){
		buttonOK = new JButton("OK");
		buttonOK.setFont(new Font(null, Font.BOLD, 14));
		buttonOK.setToolTipText("Click here to check answer");
		//действия с клавиатуры
		buttonOK.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent event){
				int key = event.getKeyCode();
				if(key == KeyEvent.VK_ENTER){
					buttonOK.transferFocus();
					useButton_OK();
				}
			}
		});
		//действия с нажатия мышки
		buttonOK.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				useButton_OK();
			}
		});

		buttonRandom = new JButton("Random");
		buttonRandom.setFont(new Font(null, Font.BOLD, 14));
		buttonRandom.setToolTipText("Click here to see next word");
		//действия с клавиатуры
		buttonRandom.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent event){
				int key = event.getKeyCode();
				if(key == KeyEvent.VK_ENTER){
					buttonRandom.transferFocus();
					useButton_Random();
				}
			}
		});
		buttonRandom.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				useButton_Random();
			}
		});

		panel.add(buttonOK, new GridBagConstraints(0,4,3,1,1,0,
				GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
				new Insets(1,1,20,1),0,0));
		panel.add(buttonRandom, new GridBagConstraints(3,4,3,1,1,0,
				GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
				new Insets(1,1,20,1),0,0));
	}
	//название данных и их результат
	private void getResult(){
		JLabel correctLabel = new JLabel("Сorrect ");
		correctLabel.setFont(new Font(null, Font.BOLD, 13));
		correctNumberLabel = new JLabel("0",SwingConstants.CENTER);
		correctNumberLabel.setFont(new Font(null, Font.BOLD, 13));
		correctNumberLabel.setBorder(BorderFactory.createLoweredBevelBorder());

		JLabel incorrectLabel = new JLabel(" Incorrect");
		incorrectLabel.setFont(new Font(null, Font.BOLD, 13));
		incorrectNumberLabel = new JLabel("0",SwingConstants.CENTER);
		incorrectNumberLabel.setFont(new Font(null, Font.BOLD, 13));
		incorrectNumberLabel.setBorder(BorderFactory.createLoweredBevelBorder());

		panel.add(correctLabel, new GridBagConstraints(0,5,2,1,1,0,
				GridBagConstraints.EAST,GridBagConstraints.NONE,
				new Insets(1,1,10,1),0,0));
		panel.add(correctNumberLabel, new GridBagConstraints(2,5,1,1,1,0,
				GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
				new Insets(1,1,10,1),0,0));
		panel.add(incorrectNumberLabel, new GridBagConstraints(3,5,1,1,1,0,
				GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
				new Insets(1,1,10,1),0,0));
		panel.add(incorrectLabel, new GridBagConstraints(4,5,2,1,1,0,
				GridBagConstraints.WEST,GridBagConstraints.NONE,
				new Insets(1,1,10,1),0,0));
	}
	protected void useButton_Random(){}
	protected void useButton_OK(){}
	protected void setFile(){}
	protected void setColor(){}
	protected void useResetItem(){}
	protected void useAbout(){}
	protected void useFirstItem(){}
	protected void useSecondItem(){}
}