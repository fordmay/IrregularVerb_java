package irregular.graphic;

import irregular.program.MainProgram;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import resource.ResourceLoader;

/**
 * @author   Dmitriy Zheliabin <d.u.zheliabin@gmail.com>
 * @version  1.0
 * @since 	 26.04.2015
 */
public class EngineProgram extends GraphicProgram {
	private static final long serialVersionUID = 1988;
	private boolean checkNumber = true;
	//чтоб рандом нажимался один раз
//	private boolean checkRundom = true;
	//мой клас с программой
	private MainProgram MPrg;
	
	public EngineProgram(){
		super();
		EnabledAllActive(false);
	}
	//метод работы кнопочки "Reset"
	@Override
	protected void useResetItem(){
		MPrg.setReset();
		forFile.setText(""+MPrg.getNumberLine()+"/"+""+MPrg.getNumberOfLines());
		EnabledAllActive(false);
		buttonRandom.setEnabled(true);
	}
	//метод работы кнопочки "first (random)"
	@Override
	protected void useFirstItem(){
		checkNumber = true;
		buttonRandom.setText("Random");
		useResetItem();
		textPane.setText("\"Random\" mode is enabled!");
		
	}
	//метод работы кнопочки "second (from A to Z)"
	@Override
	protected void useSecondItem(){
		checkNumber = false;
		buttonRandom.setText("Next");
		useResetItem();
		textPane.setText("\"From A to Z\" mode is enabled!");
	}
	//метод работы кнопочки "About"
	@Override
	protected void useAbout(){
		JOptionPane.showMessageDialog(panel,
				"Created by D.U.Zheliabin"+"\nd.u.zheliabin@gmail.com",
				"About program",JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(ResourceLoader.loadImage("working_en.gif")));
	}
	//метод работы кнопочки "OK"
	@Override
	protected void useButton_OK(){
		if(MPrg.getResultWord(MPrg.getWord1(), pastSimpleField.getText())){
			checkLabel_1.setText("✓"); //" ✓  "
			correctNumberLabel.setText(""+MPrg.getCorrect());
		}else{
			checkLabel_1.setText("✗"); //" ✗ "
			incorrectNumberLabel.setText(""+MPrg.getIncorrect());
		}

		if(MPrg.getResultWord(MPrg.getWord2(), pastParticipleField.getText())){
			checkLabel_2.setText("✓"); //" ✓  "
			correctNumberLabel.setText(""+MPrg.getCorrect());
		}else{
			checkLabel_2.setText("✗"); //" ✗ "
			incorrectNumberLabel.setText("" + MPrg.getIncorrect());
		}		
		textPane.setText(MPrg.getResultLine() + "\n" + MPrg.getWord3());
//		checkRundom = true;
		buttonOK.setEnabled(false);
		buttonRandom.setEnabled(true);
		pastSimpleField.setEditable(false);
		pastParticipleField.setEditable(false);
	}
	//метод работы кнопочки "Random"
	@Override
	protected void useButton_Random(){
//		if(checkRundom){
			checkLabel_1.setText("");
			checkLabel_2.setText("");
			pastSimpleField.setText("");
			pastParticipleField.setText("");
			if(checkNumber){
				MPrg.setLine(MPrg.setRandomLine());
				forFile.setText(""+(MPrg.getNumberLine()+1)+"/"+""+MPrg.getNumberOfLines());
			}
			else{
				MPrg.setLine(MPrg.setNumber());	
				forFile.setText(""+MPrg.getNumberLine()+"/"+""+MPrg.getNumberOfLines());
			}
			textPane.setText(MPrg.getWord0() + "\n" + MPrg.getWord3());
//			checkRundom = false;
			buttonOK.setEnabled(true);
			buttonRandom.setEnabled(true);
			pastSimpleField.setEditable(true);
			pastParticipleField.setEditable(true);
//		}
	}
	//метод создание окна для работы с файлами
	@Override
	protected void setFile(){
		JFileChooser chooser = new JFileChooser();
		//"." - указываем текущий каталог(открывает место размещения программы)
		chooser.setCurrentDirectory(new File("."));
		//устанавливаем фильтр только на *.csv с именем "CSV files"
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files","csv");
		chooser.setFileFilter(filter);
		//убираем фильтр "All Files"
		chooser.setAcceptAllFileFilterUsed(false);
		//устанавливаем результат для кнопочек "ok и cancel"
		int result = chooser.showOpenDialog(panel);
		if(result == JFileChooser.APPROVE_OPTION){
			//сохраняем путь к файлу
			String filePath = chooser.getSelectedFile().getPath();
			try {
				MPrg = new MainProgram(filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			useResetItem();
			textPane.setText("File has been loaded...");
			controlMenu.setEnabled(true);
		}else if(result == JFileChooser.CANCEL_OPTION);
	}
	//метод создание окна для выбора цвета
	@Override
	protected void setColor(){
		Color defaultColor = panel.getBackground();
		Color selected = JColorChooser.showDialog(panel, "Background Color", defaultColor);
		if(selected!=null){
			panel.setBackground(selected);
		}
	}
	//метод блокировки и деблокировки всех активных элементов
	private void EnabledAllActive(boolean TorF){
		buttonOK.setEnabled(TorF);
		buttonRandom.setEnabled(TorF);
		pastSimpleField.setEditable(TorF);
		pastParticipleField.setEditable(TorF);
		checkLabel_1.setText("");
		checkLabel_2.setText("");
		pastSimpleField.setText("");
		pastParticipleField.setText("");
		textPane.setText("");
		correctNumberLabel.setText("0");
		incorrectNumberLabel.setText("0");
	}
}
