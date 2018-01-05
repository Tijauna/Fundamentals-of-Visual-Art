/*--------------------------------------------------------------------------------------*/
/*  BasicsOfArt.java  -  Teaches elementary students about the basic fundamentals of    */
/*  visual art, such as colour theory, shading techniques and composition.              */
/*--------------------------------------------------------------------------------------*/
/*  Author:  Daniel Ding                                                                */
/*  Date:  April 18th, 2016                                                             */
/*--------------------------------------------------------------------------------------*/
/*  Input:  Lesson selection, quiz selection, navigation buttons, quiz multiple choice  */
/*  Output: Lesson content, quizzes, homepage controls and quiz options                 */
/*  15 Pages lesson 1, 11 pages lesson 2, 9 pages lesson 3, 3 quizzes (10 questions)    */
/*--------------------------------------------------------------------------------------*/

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;

public class BasicsOfArt extends Applet
    implements ActionListener
{
    //fixed values for lesson pages
    final int lessonCount1 = 15;
    final int lessonCount2 = 11;
    final int lessonCount3 = 9;

    //variables needed
    //1 -> homepage
    //2 -> colour lesson
    //3 -> technique lesson
    //4 -> composition lesson
    //5, 6, 7 --> corresponding tests
    int lessonNum = 1;
    int lessonPage = 1;
    int quizNum = 1;
    int response;
    int beginTest = 0;
    //score counter and animation sequencing
    int[] score = new int [1];
    int scoreCheck = 0;

    //double buffer
    private Image dbImage;
    private Graphics dbg;

    //quiz toggles, unlock toggle and finished toggle
    int unlock1 = 0;
    int unlock2 = 0;
    int unlock3 = 0;

    int finish1 = 0;
    int finish2 = 0;
    int finish3 = 0;

    //fonts
    Font Garamond = new Font ("Garamond", Font.PLAIN, 15);
    Font CenturyGothic = new Font ("Century Gothic", Font.PLAIN, 20);
    Font SmallCenturyGothic = new Font ("Century Gothic", Font.PLAIN, 15);

    //UI images
    Image button1, button2, button3;
    Image homeBackground, lessonBackground, quizBackground;
    Image lock;
    Image animation1;

    //lesson and quiz images
    //lesson 1
    Image l1p1, l1p2, l1p3, l1p4, l1p5, l1p6, l1p7, l1p8, l1p9, l1p10, l1p11, l1p12, l1p13, l1p14, l1p15;
    //lesson 2
    Image l2p1, l2p2, l2p3, l2p4, l2p5, l2p6, l2p7, l2p8, l2p9, l2p10, l2p11;
    //quiz 2
    Image l2q1, l2q2, l2q3;
    //lesson 3
    Image l3p1, l3p2, l3p3, l3p4, l3p5, l3p6, l3p7, l3p8, l3p9;
    //quiz 3
    Image l3q1, l3q2, l3q3, l3q4, l3q5, l3q6;

    //declare main ui buttons
    Button UI_1 = new Button ("Colour Theory");
    Button UI_2 = new Button ("Line Technique");
    Button UI_3 = new Button ("Composition");

    //test buttons
    Button test_1 = new Button ("Quiz 1");
    Button test_2 = new Button ("Quiz 2");
    Button test_3 = new Button ("Quiz 3");

    //controls for going next, prev, home
    Button next = new Button ("Next");
    Button previous = new Button ("Previous");
    Button home = new Button ("Return Home");

    Button quizNext = new Button ("Next");

    //quiz checkboxes
    CheckboxGroup choice;
    Checkbox choice1, choice2, choice3, choice4;

    //quiz class
    class quiz
    {
	//answer integer array
	int[] answer = new int [10];
	//string for the name of the quiz itself
	String name;

	//set answers for quiz
	public void setAnswer (int one, int two, int three, int four, int five, int six, int seven, int eight, int nine, int ten)
	{
	    this.answer [0] = one;
	    this.answer [1] = two;
	    this.answer [2] = three;
	    this.answer [3] = four;
	    this.answer [4] = five;
	    this.answer [5] = six;
	    this.answer [6] = seven;
	    this.answer [7] = eight;
	    this.answer [8] = nine;
	    this.answer [9] = ten;
	}

	//set name for quiz
	public void setName (String name)
	{
	    this.name = name;
	}
    }


    //quiz display method
    public void quizDisplay (Graphics g, quiz quiz1)
    {
	//visibility toggle
	lessonVisibility ();
	previous.setVisible (false);
	next.setVisible (false);

	//title and background
	g.drawImage (quizBackground, 0, 0, this);
	g.setFont (new Font ("Century Gothic", Font.PLAIN, 40));
	g.drawString (quiz1.name, 50, 100);
	g.setFont (new Font ("Century Gothic", Font.PLAIN, 20));
    }


    //check score method
    public void scorePaint (Graphics g, quiz quiz1)
    {
	g.setFont (SmallCenturyGothic);

	//display the current question number
	if (quizNum != 11)
	{
	    g.drawString ("Question " + quizNum, 625, 50);
	    choiceVisible ();
	}

	//check if the test begins
	if (beginTest == 1)
	{
	    g.drawString ("Score: " + String.valueOf (score [0]) + "/10", 625, 100);
	    //correct
	    if (response == quiz1.answer [quizNum - 2])
	    {
		g.setColor (Color.green);
		g.drawString ("Correct!", 625, 75);

		//prevent score from ticking with refresh rate
		if (scoreCheck == 1)
		{
		    score [0] = score [0] + 1;
		}
	    }
	    //false
	    else if ((response != quiz1.answer [quizNum - 2]) || (response == 0))
	    {
		g.setColor (Color.red);
		g.drawString ("False!", 625, 75);
	    }
	}
    }


    //lesson display method
    public void lessonPaint (Graphics g)
    {
	//display info at bottom of screen
	g.setColor (Color.white);
	g.setFont (new Font ("Century Gothic", Font.PLAIN, 15));
	g.drawString ("Lesson " + (lessonNum - 1), 15, 720);
	g.drawString ("Page: " + lessonPage, 680, 720);
    }


    //quiz visibility method
    public void choiceVisible ()
    {
	choice1.setVisible (true);
	choice2.setVisible (true);
	choice3.setVisible (true);
	choice4.setVisible (true);
    }


    //quiz invisibility method
    public void choiceInvisible ()
    {
	choice1.setVisible (false);
	choice2.setVisible (false);
	choice3.setVisible (false);
	choice4.setVisible (false);
    }


    //lesson visibility preset
    public void lessonVisibility ()
    {
	UI_1.setVisible (false);
	UI_2.setVisible (false);
	UI_3.setVisible (false);

	test_1.setVisible (false);
	test_2.setVisible (false);
	test_3.setVisible (false);

	home.setVisible (true);
    }


    //intialize method
    public void init ()
    {
	setLayout (null);

	//set size of the window
	this.setSize (750, 750);

	//main triple home buttons
	//add button, set font, set fore/back colours, set size, add the event listener
	//lesson 1 button; colour theory
	add (UI_1);
	UI_1.setFont (Garamond);
	UI_1.setForeground (Color.cyan);
	UI_1.setBackground (Color.gray);
	UI_1.setBounds (100, 400, 150, 50);
	UI_1.addActionListener (this);

	//lesson 2 button; line technique
	add (UI_2);
	UI_2.setFont (Garamond);
	UI_2.setForeground (Color.green);
	UI_2.setBackground (Color.gray);
	UI_2.setBounds (300, 400, 150, 50);
	UI_2.addActionListener (this);

	//lesson 3 button; composition
	add (UI_3);
	UI_3.setFont (Garamond);
	UI_3.setForeground (Color.pink);
	UI_3.setBackground (Color.gray);
	UI_3.setBounds (500, 400, 150, 50);
	UI_3.addActionListener (this);

	//quiz buttons
	add (test_1);
	test_1.setBounds (110, 510, 130, 30);
	test_1.addActionListener (this);

	add (test_2);
	test_2.setBounds (310, 510, 130, 30);
	test_2.addActionListener (this);

	add (test_3);
	test_3.setBounds (510, 510, 130, 30);
	test_3.addActionListener (this);

	//home button
	add (home);
	home.setBounds (310, 700, 130, 30);
	home.addActionListener (this);

	//forward and back buttons
	//forward
	add (next);
	next.setBounds (500, 700, 100, 30);
	next.addActionListener (this);

	//back
	add (previous);
	previous.setBounds (150, 700, 100, 30);
	previous.addActionListener (this);

	//homepage background
	homeBackground = getImage (getDocumentBase (), "backgroundnew.jpg");

	//lesson background
	lessonBackground = getImage (getDocumentBase (), "graphLesson.jpg");

	//quiz background
	quizBackground = getImage (getDocumentBase (), "graphquiz.jpg");

	//animation
	animation1 = getImage (getDocumentBase (), "cinemagraph5new.gif");

	//file handling for button images
	button1 = getImage (getDocumentBase (), "lesson1.png");
	button2 = getImage (getDocumentBase (), "lesson2.png");
	button3 = getImage (getDocumentBase (), "lesson3.png");

	//lock image
	lock = getImage (getDocumentBase (), "lock.png");

	//lesson images
	//lesson 1
	l1p1 = getImage (getDocumentBase (), "l1p1.jpg");
	l1p2 = getImage (getDocumentBase (), "l1p2.jpg");
	l1p3 = getImage (getDocumentBase (), "l1p3.jpg");
	l1p4 = getImage (getDocumentBase (), "l1p4.jpg");
	l1p5 = getImage (getDocumentBase (), "l1p5.jpg");
	l1p6 = getImage (getDocumentBase (), "l1p6.jpg");
	l1p7 = getImage (getDocumentBase (), "l1p7.jpg");
	l1p8 = getImage (getDocumentBase (), "l1p8.jpg");
	l1p9 = getImage (getDocumentBase (), "l1p9.jpg");
	l1p10 = getImage (getDocumentBase (), "l1p10.jpg");
	l1p11 = getImage (getDocumentBase (), "l1p11.jpg");
	l1p12 = getImage (getDocumentBase (), "l1p12.jpg");
	l1p13 = getImage (getDocumentBase (), "l1p13.jpg");
	l1p14 = getImage (getDocumentBase (), "l1p14.jpg");
	l1p15 = getImage (getDocumentBase (), "l1p15.jpg");

	//lesson 2
	l2p1 = getImage (getDocumentBase (), "l2p1.jpg");
	l2p2 = getImage (getDocumentBase (), "l2p2.jpg");
	l2p3 = getImage (getDocumentBase (), "l2p3.jpg");
	l2p4 = getImage (getDocumentBase (), "l2p4.jpg");
	l2p5 = getImage (getDocumentBase (), "l2p5.jpg");
	l2p6 = getImage (getDocumentBase (), "l2p6.jpg");
	l2p7 = getImage (getDocumentBase (), "l2p7.jpg");
	l2p8 = getImage (getDocumentBase (), "l2p8.jpg");
	l2p9 = getImage (getDocumentBase (), "l2p9.jpg");
	l2p10 = getImage (getDocumentBase (), "l2p10.jpg");
	l2p11 = getImage (getDocumentBase (), "l2p11.jpg");

	//quiz 2
	l2q1 = getImage (getDocumentBase (), "l2q1.jpg");
	l2q2 = getImage (getDocumentBase (), "l2q2.jpg");
	l2q3 = getImage (getDocumentBase (), "l2q3.jpg");

	//lesson 3
	l3p1 = getImage (getDocumentBase (), "l3p1.jpg");
	l3p2 = getImage (getDocumentBase (), "l3p2.jpg");
	l3p3 = getImage (getDocumentBase (), "l3p3.jpg");
	l3p4 = getImage (getDocumentBase (), "l3p4.jpg");
	l3p5 = getImage (getDocumentBase (), "l3p5.jpg");
	l3p6 = getImage (getDocumentBase (), "l3p6.jpg");
	l3p7 = getImage (getDocumentBase (), "l3p7.jpg");
	l3p8 = getImage (getDocumentBase (), "l3p8.jpg");
	l3p9 = getImage (getDocumentBase (), "l3p9.jpg");

	//quiz 3
	l3q1 = getImage (getDocumentBase (), "l3q1.jpg");
	l3q2 = getImage (getDocumentBase (), "l3q2.jpg");
	l3q3 = getImage (getDocumentBase (), "l3q3.jpg");
	l3q4 = getImage (getDocumentBase (), "l3q4.jpg");
	l3q5 = getImage (getDocumentBase (), "l3q5.jpg");
	l3q6 = getImage (getDocumentBase (), "l3q6.jpg");

	//quiz elements
	choice = new CheckboxGroup ();

	//multiple choice checkboxes
	choice1 = new Checkbox ("A", choice, false);
	choice1.setBounds (500, 300, 80, 20);
	choice2 = new Checkbox ("B", choice, false);
	choice2.setBounds (500, 400, 80, 20);
	choice3 = new Checkbox ("C", choice, false);
	choice3.setBounds (500, 500, 80, 20);
	choice4 = new Checkbox ("D", choice, false);
	choice4.setBounds (500, 600, 80, 20);

	//add the choices
	add (choice1);
	add (choice2);
	add (choice3);
	add (choice4);

	//add the quiz next button
	add (quizNext);
	quizNext.setBounds (500, 700, 100, 30);
	quizNext.addActionListener (this);

	//custom cursor
	Cursor cr = null;
	Toolkit tk = Toolkit.getDefaultToolkit ();
	Image cursor = getImage (getDocumentBase (), "cursor.png");
	Point hotSpot = new Point (0, 0);
	//create the custom cursor
	cr = tk.createCustomCursor (cursor, hotSpot, "Pointer");
	setCursor (cr);
    }


    //double buffering for no flickering
    public void update (Graphics g)
    {
	dbImage = createImage (this.getSize ().width, this.getSize ().height);
	dbg = dbImage.getGraphics ();

	// initialize buffer
	if (dbImage == null)
	{

	}
	// clear screen in background
	dbg.setColor (getBackground ());
	dbg.fillRect (0, 0, this.getSize ().width, this.getSize ().height);

	//draw elements in background
	dbg.setColor (getForeground ());
	paint (dbg);

	//draw the final image
	g.drawImage (dbImage, 0, 0, this);
    }


    //method for rendering the graphics of the program
    public void paint (Graphics g)
    {
	switch (lessonNum)
	{
		//draw homepage
	    case 1:
		//make invis
		home.setVisible (false);
		next.setVisible (false);
		previous.setVisible (false);

		choiceInvisible ();

		//draw the background
		g.drawImage (homeBackground, 0, 0, this);

		g.drawImage (animation1, -145, 0, this);

		//triple main images
		g.drawImage (button1, 100, 200, this);
		g.drawImage (button2, 300, 200, this);
		g.drawImage (button3, 500, 200, this);

		//test grey background
		g.setColor (Color.darkGray);
		g.fillRect (100, 500, 150, 50);
		g.fillRect (300, 500, 150, 50);
		g.fillRect (500, 500, 150, 50);

		//background and buttons visible
		UI_1.setVisible (true);
		UI_2.setVisible (true);
		UI_3.setVisible (true);

		//quiz function reset
		quizNext.setVisible (false);
		quizNum = 1;
		beginTest = 0;
		scoreCheck = 0;
		score [0] = 0;

		//unlocking quiz
		//unlock quiz 1
		if (unlock1 == 1)
		{
		    test_1.setVisible (true);
		}

		else
		{
		    test_1.setVisible (false);
		    g.drawImage (lock, 150, 500, this);
		}

		//unlock quiz 2
		if (unlock2 == 1)
		{
		    test_2.setVisible (true);
		}

		else
		{
		    test_2.setVisible (false);
		    g.drawImage (lock, 350, 500, this);
		}

		//unlock quiz 3
		if (unlock3 == 1)
		{
		    test_3.setVisible (true);
		}

		else
		{
		    test_3.setVisible (false);
		    g.drawImage (lock, 550, 500, this);
		}

		//display quiz stats
		if (finish1 == 1)
		{
		    g.setColor (Color.darkGray);
		    g.fillRect (100, 585, 150, 25);
		    g.setColor (Color.white);
		    g.drawString ("Quiz 1 Complete", 130, 600);

		}

		if (finish2 == 1)
		{
		    g.setColor (Color.darkGray);
		    g.fillRect (300, 585, 150, 25);
		    g.setColor (Color.white);
		    g.drawString ("Quiz 2 Complete", 330, 600);
		}

		if (finish3 == 1)
		{
		    g.setColor (Color.darkGray);
		    g.fillRect (500, 585, 150, 25);
		    g.setColor (Color.white);
		    g.drawString ("Quiz 3 Complete", 530, 600);
		}

		break;

		//lesson 1
	    case 2:
		//control the button visibility
		lessonVisibility ();

		if (lessonPage == 1)
		{
		    g.drawImage (l1p1, 0, 0, this);
		    previous.setVisible (false);
		    next.setVisible (true);
		}

		else if (lessonPage == 2)
		{
		    g.drawImage (l1p2, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 3)
		{
		    g.drawImage (l1p3, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 4)
		{
		    g.drawImage (l1p4, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 5)
		{
		    g.drawImage (l1p5, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 6)
		{
		    g.drawImage (l1p6, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 7)
		{
		    g.drawImage (l1p14, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 8)
		{
		    g.drawImage (l1p7, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 9)
		{
		    g.drawImage (l1p8, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 10)
		{
		    g.drawImage (l1p9, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 11)
		{
		    g.drawImage (l1p10, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 12)
		{
		    g.drawImage (l1p11, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 13)
		{
		    g.drawImage (l1p12, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 14)
		{
		    g.drawImage (l1p13, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == lessonCount1)
		{

		    g.drawImage (l1p15, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (false);

		    //unlock the quiz
		    unlock1 = 1;
		}

		//lesson controls method
		lessonPaint (g);
		break;

		//lesson 2
	    case 3:
		//control the button visibility
		lessonVisibility ();

		if (lessonPage == 1)
		{
		    g.drawImage (l2p1, 0, 0, this);
		    previous.setVisible (false);
		    next.setVisible (true);
		}

		else if (lessonPage == 2)
		{
		    g.drawImage (l2p2, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 3)
		{
		    g.drawImage (l2p3, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 4)
		{
		    g.drawImage (l2p4, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 5)
		{
		    g.drawImage (l2p5, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 6)
		{
		    g.drawImage (l2p6, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 7)
		{
		    g.drawImage (l2p7, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 8)
		{
		    g.drawImage (l2p8, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 9)
		{
		    g.drawImage (l2p9, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 10)
		{
		    g.drawImage (l2p10, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == lessonCount2)
		{
		    g.drawImage (l2p11, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (false);

		    //unlock the quiz
		    unlock2 = 1;
		}

		//lesson controls method
		lessonPaint (g);
		break;


		//lesson 3
	    case 4:
		//control the button visibility
		lessonVisibility ();

		if (lessonPage == 1)
		{
		    g.drawImage (l3p1, 0, 0, this);
		    previous.setVisible (false);
		    next.setVisible (true);
		}

		else if (lessonPage == 2)
		{
		    g.drawImage (l3p2, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 3)
		{
		    g.drawImage (l3p3, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 4)
		{
		    g.drawImage (l3p4, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 5)
		{
		    g.drawImage (l3p5, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 6)
		{
		    g.drawImage (l3p6, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 7)
		{
		    g.drawImage (l3p7, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == 8)
		{
		    g.drawImage (l3p8, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (true);
		}

		else if (lessonPage == lessonCount3)
		{
		    g.drawImage (l3p9, 0, 0, this);
		    previous.setVisible (true);
		    next.setVisible (false);

		    //unlock the quiz
		    unlock3 = 1;
		}

		//lesson controls method
		lessonPaint (g);
		break;

		//quiz 1
	    case 5:
		//quiz object and answers
		quiz quiz1 = new quiz ();
		quiz1.setAnswer (1, 3, 2, 4, 2, 4, 3, 1, 2, 2);
		quiz1.setName ("COLOUR THEORY QUIZ");

		//call the quiz method
		quizDisplay (g, quiz1);

		//answer a
		if (quizNum == 1)
		{
		    g.drawString ("Who invented the colour wheel?", 250, 200);
		    g.drawString ("Isaac Newton", 300, 320);
		    g.drawString ("Fred Varley", 300, 420);
		    g.drawString ("Michelangelo", 300, 520);
		    g.drawString ("Picasso", 300, 620);
		    quizNext.setVisible (true);
		}

		//answer c
		else if (quizNum == 2)
		{
		    g.drawString ("What are colours opposite each other on the colour wheel known as?", 25, 200);
		    g.drawString ("Analogous", 300, 320);
		    g.drawString ("Tertiary", 300, 420);
		    g.drawString ("Complimentary", 300, 520);
		    g.drawString ("Contrasting", 300, 620);
		    quizNext.setVisible (true);
		}

		//answer b
		else if (quizNum == 3)
		{
		    g.drawString ("What are colours beside each other on the colour wheel known as?", 25, 200);
		    g.drawString ("Secondary", 300, 320);
		    g.drawString ("Analogous", 300, 420);
		    g.drawString ("Complimentary", 300, 520);
		    g.drawString ("Paired", 300, 620);
		    quizNext.setVisible (true);
		}

		//answer d
		else if (quizNum == 4)
		{
		    g.drawString ("Red, Yellow and Blue are known as?", 100, 200);
		    g.drawString ("Tertiary Colours", 250, 320);
		    g.drawString ("Principle Colours", 250, 420);
		    g.drawString ("Core Colours", 250, 520);
		    g.drawString ("Primary Colours", 250, 620);
		    quizNext.setVisible (true);
		}

		//answer b
		else if (quizNum == 5)
		{
		    g.drawString ("What can be created by mixing primary colours?", 100, 200);
		    g.drawString ("Tertiary Colours", 250, 320);
		    g.drawString ("Secondary Colours", 250, 420);
		    g.drawString ("Additive Colours", 250, 520);
		    g.drawString ("Sequential Colours", 250, 620);
		    quizNext.setVisible (true);
		}

		//answer d
		else if (quizNum == 6)
		{
		    g.drawString ("How many main colours are there on the colour wheel?", 100, 200);
		    g.drawString ("24", 300, 320);
		    g.drawString ("8", 300, 420);
		    g.drawString ("16", 300, 520);
		    g.drawString ("12", 300, 620);
		    quizNext.setVisible (true);
		}

		///answer c
		else if (quizNum == 7)
		{
		    g.drawString ("When black is added to a colour, it is known as a:", 100, 200);
		    g.drawString ("Tint", 300, 320);
		    g.drawString ("Shadow", 300, 420);
		    g.drawString ("Shade", 300, 520);
		    g.drawString ("Hue", 300, 620);
		    quizNext.setVisible (true);
		}

		//answer a
		else if (quizNum == 8)
		{
		    g.drawString ("Blue, purple and cyan are examples of:", 100, 200);
		    g.drawString ("Cool Colours", 250, 320);
		    g.drawString ("Balanced Colours", 250, 420);
		    g.drawString ("Chilled Colours", 250, 520);
		    g.drawString ("Toned Colours", 250, 620);
		    quizNext.setVisible (true);
		}

		//answer b
		else if (quizNum == 9)
		{
		    g.drawString ("Grey is added to a pure colour. It is now a:", 100, 200);
		    g.drawString ("Shade", 300, 320);
		    g.drawString ("Tone", 300, 420);
		    g.drawString ("Hue", 300, 520);
		    g.drawString ("Gradation", 300, 620);
		    quizNext.setVisible (true);
		}

		//answer b
		else if (quizNum == 10)
		{
		    g.drawString ("Orange, green and purple are examples of:", 100, 200);
		    g.drawString ("Tertiary Colours", 250, 320);
		    g.drawString ("Secondary Colours", 250, 420);
		    g.drawString ("Mixed Colours", 250, 520);
		    g.drawString ("Variant Colours", 250, 620);
		    quizNext.setVisible (true);
		}

		//quiz end
		else if (quizNum == 11)
		{
		    g.drawImage (lessonBackground, 0, 0, this);
		    choiceInvisible ();
		    quizNext.setVisible (false);
		    g.drawString ("Quiz complete!", 300, 200);
		    finish1 = 1;
		}

		//call the quiz method
		scorePaint (g, quiz1);

		//reset score ticker
		scoreCheck = 0;

		break;

		//quiz 2
	    case 6:
		//quiz object and answers
		quiz quiz2 = new quiz ();
		quiz2.setAnswer (2, 2, 4, 3, 1, 4, 3, 1, 2, 4);
		quiz2.setName ("LINE TECHNIQUE QUIZ");

		//call the quiz method
		quizDisplay (g, quiz2);

		//answer b
		if (quizNum == 1)
		{
		    g.drawString ("What is the relationship between graphite hardness and darkness?", 25, 200);
		    g.drawString ("Increased hardness, increased darkness", 50, 320);
		    g.drawString ("Increased hardness, decreased darkness", 50, 420);
		    g.drawString ("No correlation", 50, 520);
		    g.drawString ("Decreased softness, Increased darkness", 50, 620);
		    quizNext.setVisible (true);
		}

		//answer b
		else if (quizNum == 2)
		{
		    g.drawString ("You are given an 8H, 2B and HB pencil. Which one is the darkest? ", 25, 200);
		    g.drawString ("8H", 50, 320);
		    g.drawString ("2B", 50, 420);
		    g.drawString ("HB", 50, 520);
		    g.drawString ("8H and HB are the same", 50, 620);
		    quizNext.setVisible (true);
		}

		//answer d
		else if (quizNum == 3)
		{
		    g.drawString ("You have a 9B, 7H and HB pencil. Which would you use to form outlines?", 25, 200);
		    g.drawString ("7H", 50, 320);
		    g.drawString ("7H or HB are interchangeable", 50, 420);
		    g.drawString ("9B", 50, 520);
		    g.drawString ("HB", 50, 620);
		    quizNext.setVisible (true);
		}

		//answer c
		else if (quizNum == 4)
		{
		    g.drawString ("The use of repeated dots for shading is known as?", 25, 200);
		    g.drawString ("Scumbling", 250, 320);
		    g.drawString ("Pointing", 250, 420);
		    g.drawString ("Stippling", 250, 520);
		    g.drawString ("Dimpling", 250, 620);
		    quizNext.setVisible (true);
		}

		//answer a
		else if (quizNum == 5)
		{
		    g.drawString ("A grid-like texture indicates use of:", 50, 200);
		    g.drawString ("Cross hatching", 250, 320);
		    g.drawString ("Hatching", 250, 420);
		    g.drawString ("Criss crossing", 250, 520);
		    g.drawString ("Intersecting", 250, 620);
		    quizNext.setVisible (true);
		}

		//answer d
		else if (quizNum == 6)
		{
		    g.drawString ("Smooth circling patterns indicate:", 50, 200);
		    g.drawString ("Curling", 300, 320);
		    g.drawString ("Tiling", 300, 420);
		    g.drawString ("Rounding", 300, 520);
		    g.drawString ("Scumbling", 300, 620);
		    quizNext.setVisible (true);
		}

		///answer c
		else if (quizNum == 7)
		{
		    g.drawImage (l2q1, 20, 330, this);
		    g.drawString ("What shading technique is shown in the image?", 50, 200);
		    g.drawString ("Hatching", 350, 320);
		    g.drawString ("X-Hatching", 350, 420);
		    g.drawString ("Stippling", 350, 520);
		    g.drawString ("Scumbling", 350, 620);
		    quizNext.setVisible (true);
		}

		//answer a
		else if (quizNum == 8)
		{
		    g.drawImage (l2q2, 20, 330, this);
		    g.drawString ("What shading technique is shown in the image?", 50, 200);
		    g.drawString ("X-Hatching", 350, 320);
		    g.drawString ("Scumbling", 350, 420);
		    g.drawString ("Stippling", 350, 520);
		    g.drawString ("Hatching", 350, 620);
		    quizNext.setVisible (true);
		}

		//answer b
		else if (quizNum == 9)
		{
		    g.drawImage (l2q3, 20, 330, this);
		    g.drawString ("What shading technique is shown in the image?", 50, 200);
		    g.drawString ("X-Hatching", 350, 320);
		    g.drawString ("Scumbling", 350, 420);
		    g.drawString ("Stippling", 350, 520);
		    g.drawString ("Hatching", 350, 620);
		    quizNext.setVisible (true);
		}

		//answer d
		else if (quizNum == 10)
		{
		    g.drawString ("A 9B pencil would be:", 100, 200);
		    g.drawString ("Hard", 250, 320);
		    g.drawString ("Balanced", 250, 420);
		    g.drawString ("Very light", 250, 520);
		    g.drawString ("Very dark", 250, 620);
		    quizNext.setVisible (true);
		}

		//quiz end
		else if (quizNum == 11)
		{
		    g.drawImage (lessonBackground, 0, 0, this);
		    choiceInvisible ();
		    quizNext.setVisible (false);
		    g.drawString ("Quiz complete!", 300, 200);
		    finish2 = 1;
		}

		//call the quiz method
		scorePaint (g, quiz2);

		//reset score ticker
		scoreCheck = 0;
		break;

		//quiz 3
	    case 7:
		//title and background
		g.drawImage (quizBackground, 0, 0, this);
		g.setFont (new Font ("Century Gothic", Font.PLAIN, 40));
		g.drawString ("COMPOSITION QUIZ", 50, 100);

		//visibility toggle
		lessonVisibility ();

		//quiz object and answers
		quiz quiz3 = new quiz ();
		quiz3.setAnswer (4, 1, 2, 4, 2, 1, 3, 4, 1, 2);


		g.setFont (new Font ("Century Gothic", Font.PLAIN, 20));

		//answer d
		if (quizNum == 1)
		{
		    g.drawString ("Using symmetry and asymmetry in composition is known as:", 50, 200);
		    g.drawString ("Contrast", 300, 320);
		    g.drawString ("Rhythm", 300, 420);
		    g.drawString ("Unity", 300, 520);
		    g.drawString ("Balance", 300, 620);
		    quizNext.setVisible (true);
		}

		//answer a
		else if (quizNum == 2)
		{
		    g.drawString ("Good coherence means paying attention to:", 50, 200);
		    g.drawString ("Unity", 300, 320);
		    g.drawString ("Tertiary", 300, 420);
		    g.drawString ("Complimentary", 300, 520);
		    g.drawString ("Contrasting", 300, 620);
		    quizNext.setVisible (true);
		}

		//answer b
		else if (quizNum == 3)
		{
		    g.drawString ("A piece with large variation in shadow and light displays:", 25, 200);
		    g.drawString ("Rhythm", 300, 320);
		    g.drawString ("Contrast", 300, 420);
		    g.drawString ("Focus", 300, 520);
		    g.drawString ("Proportion", 300, 620);
		    quizNext.setVisible (true);
		}

		//answer d
		else if (quizNum == 4)
		{
		    g.drawString ("Asymmetrical composition can create a sense of", 50, 200);
		    g.drawString ("Cold", 250, 320);
		    g.drawString ("Comfort", 250, 420);
		    g.drawString ("Happiness", 250, 520);
		    g.drawString ("Unease", 250, 620);
		    quizNext.setVisible (true);
		}

		//answer b
		else if (quizNum == 5)
		{
		    g.drawImage (l3q1, 20, 330, this);
		    g.drawString ("What compositional technique is primarily shown in the image?", 25, 200);
		    g.drawString ("Focus", 350, 320);
		    g.drawString ("Rhythm", 350, 420);
		    g.drawString ("Movement", 350, 520);
		    g.drawString ("Balance", 350, 620);
		    quizNext.setVisible (true);
		}

		//answer a
		else if (quizNum == 6)
		{
		    g.drawImage (l3q2, 20, 330, this);
		    g.drawString ("What compositional technique is primarily shown in the image?", 25, 200);
		    g.drawString ("Contrast", 350, 320);
		    g.drawString ("Movement", 350, 420);
		    g.drawString ("Focus", 350, 520);
		    g.drawString ("Unity", 350, 620);
		    quizNext.setVisible (true);
		}

		///answer c
		else if (quizNum == 7)
		{
		    g.drawImage (l3q3, 20, 330, this);
		    g.drawString ("What compositional technique is primarily shown in the image?", 25, 200);
		    g.drawString ("Rhythm", 350, 320);
		    g.drawString ("Contrast", 350, 420);
		    g.drawString ("Balance", 350, 520);
		    g.drawString ("Unity", 350, 620);
		    quizNext.setVisible (true);
		}

		//answer d
		else if (quizNum == 8)
		{
		    g.drawImage (l3q4, 20, 330, this);
		    g.drawString ("What compositional technique is primarily shown in the image?", 25, 200);
		    g.drawString ("Focus", 350, 320);
		    g.drawString ("Rhythm", 350, 420);
		    g.drawString ("Balance", 350, 520);
		    g.drawString ("Movement", 350, 620);
		    quizNext.setVisible (true);
		}

		//answer a
		else if (quizNum == 9)
		{
		    g.drawImage (l3q5, 20, 330, this);
		    g.drawString ("This photograph has a strong sense of:", 25, 200);
		    g.drawString ("Focus", 350, 320);
		    g.drawString ("Rhythm", 350, 420);
		    g.drawString ("Movement", 350, 520);
		    g.drawString ("Unity", 350, 620);
		    quizNext.setVisible (true);
		}

		//answer b
		else if (quizNum == 10)
		{
		    g.drawImage (l3q6, 20, 330, this);
		    g.drawString ("This photograph has poor composition because it has improper:", 25, 200);
		    g.drawString ("Focus", 350, 320);
		    g.drawString ("Balance", 350, 420);
		    g.drawString ("Contrast", 350, 520);
		    g.drawString ("Rhythm", 350, 620);
		    quizNext.setVisible (true);
		}

		//quiz end
		else if (quizNum == 11)
		{
		    g.drawImage (lessonBackground, 0, 0, this);
		    choiceInvisible ();
		    quizNext.setVisible (false);
		    g.drawString ("Quiz complete!", 300, 200);
		    finish3 = 1;
		}

		//call the quiz method
		scorePaint (g, quiz3);

		//reset score ticker
		scoreCheck = 0;
		break;
	}
    }


    //method for detecting user input and performing associated tasks
    public void actionPerformed (ActionEvent evt)
    {

	//main lesson buttons
	//lesson 1
	if (evt.getSource () == UI_1)
	{
	    lessonNum = 2;
	}

	//lesson 2
	else if (evt.getSource () == UI_2)
	{
	    lessonNum = 3;
	}

	//lesson 3
	else if (evt.getSource () == UI_3)
	{
	    lessonNum = 4;
	}

	//home button
	else if (evt.getSource () == home)
	{
	    lessonNum = 1;
	    lessonPage = 1;
	}

	//quiz buttons
	//test 1
	else if (evt.getSource () == test_1)
	{
	    lessonNum = 5;
	}

	//test 2
	else if (evt.getSource () == test_2)
	{
	    lessonNum = 6;
	}

	//test 3
	else if (evt.getSource () == test_3)
	{
	    lessonNum = 7;
	}

	//next button
	if (evt.getSource () == next)
	{
	    lessonPage = lessonPage + 1;
	}

	//previous button
	else if (evt.getSource () == previous)
	{
	    lessonPage = lessonPage - 1;
	}

	//quiz next button
	if (evt.getSource () == quizNext)
	{
	    beginTest = 1;
	    quizNum = quizNum + 1;
	    scoreCheck = 1;
	}

	//multiple choice A
	if (choice1.getState ())
	{

	    response = 1;
	}

	//multiple choice B
	else if (choice2.getState ())
	{

	    response = 2;
	}

	//multiple choice C
	else if (choice3.getState ())
	{

	    response = 3;
	}

	//multiple choice D
	else if (choice4.getState ())
	{

	    response = 4;
	}

	//call the paint method to refresh the graphics
	repaint ();
    }
}


