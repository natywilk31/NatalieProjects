/*
 * This will create a path object. We can choose some points on our map to create a path, 
 * and then we can save it or load a new map.
 * 
 * @author Natalie Wilkins
 * 2022-11-09
 * 
 */

package path;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PathEditor extends JPanel implements Runnable, ActionListener, MouseListener {

	JMenuItem loadItem;
	JMenuItem saveItem;
	JMenuItem clearItem;
	BufferedImage backdrop;
	Path ourPath;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new PathEditor());

	}

	@Override
	public void run() {

		System.out.println("This is running.");

		ourPath = new Path();

		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(600, 600));
		this.setPreferredSize(new Dimension(600, 600));
		f.setContentPane(this);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		loadItem = new JMenuItem("Load");

		saveItem = new JMenuItem("Save");
		
		clearItem = new JMenuItem("Clear");
		

		f.setJMenuBar(menuBar);

		menuBar.add(fileMenu);
		fileMenu.add(loadItem);
		fileMenu.add(saveItem);
		fileMenu.add(clearItem);

		loadItem.addActionListener(this);
		saveItem.addActionListener(this);
		clearItem.addActionListener(this);;

		f.pack();
		f.setVisible(true);

		this.addMouseListener(this);

		try {
			backdrop = javax.imageio.ImageIO.read(new File("path_2.jpg"));
		} catch (IOException e) {
			System.out.println("We have an error with the backdrop.");
		}

	}

	public void paint(Graphics g) {

		g.drawRect(1, 100, 30, 30);
		g.drawImage(backdrop, 0, 0, null);
		ourPath.draw(g);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ourPath.pointsList.add(new Point ((int)e.getX(), (int) e.getY()));
		repaint();

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loadItem)
			loadPath();
		if (e.getSource() == saveItem)
			savePath();
		if (e.getSource() == clearItem)
			clearPath();

	}

	/**
	 * Called when the user has selected the 'load' menu item. Prompts the user for
	 * a file to load, then loads the shapes from that file. The existing
	 * arrangement of shapes is lost.
	 * 
	 * If the user cancels the load dialog, no action is taken.
	 */
	private void loadPath() {
		// Ask the user for a file to load. Restrict their choices
		// to files that end in .path
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Path files", "path");
		chooser.setFileFilter(filter);
		int result = chooser.showOpenDialog(this);
		if (result != JFileChooser.APPROVE_OPTION)
			return; // Bail out - user cancelled
		// Get the file the user selected.
		File f = chooser.getSelectedFile();
		// Load the shapes.
		try {
			Scanner in = new Scanner(f);
			// The first integer in the file specifies the number
			// of shapes. Rebuild the shape array to be the correct
			// size.
			
			ourPath = new Path(in);
			// Build a new path from the file it gives us.

		}

		catch (IOException e) {
			// It would be better to put up an error dialog box, like this:
			// JOptionPane.showConfirmDialog(this, "Could not load that
			// file.");
			System.out.println("error loading.");
		}
		// Repaint the window. (Changing the array does not change
		// the screen. We must re it.)
		repaint();
	}

	/**
	 * Called when the user has selected the 'save' menu item. The user is asked to
	 * specify a save filename. The Path array list is written to that file (so that
	 * it can be loaded later). The filename extension is guaranteed to be .path and
	 * the file is a plain text file. (You can view the file in any editor that will
	 * open a plain text file.)
	 * 
	 * If the user cancels the filename selection dialog, no action is taken.
	 */
	private void savePath() {
		// Ask the user for a save filename.
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showSaveDialog(this);
		if (result != JFileChooser.APPROVE_OPTION)
			return; // Bail out - user cancelled
		File f = chooser.getSelectedFile();
		// Added after lecture: It would be a good idea to make sure
		// the filename ends in .path
		// If not, add .path to the end of the filename.
		String completeFilePath = f.getAbsolutePath();
		if (!completeFilePath.endsWith(".path"))
			f = new File(completeFilePath + ".path");
		// Save the file.
		try {
			PrintWriter out = new PrintWriter(f);
			out.println(ourPath.toString());
			out.close();
		} catch (IOException e) {
			// It would be better to put up an error dialog box, like this:
			// JOptionPane.showConfirmDialog(this, "Could not load that
			// file.");
			System.out.println("Error.");
		}
	}
	
	
	/*
	 * lets us clear the path. 
	 */
	private void clearPath() {
		ourPath = new Path();
		repaint();
	}

}
