/**
 * This is the main class for the TeacherReportAssistant.
 */

import java.awt.GridBagConstraints;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class TeacherReportAssistant extends JFrame
{
  private MenuBar traMenuBar;
  private ArrayList<Student> students;
  private int studentsIndex;
  private Map<String, GenderWordPair> genderWordsDict;
  private ArrayList<TemplateCategory> templates;
  private ContentPanelWDesigner contentPanel;
  
 

  public TeacherReportAssistant()
  {
    super("TeacherReportAssistant");
    //super.setSize(800, 480);
    super.getContentPane().add(new JPanel());
    
    templates = new ArrayList<TemplateCategory>();
    students = new ArrayList<Student>();
    genderWordsDict = new HashMap<String, GenderWordPair>();
    
    students.add(new Student(" ","First: File->Open Student Names"));
    ArrayList<String> tmPlates = new ArrayList<String>();
    //tmPlates.add("  ");
    //tmPlates.add("  ");
    TemplateCategory tc=new TemplateCategory(" ",tmPlates);
    templates.add(tc);
//
  //templates.add(tc);
    

    traMenuBar = new MenuBar(this);
    setJMenuBar(traMenuBar);
       
    contentPanel = new ContentPanelWDesigner(this);
    //this.setContentPane(contentPanel);
    this.setContentPanel(contentPanel);
    
    readGenderWordPairs();

  }

  
  
  public void advanceToNextStudent()
  {
    // copy checked templates to editable text
    updateStudentReportFromEditableText();
    studentsIndex++;
    if (studentsIndex>=students.size()) studentsIndex=students.size()-1;
    updateStudentNameLabel();
    contentPanel.setFocusToFirstTab();
    
    // Set editable text to text for student
    updateEditableTextFromStudentReport();
  }

  public void backToPriorStudent()
  {
    updateStudentReportFromEditableText();
    studentsIndex--;
    if (studentsIndex<0)studentsIndex=0;
    if (students.size()==0) studentsIndex=-1;
    updateStudentNameLabel();
    contentPanel.setFocusToFirstTab();

    // Set editable text to text for student
    updateEditableTextFromStudentReport();
  }
  
  public void updateStudentReportFromEditableText()
  {
    if (students.size()==0) return;
    if (studentsIndex==-1) return;
    String r=contentPanel.getEditableTest();
    students.get(studentsIndex).setReport(r);
  }
  

  public void updateEditableTextFromStudentReport()
  {
    String sr="";
    if (students.size()==0)
      sr="";
    else if (studentsIndex==-1) 
      sr="";
    else
      sr = students.get(studentsIndex).getReport();
    contentPanel.setEditableText(sr);
  }
  
  public String getReports()
  {
    // Make sure current editable text is copied to student
    updateStudentReportFromEditableText();
    
    String retVal="";
    for (Student s : getStudents()) {
      String sReport=s.getReport().trim();
      if (sReport.length()==0) continue;
      retVal+="\n\n"+s.getName();
      retVal+="\n"+s.getReport();
    }
    return retVal;
  }
  
  private void updateStudentNameLabel()
  {
    if(studentsIndex<0)return;
    contentPanel.updateStudentNameLabel(students.get(studentsIndex));
  }
  
  public String getStudentName()
  {
    return students.get(studentsIndex).getName();
  }

  public String getStudentGender()
  {
    return students.get(studentsIndex).getGender();
  }
  
  
  public String getStudentReport()
  {
    return students.get(studentsIndex).getReport();
  }

  public void setStudentReport(String r)
  {
    students.get(studentsIndex).setReport(r);
  }


 
  public ArrayList<TemplateCategory> getTemplateCategories()
  {
    return templates;
  }

  public void setTemplates(ArrayList<TemplateCategory> templates)
  {
    this.templates = templates;
  }

  public Map<String, GenderWordPair> getGenderWordsDict()
  {
    return genderWordsDict;
  }

  public void setGenderWordsDict(Map<String, GenderWordPair> genderWordsDict)
  {
    this.genderWordsDict = genderWordsDict;
  }

  public void setStudents(ArrayList<Student> students)
  {
    this.students = students;
    studentsIndex = -1;
  }

  public ArrayList<Student> getStudents()
  {
    return students;
  }
  

  public ContentPanelWDesigner getContentPanel()
  {
    return contentPanel;
  }
  public void setContentPanel(ContentPanelWDesigner cp)
  {
    contentPanel=cp;
    
    GridBagConstraints gbc = new GridBagConstraints();          
    gbc.gridx=0;
    gbc.gridy=0;
    gbc.weightx=1.0;
    gbc.weighty=1.0;
    gbc.fill = GridBagConstraints.BOTH;

    {
    JFrame jf = new JFrame();
    jf.getContentPane().removeAll();

    //jf.getContentPane().add(new TemplatesPanelIncApplyWDesigner(this,templates.get(1/*templates.size()-1*/)));
    jf.getContentPane().add(new TabbedTemplatePanel2WDesigner(this));
    //jf.setContentPane(cp,gbc);
    jf.setTitle("Test Content Panel");
    jf.setSize(800, 480);
    jf.pack();
    jf.setVisible(true);
    }
  
    
    this.getContentPane().removeAll();
    this.getContentPane().add(cp);
    this.pack();
    //this.setContentPane(contentPanel);
    this.getContentPane().revalidate();
    this.repaint();

    
    
//    this.invalidate();
//    this.validate();
  }
  
  private void readGenderWordPairs() {

    //String pathSeparator = System.getProperty("file.separator"); 
    try {

     
      ClassLoader classLoader = getClass().getClassLoader();
      //File file = new File(classLoader.getResource(".."+pathSeparator+"resources"+pathSeparator+"genderWordPairs.tra").getFile());
      File file = new File(classLoader.getResource("resources/genderWordPairs.tra").getFile());
      FileInputStream fstream = new FileInputStream(file);
      BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

      String strLine;

      // Read File Line By Line
      while ((strLine = br.readLine()) != null) {
        strLine = strLine.trim();

        // check for a comment or empty line
        if (strLine.length() == 0)
          continue;
        if (strLine.substring(0, 1).equals("#"))
          continue;
        // Split line into male and female gender words
        String[] parsed = strLine.split(" ", 2);

        GenderWordPair gwp = new GenderWordPair(parsed[0], parsed[1]);
        genderWordsDict.put(parsed[0], gwp);
        genderWordsDict.put(parsed[1], gwp);
      }
      br.close();
    }
    catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
  



  public MenuBar getTraMenuBar() {
    return traMenuBar;
  }

  public static void main(String[] args)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception ex)
    {
    }

    TeacherReportAssistant window = new TeacherReportAssistant();
    //window.setBounds(100, 100, 900, 600);
    window.setBounds(100, 50, 620, 750);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setVisible(true);
  }
}

