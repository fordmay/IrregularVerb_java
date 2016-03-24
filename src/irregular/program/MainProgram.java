package irregular.program;

import java.io.*;

/**
 * @author   Dmitriy Zheliabin <d.u.zheliabin@gmail.com>
 * @version  3.0
 * @since 	 04.04.2015
 */
public class MainProgram   {
	private int trueNumber; 
	private int falseNumber;
	private String line;
	private String word0;
	private String word1;
	private String word2;
	private String word3;
	
	private int numberLine = 0;
	private String[] massLines;
	private int numberOfLines; 
	private String path;
	
	public MainProgram(String file) throws IOException{
		path = file;
		setNumberOfLines();
		getMassLines();	
	}
	
	//выводит позитивное количество ответов			
	public int getCorrect(){
		return trueNumber;
	}
	//выводит негативное количество ответов			
	public int getIncorrect(){
		return falseNumber;
	}
	//выводит количество строк
	public int getNumberOfLines(){
		return numberOfLines;
	}
	//выводит номер выбраной строки
	public int getNumberLine(){
		return numberLine;
	}
	/* выводит слова и предложение с этих слов
	 */
	public String getResultLine(){
		return line;
	}
	public String getWord0(){
		return word0;
	}
	public String getWord1(){
		return word1;
	}
	public String getWord2(){
		return word2;
	}
	public String getWord3(){
		return word3;
	}
	//обнуляет результат
	public void setReset(){
		trueNumber = 0;
		falseNumber = 0;
		numberLine = 0;
	}
	//берет строку попорядку
	public int setNumber(){
		if(numberLine == numberOfLines){
			numberLine = 0;
		}
		return numberLine++;	
	}
	//рандомно выбирает строку 
	public int setRandomLine(){
		numberLine = (int)(Math.random()*numberOfLines);
		return numberLine;
	}
	//берет строку из масива, разбивает на слова
	public void setLine(int numberLine){
		String[] words = massLines[numberLine].split(";");
		word0 = words[0];
		word1 = words[1];
		word2 = words[2];
		word3 = words[3];
		line = words[0]+"  "+words[1]+"  "+words[2];
	}
	//берет слово, проверяет, если не правильно и есть "/" разбивает на части и проверяет с частями 
	public boolean getResultWord(String readWord, String writeText){
		boolean checkWord = false;
		if(readWord.equalsIgnoreCase(writeText)){
			trueNumber++;
			checkWord = true;
		}else if(readWord.contains("/")){
			String[] words = readWord.split("/");
			for(int i=0;i<words.length;i++){
				if(words[i].equalsIgnoreCase(writeText)){
					trueNumber++;
					checkWord = true;
				}
			}
			if(checkWord!=true){
				falseNumber++;
			}
		}else{
			falseNumber++;
		}
		return checkWord;
	}	
	//пересчитывает количество строк в файле
	private void setNumberOfLines() throws IOException{
		BufferedReader bufferedReader1 = new BufferedReader(new FileReader(path));
		while(bufferedReader1.readLine()!=null){
			numberOfLines++;
		}
		bufferedReader1.close();
	}
	//считывает с файла строки, и создает из них масив
	private void getMassLines () throws IOException {	
		FileReader fileReader = new FileReader(path);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		massLines = new String[numberOfLines];
		for(int i=0; i<massLines.length; i++){
			massLines[i] = bufferedReader.readLine();
		}
		bufferedReader.close();
	}


}