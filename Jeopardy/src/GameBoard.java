
import java.util.*;
import java.io.*;

public class GameBoard {
	
	//Global Variables
	Team [] teams = new Team[4];
	Question[][] all_question_categories = new Question[5][1];
	Question[] c1questions = new Question[5];
	Question[] c2questions = new Question[5];
	Question[] c3questions = new Question[5];
	Question[] c4questions = new Question[5];
	Question[] c5questions = new Question[5];
	String [] categories = new String[5];
	String [] values = new String[5];
	int n = 0;
	Question finaljeopardy = null;
	
	
	
	public void initialMenu() {
		System.out.println("Please enter the number of teams that will be playing in this game");
		Scanner reader = new Scanner(System.in);  
		n = reader.nextInt();
		if(n<5){
			Scanner name = new Scanner(System.in);
			for(int i=0; i<n; i++){
				int k = i+1;
				System.out.println("Enter a name for Team " + k);
				String u = name.nextLine();
				Team temp = new Team();
				temp.setName(u);
				temp.setPoints(0);
				teams[i]=temp;
			}
			System.out.println("Thank you! Setting up the game for you...");
			System.out.println("Ready to play!");
			System.out.println("Autoplay?");
			Scanner autoplay = new Scanner(System.in);
			String auto = name.nextLine();
		}
	}
		
	public void parseFile(String file){
		FileReader fr = null;
		BufferedReader br = null;
		
		try{
			fr = new FileReader(file);
			String line = "";
			String filesplitter = "::";
			
			//read the first line
			br = new BufferedReader(fr);
			line = br.readLine();
			categories = line.split(filesplitter);
			if(categories.length != 5){
			    System.out.println("Number of categories must equal 5");
			    System.exit(0);
			}
			for(int i=0; i<categories.length; i++){
			    for(int j=0; j<categories.length; j++){
        		    if(categories[i].equalsIgnoreCase(categories[j]) && i!=j){
        		        System.out.println("Duplicate Categories");
        		        System.exit(0);
        		    }
			    }
			}
			
			//read the second line
			line = br.readLine();
			values = line.split(filesplitter);
			if(values.length != 5){
			    System.out.println("Number of Point Values must equal 5");
			    System.exit(0);
			}
			for(int i=0; i<values.length; i++){
			    for(int j=0; j<values.length; j++){
        		    if(values[i].equals(values[j]) && i!=j){
        		        System.out.println("Duplicate Point Values");
        		        System.exit(0);
        		    }
			    }
			}
			
			line = br.readLine();
			while(line != null){
				String firstletter = "";
				firstletter = String.valueOf(line.charAt(0));
				if(!firstletter.equals(":")){
					System.exit(0);
				}
				else{
			        String [] temp = line.split(filesplitter);
			        Question tempq = new Question();
			        if(temp[1].equalsIgnoreCase("FJ")){
			            tempq.setQuestion(temp[2]);
			            tempq.setAnswer(temp[3]);
			            if(finaljeopardy==null){
			                finaljeopardy = tempq;
			            }
			            else{
			                System.out.println("Cannot repeat Final Jeopardy");
			                System.exit(0);
			            }
			            line = br.readLine();
			            
			        }
			        else{
    			        if(temp.length > 5){
    			            System.out.println("Too many parts to question");
    			            System.exit(0);
    			        }
    			        
    			        line = br.readLine();
    			        if (line.charAt(0) == ':' && temp.length < 5) {
    			            System.out.println("Missing data");
    			            System.exit(0);
    			        }
    			        if(line.charAt(0) != ':') {
    			            String [] temp2 = line.split(filesplitter);
    			            if(temp2.length > 2){
    			                System.out.println("Missing data");
    			                System.exit(0);
    			            }
    			            else if(temp2.length==2 && temp.length==4){
    			                temp[3] = temp[3] + " " + temp2[0];
    			                String [] newtemp = new String[5];
    			                for (int i=0; i<temp.length; i++){
    			                    newtemp[i]=temp[i];
    			                }
    			                newtemp[4] = temp2[1];
    			                temp=newtemp;
    			            }
    			            else if(temp.length==5 && temp2.length==1){
    			                temp[4] = temp[4] + " " + temp2[0];
    			            }
    			            else{
    			                System.out.println("Bad input question");
    			                System.exit(0);
    			            }
    			            line = br.readLine();
    			        }
    			        tempq.setValue(Integer.parseInt(temp[2]));
    			        tempq.setQuestion(temp[3]);
    			        tempq.setAnswer(temp[4]);
    			        if(temp[1].equalsIgnoreCase(categories[0])){
    			            c1questions[find_dollar_index(Integer.parseInt(temp[2]))]=tempq;
    			            
    			        }
    			        else if(temp[1].equalsIgnoreCase(categories[1])){
    			            c2questions[find_dollar_index(Integer.parseInt(temp[2]))]=tempq;
    			       
    			        }
    			        else if(temp[1].equalsIgnoreCase(categories[2])){
    			            c3questions[find_dollar_index(Integer.parseInt(temp[2]))]=tempq;
    			            
    			        }
    			        else if(temp[1].equalsIgnoreCase(categories[3])){
    			            c4questions[find_dollar_index(Integer.parseInt(temp[2]))]=tempq;
    			           
    			        }
    			        else{
    			            c5questions[find_dollar_index(Integer.parseInt(temp[2]))]=tempq;
    			            
    			        }
			       }
				}
			}
			all_question_categories[0] = c1questions;
			all_question_categories[1] = c2questions;
			all_question_categories[2] = c3questions;
			all_question_categories[3] = c4questions;
			all_question_categories[4] = c5questions;
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("FileNotFoundException: " + fnfe.getMessage());
        } catch (IOException ioe) {
        	System.out.println("IOException: " + ioe.getMessage());
        }finally {
        	try {
        		if (br != null) {
        			br.close();
        		}
        		 if (fr != null) {
        			 fr.close();
        		 }
        	}
        	catch (IOException ioe) {
        		System.out.println("IOException closing file: "  + ioe.getMessage());
        	}
        }
	}

