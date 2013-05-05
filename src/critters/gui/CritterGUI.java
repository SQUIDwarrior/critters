/*
 * Copyright (c) 2013 Mike Deats
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created on May 4, 2013
 */

package critters.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import critters.Critter;
import critters.CritterModel;



public class CritterGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1692328667196926478L;

	public static final int TEXT_SIZE_H = 10;
	
	public static final int TEXT_SIZE_V = 12;
	
	public static final int FIELD_WIDTH = 100; // Columns of Critters
	
	public static final int FIELD_HEIGHT = 90; // Rows of Critters
	
	public static final int SIDEBAR_WIDTH = 140;

	public static final int WINDOW_WIDTH = (FIELD_WIDTH +1)* TEXT_SIZE_H + SIDEBAR_WIDTH;

	public static final int WINDOW_HEIGHT = (FIELD_HEIGHT + 2) * (TEXT_SIZE_V);	
	
	public static final int SPEED = 50; // ms / frame

	@SuppressWarnings("unused")
	private static FontMetrics fm;
	
	private static CritterCanvas canvas;
	
	private static JButton startButton;
	
	private static JButton stopButton;
	
	private static JButton resetButton;
	
	private static JButton stepButton;
	
	private static JTextArea statusBar;
	
	private CritterModel theModel;
	
	private HashMap<Class<? extends Critter>, Integer> initialState;
	
	private Timer roundTimer;
		
	public CritterGUI()
	{		
		theModel = new CritterModel();
		initialState = new HashMap<Class<? extends Critter>, Integer> ();
		init();
	}

	public void init()
	{		
		// Setup default font		
		fm = getFontMetrics(new Font("TimesRoman", Font.PLAIN, TEXT_SIZE_H));

		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);	
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Where the critters will live
		canvas = new CritterCanvas(); // create the canvas
		
		
		JPanel sideBar = new JPanel(); // a Panel to hold the control buttons		
		sideBar.setLayout(new GridLayout(5, 1));
		sideBar.setPreferredSize(new Dimension(SIDEBAR_WIDTH, WINDOW_HEIGHT));
		
		statusBar = new JTextArea();		
		sideBar.add(statusBar);

		roundTimer = new Timer(SPEED, new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				updateGrid();
			}
		});
		
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				roundTimer.start();
				
			}			
		});
		sideBar.add(startButton);
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				roundTimer.stop();				
			}});
		sideBar.add(stopButton);
		
		// TODO add reset behavior
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				resetGrid();
				
			}});
		sideBar.add(resetButton);
		
		stepButton = new JButton("Step");
		stepButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				updateGrid();
				
			}});
		sideBar.add(stepButton);
		
		
		// Add all the objects to the content pane
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(canvas, BorderLayout.CENTER);
		getContentPane().add(sideBar, BorderLayout.EAST);		
		
		getContentPane().setBackground(Color.cyan);
		
		// Finally, set the window to visible
		setVisible(true);
		setResizable(false);	
		
	} // end init()

	
	
	public void populateGrid(int num, Class<? extends Critter>critterType)
	{
		theModel.populateCritter(num, critterType);
		initialState.put(critterType, new Integer(num));
		updateGridState();
		updateStatusBar();
		repaint();
		
	}
	
	private void updateGrid()
	{		
		
		theModel.updateCritters();
		updateStatusBar();
		repaint();
		
	}
	
	private void updateStatusBar() {
		String str = "";
		for(Class<? extends Critter> critterType : initialState.keySet())					
		{
			int pop = theModel.getPopulationCounters().get(critterType).intValue();
			String name = critterType.getName();
			name = name.substring(13);
			str += name + "\t= " + pop + "\n";
		}
		statusBar.setText(str);		
		
	}

	/**
	 * Resets the grid with the same number and type of Critters.
	 * The new grid will be randomized in the same fashion as the
	 * initial grid.
	 *
	 */
	private void resetGrid()
	{
		roundTimer.stop();
		theModel.clearGrid();
		for(Class<? extends Critter> critterType : initialState.keySet())
		{
			theModel.populateCritter(initialState.get(critterType).intValue(), critterType);
		}
		updateGridState();
		updateStatusBar();
		repaint();
	}
	
	private void updateGridState()
	{		
		theModel.updateGridState();
	}
	
	private class CritterCanvas extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -6871194126192369071L;

		public void paintComponent(Graphics g)
		{
			for(int i = 0; i < FIELD_HEIGHT; i++)
				for(int j = 0; j < FIELD_WIDTH; j++)
				{
					Critter critter = theModel.getCritterAt(new Point(j, i));
					
					if(critter != null)
						g.drawString(critter.getSymbol()+ "", j * (TEXT_SIZE_H), i * (TEXT_SIZE_V));
			}
		}
		
	}
}


