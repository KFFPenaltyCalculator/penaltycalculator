package penaltycalculator;
import java.util.*;
import java.lang.Math;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Jacob Gosselin
//Kaiser Family Foundation
//Penalty Calculator

	public class penaltycalculator{
//DECLARING ALL PUBLIC INPUTS
		
		//USER GIVEN INPUTS pt1
		public static int state;	//state of residence
		public static JComboBox<String> statedisplay;
		public static int zip;	//zip code
		public static JTextField zipdisplay;
		public static int countynum;	//which county the user is in, if there zip code encompasses multiple counties (i.e. think of a drop down menu counties)
		public static int agi;	//adjusted gross income
		public static JTextField agidisplay;
		public static JComboBox<String> employeedisplay;
		public static boolean employeeoffer;
		public static int household;	//number of people in household
		public static int num_parents;	//number of parents/adults in household
		public static int num_children;	//number of children/dependents in household
		public static JComboBox <String> parentsdisplay;	//display for choosing # of adults/parents
		public static JComboBox<String> childrendisplay;	//display for choosing # of children/adult dependents
		
		//USER GIVEN INPUTS pt2
		public static ArrayList<Integer> ages;	//list of ages of family members
		public static ArrayList<Integer> adultmonths;	//list of months uninsured by adults  
		public static ArrayList<Integer> childrenmonths;	//list of months uninsured by children
		public static ArrayList<JTextField> peopleage;	//age input for adults/parents
		public static ArrayList<JComboBox<String>> peoplemonths;	//months inputs for adults/parents
		public static ArrayList<JComboBox<String>> peopleinsurance;	//insurance input for adults/parents
		public static ArrayList<JComboBox<String>> peoplemonths2;	//months inputs for children/dependents
		public static ArrayList<JTextField> peopleage2;	//age input for children/dependents
		public static ArrayList<JComboBox<String>> peopleinsurance2;	//insurance input for children/dependents
		
		//INFERRED INPUTS
		public static String employeeoption;
		public static int uninsureda;	//uninsured adults, i.e. how many entries select uninsured and adult/parent 
		public static int uninsuredc;	//uninsured children, i.e. how many entries select uninsured and child/dependent
		public static int filingstatus;  //1 is single, 2 is head of household, 3 is married and filing jointly; calculated based on givens (i.e. if there are 2 adults/parents, we assume they're married and filing jointly)
		public static float povertylevel;	//what is the poverty level for your household (calculate based on filing status, number of dependents, and state of residency)
		public static float povertypercent;	//what percentage of the poverty line you are (calculated based on your specific poverty level, and your agi)
		public static double silverplan;	//what is the second lowest cost silver plan in your area (needed to calculate subsidies) 
		public static double bronzeplan; //what is the  cheapest bronze plan in your area (needed to check for exemptions)
		public static float discounted;	//bronze plan with tax subsidy
		public static float subsidy; //subsidy
		public static double subsidypercent; //percentage of income used to determine subsidy
		public static double penalty;	//penalty 
		
		//ADDITIONAL INFO
		public static String[] zips={"02816","02816","02817","02818","02827","75243"};	//zips being hard-coded in (in final version read from excel) 
		public static ArrayList<String> zipoptions;	//list of counties which are encompassed by given zip code, to be used in additional pop up menu	
		public static String[] county={"Providence","Kent","Kent","Kent","Kent","Dallas"};	//counties hard-coded in
		public static JComboBox<String> countydisplay;	//display for county options
		public static double[] silverplans={263,263,263,263,263,263.87};	//silver plan premiums for 40 year old non-smokers (in final version read from excel)
		public static double[] bronzeplans={203,203,203,203,203,219.32};	//bronze plan premiums for 40 year old non-smokers (in final version read from excel)
		public static double[][] subsidytable;	//table of values for subsidy calculation
		public static String[] States={"AL","AK","AZ","AR","CA","CO","CT","DE","DC","FL","GA","HI","ID","IL","IN","IA","KS","KY","LA","ME","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","MD","MA","MI","MN","MS","MO","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};
		public static double[] familymedicaid={0.18,1.43,1.38,1.38,1.38,1.38,1.55,1.38,2.21,0.33,0.37,1.38,0.26,1.38,1.39,1.38,0.38,1.38,0.24,1.05,1.38,1.38,1.38,1.38,0.27,0.22,1.38,0.63,1.38,1.38,1.38,1.38,1.38,0.44,1.38,1.38,0.44,1.38,1.38,1.38,0.67,0.52,1,0.18,0.45,1.38,0.39,1.38,1.38,1,0.56};       //note: SC varies based on employment      
		public static double[] chipeligibility={3.12, 2.03, 2, 2.11, 3.17, 2.6, 3.18, 2.12, 3.19, 2.1, 2.47, 3.08, 1.85, 3.13, 2.5, 3.02, 2.38, 2.13, 2.5, 2.08, 3.17, 3, 2.12, 2.75, 2.09, 3, 2.61, 2.13, 2, 3.18, 3.5, 2.4, 4, 2.11, 1.7, 2.06, 2.05, 3, 3.14, 2.61, 2.08, 2.04, 2.5, 2.01, 2, 3.12, 2, 3.12, 3, 3.01, 2};	//CHIP Eligibility Poverty Levels, hard coded in
		public static double[] individualmedicaid={0,1.38,1.38,1.38,1.38,1.38,1.38,1.38,2.15,0,0,1.38,0,1.38,1.39,1.38,0,1.38,0,0,1.38,1.38,1.38,1.38,0,0,1.38,0,1.38,1.38,1.38,1.38,1.38,0,1.38,1.38,0,1.38,1.38,1.38,0,0,0,0,0,1.38,0,1.38,1.38,1,0};
		public static double[] agefactors={0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 0.6350 , 1.0000 , 1.0000 , 1.0000 , 1.0000 , 1.0040 , 1.0240 , 1.0480 , 1.0870 , 1.1190 , 1.1350 , 1.1590 , 1.1830 , 1.1980 , 1.2140 , 1.2220 , 1.2300 , 1.2380 , 1.2460 , 1.2620 , 1.2780 , 1.3020 , 1.3250 , 1.3570 , 1.3970 , 1.4440 , 1.5000 , 1.5630 , 1.6350 , 1.7060 , 1.7860 , 1.8650 , 1.9520 , 2.0400 , 2.1350 , 2.2300 , 2.3330 , 2.4370 , 2.5480 , 2.6030 , 2.7140 , 2.8100 , 2.8730 , 2.9520 , 3.0000};	//Age Factors hard coded in
		
		
		
		//MISC
		public static SpringLayout layout;	//layout
		public static Container contentpane;	//container manager
		public static JFrame frame;	//frame
		
		
		public static JButton button;	//go button
		
		//Results
		public static boolean exempt;	//whether or not one is exempt
		public static String explanation;	//explanation if you're exempt
		public static String main_para;	//main paragraph for result, altered based on output of penalty calculation
		public static String help1;	//CHIP paragraph
		public static String help2;	//Basic Health Plan paragraph
		public static String help3;	//Additional subsidies paragraph
		public static boolean medicaideligible;	//boolean for whether the family/individiual is eligible for medicaid
		public static boolean chipeligible;	//boolean for whether the family has any kids who may be eligible for medicaid
		public static boolean catastrophic_plan;
		public static boolean help2eligible=false;	//boolean for basic health plan possibility
		public static boolean help3eligible=false;	//boolean for additional subsidies possibility
		public static String short_coverage_gap;	//string of text that is displayed if any members of the user's family are insured for less than 3 months (if entire family is, they will just be shown as exempt, but if only certain family members are, then they won't be considered in penalty calculation and this message will show)
		public static boolean extra_subsidy;	//boolean for if the subsidy shown, which maxes out at the family cost for a bronze plan, is less than it would be if they chose a silver plan (if true, a message communicating that will appear)
		public static JLabel resultdisplay;	//display for result
		public static JLabel display_help1;
		public static JLabel display_help2;
		public static JLabel display_help3;
		public static JLabel display_shortcoveragegap;
		
		
//MAIN FUNCTION
		public static void main(String[]args){
			//setting up frame
			frame=new JFrame("Screen");
			layout=new SpringLayout();
			contentpane=frame.getContentPane();
			contentpane.setLayout(layout);
			frame.setSize(800,800);
			
			//Creating input fields
			String[] parents={"# of adults/parents in household", "1","2"};	//options for parentsdisplay
			String[] children={"#of children/dependents in household","0","1", "2", "3", "4", "5", "6", "7", "8"};	//options for childrendisplay
			parentsdisplay=new JComboBox<String>(parents);	//display parents options
			String[] states={"AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"};
			statedisplay=new JComboBox<String>(states);	//display states options
			statedisplay.setSelectedIndex(39);	//DEFAULT STATE TO RHODE ISLAND SINCE THOSE ARE THE ZIPS I HAVE CODED IN
			agidisplay=new JTextField("Annual Gross Income",15);	//AGI input
			zipdisplay=new JTextField("Zip code");	//zip input
			countydisplay=new JComboBox<String>();	//county options (displayed later)
			String[] employeeoptions={"Does anyone in your family have access to an employer sponsored plan?", "Yes", "No"};
			employeedisplay=new JComboBox<String>(employeeoptions);
			
			//Creating Result Fields
			resultdisplay=new JLabel();	//result display
			display_help1=new JLabel();
			display_help2=new JLabel();
			display_help3=new JLabel();
			display_shortcoveragegap=new JLabel();
			
			
			
			//Displaying input fields 
			contentpane.add(statedisplay);	//adding statedisplay
			layout.putConstraint(SpringLayout.NORTH, statedisplay, 0, SpringLayout.NORTH, contentpane);	//positioning statedisplay
			contentpane.add(zipdisplay);	//adding zipdisplay
			layout.putConstraint(SpringLayout.NORTH, zipdisplay, 30, SpringLayout.NORTH, contentpane);	//position zipdisplay
			
			//Displaying county field based on Zip input
			zipdisplay.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							countydisplay.removeAllItems();
							zipoptions=new ArrayList<String>();
							for (int i=0; i<zips.length; i++){
								if (zipdisplay.getText().equals(zips[i])){
									zipoptions.add(county[i]);
									countydisplay.addItem(county[i]);
									silverplan=silverplans[i];
									bronzeplan=bronzeplans[i];
									System.out.println("County: "+county[i]);
								}
							}
							if (zipoptions.size()>0){
								contentpane.add(countydisplay);
								layout.putConstraint(SpringLayout.NORTH, countydisplay, 30, SpringLayout.NORTH, contentpane);
								layout.putConstraint(SpringLayout.WEST, countydisplay, 50, SpringLayout.WEST, contentpane);
							}
							frame.revalidate();
							frame.repaint();
							}	
						}
					);
			
			//displaying fields con.
			contentpane.add(agidisplay);
			layout.putConstraint(SpringLayout.NORTH, agidisplay, 70, SpringLayout.NORTH, contentpane);
			contentpane.add(employeedisplay);
			layout.putConstraint(SpringLayout.NORTH, employeedisplay,110, SpringLayout.NORTH, contentpane);
			contentpane.add(parentsdisplay);
			layout.putConstraint(SpringLayout.NORTH, parentsdisplay, 140, SpringLayout.NORTH, contentpane);
			
			//displaying person specific fields for parents
			parentsdisplay.addActionListener(
					new ActionListener(){
						//allowing for removing based on selection (long story)
						public void actionPerformed(ActionEvent e){
							if (peopleage!=null){
								for (int i=0; i<peopleage.size(); i++){
									contentpane.remove(peopleage.get(i));
									contentpane.remove(peoplemonths.get(i));
									contentpane.remove(peopleinsurance.get(i));
								}
							}
							num_parents=parentsdisplay.getSelectedIndex();
							household=num_parents+num_children;
							peopleage=new ArrayList<JTextField>();
							peoplemonths=new ArrayList<JComboBox<String>>();
							peopleinsurance=new ArrayList<JComboBox<String>>();
							for (int i=0; i<parentsdisplay.getSelectedIndex(); i++){
								peopleage.add(new JTextField(("Person "+(i+1)+" age")));
								contentpane.add(peopleage.get(i));
								String[] options={"Insured", "Uninsured", "Exempt"};
								peopleinsurance.add(new JComboBox<String>(options));
								contentpane.add(peopleinsurance.get(i));
								layout.putConstraint(SpringLayout.NORTH, peopleinsurance.get(i), 180+30*(i), SpringLayout.NORTH, contentpane);
								layout.putConstraint(SpringLayout.WEST, peopleinsurance.get(i), 85, SpringLayout.WEST, contentpane);
								layout.putConstraint(SpringLayout.NORTH, peopleage.get(i), 180+30*(i), SpringLayout.NORTH, contentpane);
								String[] options2={"Months Uninsured", "0", "1","2","3","4","5","6","7","8","9","10","11","12"};
								peoplemonths.add(new JComboBox<String>(options2));
								contentpane.add(peoplemonths.get(i));
								layout.putConstraint(SpringLayout.NORTH, peoplemonths.get(i), 180+30*(i), SpringLayout.NORTH, contentpane);
								layout.putConstraint(SpringLayout.WEST, peoplemonths.get(i), 180, SpringLayout.WEST, contentpane);
								//I SET MY DEFAULTS TO 12 MONTHS, UNINSURED, AND NO EMPLOYEE OPTION TO EXPEDITE TESTING
								peoplemonths.get(i).setSelectedIndex(13);
								peopleinsurance.get(i).setSelectedIndex(1);
								
								
							}
							layout.putConstraint(SpringLayout.NORTH, childrendisplay, 210+30*num_parents, SpringLayout.NORTH, contentpane);
							layout.putConstraint(SpringLayout.NORTH, button, 240+30*household, SpringLayout.NORTH, contentpane);
							layout.putConstraint(SpringLayout.NORTH, resultdisplay, 250+30*household, SpringLayout.NORTH, contentpane);
							if (peopleinsurance2!=null){
								for (int i=0; i<peopleinsurance2.size(); i++){
									layout.putConstraint(SpringLayout.NORTH, peoplemonths2.get(i), 200+30*(num_parents+(i)), SpringLayout.NORTH, contentpane);
									layout.putConstraint(SpringLayout.NORTH, peopleinsurance2.get(i), 200+30*(num_parents+(i)), SpringLayout.NORTH, contentpane);
									layout.putConstraint(SpringLayout.NORTH, peopleage2.get(i), 200+30*(num_parents+(i)), SpringLayout.NORTH, contentpane);
								}
								
							}
							
							frame.revalidate();
							frame.repaint();
						}
					}
					);
			
			childrendisplay=new JComboBox<String>(children);
			contentpane.add(childrendisplay);
			layout.putConstraint(SpringLayout.NORTH, childrendisplay, 210+20*household, SpringLayout.NORTH, contentpane);
			childrendisplay.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e){
							//allowing for removing based on selection (long story)
							if (peopleage2!=null){
								for (int i=0; i<peopleage2.size(); i++){
									contentpane.remove(peopleage2.get(i));
									contentpane.remove(peopleinsurance2.get(i));
									contentpane.remove(peoplemonths2.get(i));
								}
							}
							num_children=childrendisplay.getSelectedIndex()-1;
							household=num_parents+num_children;
							peopleage2=new ArrayList<JTextField>();
							peopleinsurance2=new ArrayList<JComboBox<String>>();
							peoplemonths2=new ArrayList<JComboBox<String>>();
							for (int i=0; i<childrendisplay.getSelectedIndex()-1; i++){
								peopleage2.add(new JTextField("Person "+(i+1)+" age",7));
								contentpane.add(peopleage2.get(i));
								layout.putConstraint(SpringLayout.NORTH, peopleage2.get(i), 240+30*(num_parents+(i)), SpringLayout.NORTH, contentpane);
								String[] options={"Insured", "Uninsured", "Exempt"};
								peopleinsurance2.add(new JComboBox<String>(options));
								contentpane.add(peopleinsurance2.get(i));
								layout.putConstraint(SpringLayout.NORTH, peopleinsurance2.get(i), 240+30*(num_parents+(i)), SpringLayout.NORTH, contentpane);
								layout.putConstraint(SpringLayout.WEST, peopleinsurance2.get(i), 85, SpringLayout.WEST, contentpane);
								String[] options2={"Months Uninsured","0", "1","2","3","4","5","6","7","8","9","10","11","12"};
								peoplemonths2.add(new JComboBox<String>(options2));
								contentpane.add(peoplemonths2.get(i));
								layout.putConstraint(SpringLayout.NORTH, peoplemonths2.get(i), 240+30*(num_parents+(i)), SpringLayout.NORTH, contentpane);
								layout.putConstraint(SpringLayout.WEST, peoplemonths2.get(i), 180, SpringLayout.WEST, contentpane);
								//I SET MY DEFAULTS TO 12 MONTHS, UNINSURED, AND NO EMPLOYEE OPTION TO EXPEDITE TESTING
								peoplemonths2.get(i).setSelectedIndex(13);
								peopleinsurance2.get(i).setSelectedIndex(1);
								
							}
							layout.putConstraint(SpringLayout.NORTH, button, 250+30*household, SpringLayout.NORTH, contentpane);
							layout.putConstraint(SpringLayout.NORTH, resultdisplay, 270+30*household, SpringLayout.NORTH, contentpane);
							frame.revalidate();
							frame.repaint();
						}
					}
					);
			
			//adding go button
			button=new JButton("Go");
			contentpane.add(button);
			layout.putConstraint(SpringLayout.NORTH, button, 250+20*household, SpringLayout.NORTH, contentpane);
			
			//displaying screen
			frame.setVisible(true);
			

			//Adding the button action (i.e. reads all inputs and runs Penalty Calculator)
			button.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					//Define all integer inputs
					
					//setting default values
					uninsureda=0;
					uninsuredc=0;
					catastrophic_plan=false;
					employeeoffer=false;
					
					
					//reseting array lists for ages and months, if necesarry
					if (adultmonths!=null||childrenmonths!=null||ages!=null){
						adultmonths=null;
						childrenmonths=null;
						ages=null;
						System.out.println("here");
					}
					
					//creating array lists for months and ages
					adultmonths=new ArrayList<Integer>();
					childrenmonths=new ArrayList<Integer>();
					ages=new ArrayList<Integer>();
					
					//figuring out # of uninsured adults and uninsured children based on entries
					int num_adults=parentsdisplay.getSelectedIndex();
					for (int i=0; i<parentsdisplay.getSelectedIndex(); i++){
							uninsureda++;
							adultmonths.add(peoplemonths.get(i).getSelectedIndex()-1);
							ages.add(Integer.parseInt(peopleage.get(i).getText()));
						}
					if (childrendisplay.getSelectedIndex()>0){
						for (int i=0; i<childrendisplay.getSelectedIndex()-1; i++){
							if (peopleinsurance2.get(i).getSelectedIndex()==1&&Integer.parseInt(peopleage2.get(i).getText())<18){
								uninsuredc++;
								childrenmonths.add(peoplemonths2.get(i).getSelectedIndex()-1);
								ages.add(Integer.parseInt(peopleage2.get(i).getText()));
							}
							else if (peopleinsurance2.get(i).getSelectedIndex()==1&&Integer.parseInt(peopleage2.get(i).getText())>=18){
								uninsureda++;
								adultmonths.add(peoplemonths.get(i).getSelectedIndex()-1);
								ages.add(Integer.parseInt(peopleage2.get(i).getText()));
							}
							
						}
					}
					
					
					explanation=" ";
					help1="";
					help2="";
					help3="";
					
					//checking employeeoption
					if (employeedisplay.getSelectedIndex()==1){
						employeeoffer=true;
					}
					
					//inferring filing status
					if (household==1){
						filingstatus=1;
					}
					else if (num_adults==2){
						filingstatus=3;
					}
					else if (household>1 && num_adults==1){
						filingstatus=2;
					}
					
					//getting direct inputs
					agi=Integer.parseInt(agidisplay.getText());
					zip=Integer.parseInt(zipdisplay.getText());
					state=statedisplay.getSelectedIndex();
					countynum=countydisplay.getSelectedIndex();

					

					
					//print out all inputs
					System.out.println("Filing Status: " +filingstatus);
					System.out.println("Ages: "+ages);
					System.out.println("Household Size: "+household);
					System.out.println("State: "+state);
					System.out.println("Uninsured Adults: "+uninsureda);
					System.out.println("Uninsured Children: "+uninsuredc);
					System.out.println("Annual Gross Income: "+agi);
					
					
					//return/print out results 
					layout.putConstraint(SpringLayout.WEST, resultdisplay, 0, SpringLayout.WEST, contentpane);
					layout.putConstraint(SpringLayout.WEST, resultdisplay, 0, SpringLayout.WEST, contentpane);
					penalty=penaltycalculation(filingstatus, ages, household, state, uninsureda, uninsuredc, adultmonths, childrenmonths, agi, zip, countynum, employeeoffer);
					
					//deciding on main paragraph
					if (exempt==true){
						if (medicaideligible==true){
							main_para="You are exempt from the Individual Mandate, because " +explanation+". However, our data shows that you or your family may be eligible for Medicaid.  For more information, please see our subsidy calculator.";
						}
						else{
							main_para="You are exempt from the Individual Mandate, because "+ explanation +". Our data shows that the cheapest bronze plan in your area would cost "+Math.round(discounted)+" given a "+Math.round(subsidy)+" subsidy.";
						}
					}
					else{
						if (employeeoffer==true){
							main_para="Because you have an employee offer, we cannot definitely determine your exemption status.  Your maximum penalty would be "+penalty;
						}
						else if (medicaideligible==true){
							main_para="Your penalty would be "+ Math.round(penalty) + " (or " +Math.round(penalty/12)+" per month).  However, our data shows that you or your family may be eligible for Medicaid.  For more information, please see our subsidy calculator.";
						}
						
						else{
							if (discounted>=penalty/12){
								main_para="Your penalty would be "+ Math.round(penalty) +"(or "+Math.round(penalty/12)+" per month).  Our data shows that the cheapest bronze plan in your area would cost you "+Math.round(discounted)+", given a "+Math.round(subsidy)+" subsidy. ";
							}
							if (discounted<penalty/12){
								main_para="Your penalty would be "+ Math.round(penalty) +"(or "+Math.round(penalty/12)+" per month).  However, our data shows that the cheapest bronze plan in your area would cost you "+Math.round(discounted)+", given a "+Math.round(subsidy)+" subsidy. ";
							}
						}
					}

					//deciding on whether to display additional help paragraph/other misc. paragraphs
					

					
					JLabel display_extrasubsidy=new JLabel();
					JLabel catastrophe=new JLabel("You may be eligible for a catastrophic plan");
					JLabel esi=new JLabel(employeeoption);
					if (chipeligible==true){
						help1="Our data shows that your children may be eligible for CHIP.  We suggest you re-enter your information to our calculator, listing your children as covered.";	
					}
					
					if (help2eligible==true){
						help2="Our data shows that you may be eligible for a basic health plan offered by your state.  ";
					}
					
					if (help3eligible==true){
						help3="Our data shows your state may have additional subsidies.";
					}
					if (short_coverage_gap!=null){
						contentpane.add(display_shortcoveragegap);
						layout.putConstraint(SpringLayout.NORTH, display_shortcoveragegap, 480+30*household, SpringLayout.NORTH, contentpane);
					}
					if (extra_subsidy==true){
						display_extrasubsidy.setText("You would be eligible for additional subsidies if you selected a silver plan.  For more information see our subsidy calculator");
						contentpane.add(display_extrasubsidy);
						layout.putConstraint(SpringLayout.NORTH, display_extrasubsidy, 410+30*household, SpringLayout.NORTH, contentpane);
					}
					if (catastrophic_plan==true){
						contentpane.add(catastrophe);
						layout.putConstraint(SpringLayout.NORTH, catastrophe, 450+30*household, SpringLayout.NORTH, contentpane);
					}
					if (employeeoption!=null){
						contentpane.add(esi);
						layout.putConstraint(SpringLayout.NORTH, esi, 510+30*household, SpringLayout.NORTH, contentpane);
					}
					
					System.out.println(chipeligible);
					resultdisplay.setText(main_para);
					System.out.println(help1);
					
					
					display_help1.setText(help1);
					display_help1.setForeground(Color.BLACK);
					contentpane.add(display_help1);
					layout.putConstraint(SpringLayout.NORTH, display_help1, 330+30*household, SpringLayout.NORTH, contentpane);
					
					display_help2.setText(help2);
					contentpane.add(display_help2);
					layout.putConstraint(SpringLayout.NORTH, display_help2, 360+30*household, SpringLayout.NORTH, contentpane);
					
					display_help3.setText(help3);
					contentpane.add(display_help3);
					layout.putConstraint(SpringLayout.NORTH, display_help3, 390+30*household, SpringLayout.NORTH, contentpane);
					
					resultdisplay.setForeground(Color.BLACK);
					contentpane.add(resultdisplay);
					layout.putConstraint(SpringLayout.NORTH, resultdisplay, 290+30*household, SpringLayout.NORTH, contentpane);
					frame.revalidate();
					frame.repaint();
				}
			});

		}
		public static void exemptcheck(int filingstatus, ArrayList<Integer> ages,int household, int state, int uninsureda, int uninsuredc, ArrayList<Integer> adultmonths, ArrayList<Integer> childrenmonths, int agi, int zip, int countynum, boolean employeeoffer){
			//setting poverty levels for 48 states
			exempt=false;
			medicaideligible=false;
			chipeligible=false;
			int age=ages.get(0);
			explanation="";
			povertylevel=0;
			
			if (state!=11&&state!=0){
				povertylevel=11770+4160*(household-1);
			}
			if (state==0){
				povertylevel=14720+5200*(household-1);
			}
			if (state==11){
				povertylevel=13550+4780*(household-1);
			}
			
			//Checking for Medicaid/CHIP (I do this first because from here on out I end the function when an exemption is found)
			povertypercent=agi/povertylevel;	
			for (int i=0; i<familymedicaid.length; i++){
				if (uninsuredc>0){
					if (povertypercent<familymedicaid[state]){
						medicaideligible= true;
					}
					else{
						medicaideligible=false;
						chipeligible=false;
					}	
				}
				else {
					if (povertypercent<individualmedicaid[state]){
						medicaideligible=true;
					}
					else{
						medicaideligible=false;
					}
				}
			}
			
			//I do CHIP separately, because it includes 18 year olds as eligible
			for (int i=0; i<ages.size(); i++){
				if (ages.get(i)<19 && povertypercent<chipeligibility[state]){
					chipeligible=true;
				}
			}
			
			//Checking other additional helps
			if (States[state].equals("NY") || States[state].equals("MN")){
				if (1.333<povertypercent && povertypercent<2){
					help2eligible=true;
				}
			}
			if (States[state].equals("VT")|| States[state].equals("MA")){
				help3eligible=true;
			}
			
			
			//short coverage gap exemption:
			int monthscheck=0;
			for (int i=0; i<uninsureda; i++){
				monthscheck=Math.max((int)adultmonths.get(i), monthscheck);
			}
			for (int i=0; i<uninsuredc; i++){
				monthscheck=Math.max(childrenmonths.get(i), monthscheck);
			}

			if (monthscheck<3){
				exempt=true;
				explanation= "Your gap in coverage is small enough to exempt you from the penalty";
	
				return;
			}
			
			//eligible for catastrophic plan
			int maxage=0;
			for (int i=0; i<ages.size(); i++){
				maxage=Math.max(maxage, ages.get(i));
			}
			if (maxage<30){
				catastrophic_plan=true;
			}
			
			//not filing exemption:
			
			if (filingstatus==1){
				if (age>=65){
					if (agi<11850){
						exempt=true;
						explanation= "Your income is below the filing level";
						return;
					}
				}
				else if (age<65){
					if (agi<10300){
						exempt=true;
						explanation= "Your income is below the filing level";
						return;
					}
				}
			}
			if (filingstatus==2){
				if (age>=65){
					if (agi<14800){
						exempt=true;
						explanation= "Your income is below the filing level";
						return;
					}
				}
				else if (age<65){
					if (agi<13250){
						exempt=true;
						explanation= "Your income is below the filing level";
						return;
					}
				}
			}
			if (filingstatus==3){
				int spouseage=Integer.parseInt(peopleage.get(1).getText());
				System.out.println("Age, Spouseage "+ age+ ","+spouseage );
				if (spouseage>=65 && age>=65){
					if (agi<23100){
						exempt=true;
						explanation= "Your income is below the filing level";
						return;
					}
				}
				else if (age>=65 ||spouseage>=65){
					if (agi<21850){
						exempt=true;
						explanation= "Your income is below the filing level";
						return;
					}
				}
				else{
					if (agi<20600){
						exempt=true;
						explanation= "Your income is below the filing level";
						return;
					}
				}
			}
		
			//Unaffordable Coverage Exemption
			double affordable=.0805*agi;	//creation of an "affordable input", above which qualify for the unaffordable coverage
			
			//getting silver and bronze plans
			for (int i=0; i<silverplans.length; i++){
				if (zip==Integer.parseInt(zips[i])){
					silverplan=silverplans[i+countynum];
					bronzeplan=bronzeplans[i+countynum];
				}
			}
			


			//creation of subsidy table used to calculate your subsidy
			subsidytable=new double[8][3];
			subsidytable[0][0]=1;
			subsidytable[0][1]=0;
			subsidytable[0][2]=0;
			subsidytable[1][0]=1.33;
			subsidytable[1][1]=.0203;
			subsidytable[1][2]=0;
			subsidytable[2][0]=1.5;
			subsidytable[2][1]=.0305;
			subsidytable[2][2]=.06;
			subsidytable[3][0]=2;
			subsidytable[3][1]=.0407;
			subsidytable[3][2]=.04680;
			subsidytable[4][0]=2.5;
			subsidytable[4][1]=.0641;
			subsidytable[4][2]=.0354;
			subsidytable[5][0]=3;
			subsidytable[5][1]=.0818;
			subsidytable[5][2]=.0296;
			subsidytable[6][0]=3.5;
			subsidytable[6][1]=.0966;
			subsidytable[6][2]=0;
			subsidytable[7][0]=4;
			subsidytable[7][1]=.0966;
			subsidytable[7][2]=0;
			
			//subsidy calculator
			subsidy=0;
			//getting actual plan  values
			float household_silver=0;
			float household_bronze=0;
			float childcounter=0;	//keeping track of #of children, if it's greater than 3 stop adding to subsidy
			
			//all these if statements are to prevent more than 3 children to be added to our subsidy (if you have more than 3 children under 18, they are covered at no additional cost)
			for (int i=0; i<ages.size(); i++){
				if (ages.get(i)<18 && childcounter<3){
					household_bronze=(float)(household_bronze+(bronzeplan/1.278)*agefactors[ages.get(i)-1]);
					household_silver=(float)(household_silver+(silverplan/1.278)*agefactors[ages.get(i)-1]);
					childcounter++;
				}
				else if (ages.get(i)<18 && childcounter==3){
					continue;
				}
				else{
					household_silver=(float)(household_silver+(silverplan/1.278)*agefactors[ages.get(i)-1]);
					household_bronze=(float)(household_bronze+(bronzeplan/1.278)*agefactors[ages.get(i)-1]);
				}
			}
			
			
			outerloop:
			for (int i = 0; i < subsidytable.length; i++) {
			    for (int j = 0; j < subsidytable[0].length; j++) {
			    	//TAKE THIS OUT
			    	if (povertypercent<=1){
			    		subsidypercent=0;
			    		subsidy=household_bronze;  //ASK CYNTHIA
			    		break outerloop;
			    	}
			    	else if (povertypercent<subsidytable[i][0]){
			    		float subsidyrange=(float)subsidytable[i-1][0];
			    		double phaseout=subsidytable[i][2];
			    		double initial=subsidytable[i][1];
			    		
			    		subsidypercent=(float)(initial+phaseout*(povertypercent-subsidyrange));
			    		subsidy=(household_silver)-(float)((subsidypercent*agi)/12);
			    		//subsidy=(float)((household)*silverdiscount);
			    		break outerloop;
			    	}
			    }	    
			}
			
			float temp_subsidy=subsidy;
			subsidy=Math.min(temp_subsidy, household_bronze);
			
			//extra subsidy check (must do this before negative check)
			if (subsidy!=temp_subsidy){
				extra_subsidy=true;
			}
			
			//negative subsidy check
			if (subsidy<0){
				subsidy=0;
			}
			
			//employeeoption check
			if (employeeoffer==true){
				subsidy=0;
				return;
			}
			System.out.println(affordable);
			discounted=(household_bronze)-(subsidy);	//creating discounted bronzeplan
			if ((discounted*12)>affordable){
				exempt=true;
				explanation= "According to our data, there are no affordable plans in your area, and you are therefore exempt";
			}
			System.out.println("Poverty Percent: "+povertypercent);
			System.out.println("Subsidy: " + subsidy);
			System.out.println("Silver Plan: "+ household_silver);
			System.out.println("Subsidy Percent: " +subsidypercent);

			
			//Medicaid Gap
			String[] medigapstates={"AL", "FL", "GA", "ID", "KS", "LA", "ME","MO", "MS", "MT", "NC", "NE", "OK", "SC", "SD", "TN", "TX", "UT", "VA", "WI", "WY"};
			for (int i=0; i<=medigapstates.length-1; i++){
				if (States[state].equals(medigapstates[i])){
					if (povertypercent<=1.38){
						exempt=true;
						explanation= "Your state has not expanded Medicaid under the ACA; since you would qualify otherwise, you are exempt";
					}
				}
			}
			

		}
		
		public static double penaltycalculation(int filingstatus, ArrayList <Integer> ages, int household, int state, int uninsureda, int uninsuredc, ArrayList<Integer> adultmonths, ArrayList<Integer> childrenmonths, int agi, int zip, int countynum, boolean employeeoffer){
			//define age
			int age=Integer.parseInt(peopleage.get(0).getText());
			//check exemptions
			exemptcheck(filingstatus, ages, household, state, uninsureda, uninsuredc, adultmonths, childrenmonths, agi, zip, countynum, employeeoffer);	
			
			if (exempt==true){
				System.out.println("exempt");
				return 0;
			}
			
			//calculate penalty
			
			//subtracting filing threshold from agi to calculate income penalty
			else{
				double realagi=0;
				if (filingstatus==1){
					realagi=agi-10300;	
				}
				else if (filingstatus==2){
					realagi=agi-13250;
				}
				else if (filingstatus==3){
					int spouseage=Integer.parseInt(peopleage.get(1).getText());
					if (age>=65&&spouseage>=65){
						realagi=agi-23100;
					}
					else if (age<65&&spouseage>=65){
						realagi=agi-21850;
					}
					else if (age>=65 && spouseage<65){
						realagi=agi-21850;
					}
					else{
						realagi=agi-20600;
					}
				}
				else if (filingstatus==4){
					realagi=agi-4000;
				}
				else if (filingstatus==5){
					if (agi<65){
						realagi=agi-16600;
					}
					else{
						realagi=agi-17850;
					}
					
				}
				
				//calculating first type of penalty (income based)
				double penaltya= .025*realagi*(adultmonths.get(0)/12);	
				if (penaltya>(2676*(uninsureda+uninsuredc)*(float)(adultmonths.get(0)/12))){
					penaltya=2676*(uninsureda+uninsuredc)*(double)(adultmonths.get(0))/12;
				}
				
				//calculating second type of penalty (person based)
				double penaltyb=0;
				for (int i=0; i<uninsureda; i++){
					if (adultmonths.get(i)>3){
						penaltyb=penaltyb+695*(double)(adultmonths.get(i))/12;
					}
					else {
						short_coverage_gap="Some members of your family are exempt because their gap in coverage is less than 3 months";
					}
				}
				for (int i=0; i<uninsuredc; i++){
					if (childrenmonths.get(i)>3){
						penaltyb=penaltyb+347.5*(double)(childrenmonths.get(i))/12;
					}
					else {
						short_coverage_gap="Some members of your family are exempt because their gap in coverage is less than 3 months";
					}
				}
				if (penaltyb>2085){
					penaltyb=2085;
				}
				
				penalty=Math.max(penaltya, penaltyb);	//choosing largest 
				System.out.println("Penalty: " +penalty);
				return (penalty);
				
			}
		}


		


	}