	public int find_dollar_index(int n){
		for (int i = 0; i < values.length; i++){
			if (Integer.parseInt(values[i]) == n)
				return i;
		}
		return -1;
	}
	
	public String[] allAnswers(String ans){
		String[] ans_arr = new String[3];
		ans_arr[0] = "who is "+ans;
		ans_arr[1] = "who are "+ans;
		ans_arr[2] = "what is "+ans;
		return ans_arr;
	}

	public void commandLineMenu(){
		int totalcount=0;
		Random rand = new Random();
	    int teamcount = rand.nextInt(n) + 0;
	   	int questionsasked = 0;
	   	while (true){
	   		if (questionsasked != 2){
	   			Scanner firstcat = new Scanner(System.in);
				Scanner firstval = new Scanner(System.in);
				if(totalcount==0){
					System.out.println("The team to go first will be " + teams[teamcount].getName());
				}
				else{
					System.out.println("It is " + teams[teamcount].getName() + "'s turn to choose a question!");
				}
	   			System.out.println("Please choose a Category");
				String[] fa = firstcat.nextLine().split(" ");
				int category_num = Integer.parseInt(fa[1]);
				if (category_num >= 1 && category_num <= 5){
					System.out.println("Please enter the dollar value of the question you wish to answer");
					int val = firstval.nextInt();
					int value_num = find_dollar_index(val);
					if (value_num >= 0 && value_num <= 4){
						Question que = all_question_categories[category_num-1][value_num];
						if (que == null || que.asked == true){
							System.out.println("Question has already been chosen, or it doesn't exist");
						}
						else{
							System.out.println(que.getQuestion());
							System.out.println("please enter your answer. Remember to pose it as a question.");
							String answer = firstcat.nextLine();
							String[] possible_answer = allAnswers(que.getAnswer());
							if (answer.equalsIgnoreCase(possible_answer[0]) || 
								answer.equalsIgnoreCase(possible_answer[1]) ||
								answer.equalsIgnoreCase(possible_answer[2])){
								System.out.println("Your Answer is right.");
								int points = teams[teamcount].getPoints();
								teams[teamcount].setPoints(points + val);
							}
							else{
								System.out.println("Your Answer is wrong.");
							}
							que.asked = true;
						}
						questionsasked += 1;
						teamcount += 1;
						teamcount %= n;
					}
					else{
						System.out.println("Choose a Valid Dollar Value");
					}
				}
				else{
					System.out.println("Choose a Valid Category");
				}
	   		}
	   		else{
	   			break;
	   		}
	   	}
	    System.out.println("Now that all questions have been chosen, it's time for Final Jeopardy!");
	    Scanner wager = new Scanner(System.in);
	    int[] wagers = new int[4];
		String[] answers = new String[4];
		for(int i=0; i<n; i++){
			System.out.println(teams[i].getName() + ", please give a dollar amount from your total that you would like to bet");
			int w = wager.nextInt();
			if(w>teams[i].getPoints()){
				System.out.println("Cannot wager more points than you have");
				while(w>teams[i].getPoints()){
					System.out.println(teams[i].getName() + ", please give a dollar amount from your total that you would like to bet");
					w = wager.nextInt();
				}
			}
			wagers[i]=w;
		}
		System.out.println("The question is: ");
		System.out.println(finaljeopardy.getQuestion());
		Scanner fanswer = new Scanner(System.in);
		for(int i=0; i<n; i++){
			System.out.println("Team " + teams[i].getName() + " what is your answer?");
			String fans = fanswer.nextLine();
			answers[i] = fans;
		}
		String[] possible_answer = allAnswers(finaljeopardy.getAnswer());
		for (int i = 0; i < n; i++){
			if (answers[i].equalsIgnoreCase(possible_answer[0]) || 
				answers[i].equalsIgnoreCase(possible_answer[1]) ||
				answers[i].equalsIgnoreCase(possible_answer[2])){
				int points = teams[i].getPoints();
				teams[i].setPoints(points + wagers[i]);
			}
			else{
				int points = teams[i].getPoints();
				teams[i].setPoints(points - wagers[i]);
			}
		}
		int index = -1;
		int max_point = -1;
		for (int i = 0; i < n; i++){
			if (teams[i].getPoints() > max_point){
				index = i;
				max_point = teams[i].getPoints();
			}
		}
		System.out.println("And the winner is " + teams[index].getName());
		System.out.println("---- Points of the Teams ----");
		for (int i = 0; i < n; i++){
			System.out.println("Team "+(i+1)+"  Score is :   "+teams[i].getPoints());
		}
	}
	
	public void Quit(){
		System.exit(0);
	}
	public void Replay(){
		
	}
	public void updateScores(){
		
	}}