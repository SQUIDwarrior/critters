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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Timer;

import critters.Critter;
import critters.CritterModel;
import critters.type.FlyTrap;
import critters.type.Food;
import critters.type.Wanderer;
import critters.util.PerformanceMonitor;

/**
 * 
 * @author mike
 */
public class CritterMain extends javax.swing.JFrame {

	public static final int FIELD_WIDTH = 100; // Columns of Critters

	public static final int FIELD_HEIGHT = 50; // Rows of Critters

	public static final int SPEED = 50; // ms / frame

	private CritterModel theModel;

	private critters.gui.CritterCanvas critterCanvas1;
	private critters.gui.ControlPanel controlPanel1;
	private HashMap<Class<? extends Critter>, Integer> lastState;

	private Timer roundTimer;
	private FontMetrics fm;

	/** Creates new form CritterMain */
	public CritterMain() {
		initComponents();
		controlPanel1 = new critters.gui.ControlPanel();
		critterCanvas1 = new critters.gui.CritterCanvas();
		jPanel1.add(controlPanel1);
		jPanel2.add(critterCanvas1);
		fm = getFontMetrics(new Font("TimesRoman", Font.PLAIN, 10));
		critterCanvas1.setFont(fm.getFont());
		theModel = new CritterModel(FIELD_WIDTH, FIELD_HEIGHT);
		lastState = new HashMap<Class<? extends Critter>, Integer>();
		critterCanvas1.setModel(theModel);
		roundTimer = new Timer(SPEED, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updateGrid();
			}
		});

		controlPanel1.getStartButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				roundTimer.start();

			}
		});

		controlPanel1.getStopButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				roundTimer.stop();
			}
		});

		controlPanel1.getResetButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				resetGrid();
			}
		});

		controlPanel1.getConfigureButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ConfigureDialog diag = new ConfigureDialog(CritterMain.this, theModel);
				diag.setVisible(true);
				clearConfiguration();
				for (Class<? extends Critter> critterType : diag.getConfiguration()
						.keySet()) {
					populateGrid(diag.getConfiguration().get(critterType), critterType);
				}

				pack();
				setSize((FIELD_WIDTH + 1) * fm.getFont().getSize(), (FIELD_HEIGHT + 2)
						* (fm.getFont().getSize() + 4));

			}

		});

		getContentPane().setBackground(Color.cyan);

		// critterCanvas1.setPreferredSize(new Dimension(WINDOW_WIDTH,
		// WINDOW_HEIGHT));
		critterCanvas1.setFontmetrics(fm);
		setSize((FIELD_WIDTH + 1) * fm.getFont().getSize(), (FIELD_HEIGHT + 2)
				* (fm.getFont().getSize() + 4) + controlPanel1.getHeight());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		// setResizable(false);
	}

	public void populateGrid(int num, Class<? extends Critter> critterType) {
		theModel.populateCritter(num, critterType);
		lastState.put(critterType, new Integer(num));
		controlPanel1.addCritter(critterType, num, theModel.getPopulation());
		updateGridState();
		updateStatusBar();
		repaint();
	}

	private void updateGrid() {
		theModel.updateCritters();
		updateStatusBar();
		repaint();
	}

	private void updateStatusBar() {
		controlPanel1.update(theModel);
	}

	/**
	 * Resets the grid with the same number and type of Critters. The new grid
	 * will be randomized in the same fashion as the initial grid.
	 * 
	 */
	private void resetGrid() {
		roundTimer.stop();
		theModel.clearGrid();
		for (Class<? extends Critter> critterType : lastState.keySet()) {
			theModel.populateCritter(lastState.get(critterType).intValue(),
					critterType);
		}
		updateGridState();
		updateStatusBar();
		repaint();
	}
	

	private void clearConfiguration() {
		theModel.clearGrid();
		controlPanel1.clear();
		lastState.clear();
	}

	private void updateGridState() {
		theModel.updateGridState();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel1.setLayout(new java.awt.BorderLayout());
		getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

		jPanel2.setLayout(new java.awt.BorderLayout());
		getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args
	 *          the command line arguments
	 */
	public static void main(String args[]) {
		CritterMain cg = new CritterMain();
		cg.populateGrid(100, Wanderer.class);
		cg.populateGrid(100, FlyTrap.class);
		cg.populateGrid(100, Food.class);
		
		Thread t = new Thread(new Runnable(){

			public void run() {
				while(true)
				{
					System.out.printf("cpu=%.4f\n", (PerformanceMonitor.getInstance().getCpuUsage() * 100.0));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}},"PerformanceMonitor");
		t.setDaemon(true);
		t.start();

	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	// End of variables declaration//GEN-END:variables

}