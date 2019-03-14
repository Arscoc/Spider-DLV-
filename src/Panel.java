import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;

public class Panel extends JPanel {

	private static String encodingResource = "encodings/spider";
	private static String instanceResource = "encodings/spider-instance";

	private static Handler handler;
	AnswerSets answers = null;

	int column;
	int line;
	int pos = 0;
	ArrayList<rightMove> moves = new ArrayList<rightMove>();
	ImageLoader loader = new ImageLoader();
	Table t = new Table();
	MouseListener l;
	Boolean endgame = true;
	Boolean rewriteFacts = true;
	int n = 0;

	public Panel() {
		setLayout(null);
		setBackground(Color.BLACK);

		handler = new DesktopHandler(new DLVDesktopService("lib/dlv.mingw.exe"));

		JButton give = new JButton("DAI CARTE");
		give.setBounds(770, 50, 120, 50);
		give.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < 10; i++) {
					t.mainDeck.get(0).hide = false;
					t.columns.get(i).add(t.mainDeck.get(0));
					t.mainDeck.remove(0);
				}
				Values.selected = false;
				Values.tip = false;
				repaint();
				rewriteFacts = true;
			}
		});
		JButton restart = new JButton("RICOMINCIA");
		restart.setBounds(770, 120, 120, 50);
		restart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				t = null;
				t = new Table();
				Values.selected = false;
				Values.tip = false;
				moves.clear();
				repaint();
			}
		});

		JButton help = new JButton("AIUTO");
		help.setBounds(770, 190, 120, 50);
		help.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent arg0) {
				Write();
				answerSets();
				//AutoMove();
				
				
				
/*
				if (rewriteFacts == false) {
					for (int i = 0; i < moves.size(); i++) {
						if (n < moves.size() - 1) {
							Values.tipX = moves.get(n).column;
							Values.tipY = moves.get(n).line;
						}
					}
					if (n == moves.size() - 1)
						n = 0;
					else
						n++;
					repaint();

				} else {
					moves.clear();
					Write();
					answerSets();
					if (n < moves.size() - 1) {
						Values.tipX = moves.get(n).column;
						Values.tipY = moves.get(n).line;
					}
					if (n == moves.size() - 1)
						n = 0;
					else
						n++;
					repaint();
					rewriteFacts = false;

				}
				Values.tip = true;
*/
			}
		});

		add(give);
		add(restart);
		add(help);

		l = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				column = GetColumn(e.getX());
				line = GetLine(e.getY(), column);

				if (Values.selected == false) {
					cardSelection(column, line);
					repaint();
				}

				else {
					cardMovement(column, line);
					repaint();
				}

				deleteStack();
				repaint();

				for (int i = 0; i < 10; i++)
					if (t.columns.get(i).size() > 0 || t.mainDeck.size() > 0) {
						endgame = false;
					}
				if (endgame) {

					System.out.println("HAI VINTO");
					System.exit(1);

				} else
					endgame = true;

			}
		};
		addMouseListener(l);
		setVisible(true);

	}

	@Override
	protected void paintComponent(final Graphics g) {
		int oriz = 20;
		int vert = 10;
		String s;
		super.paintComponent(g);
		for (int i = 0; i < 10; i++) {
			g.drawRect(oriz, vert, Values.CardDimensionX, Values.CardDimensionY);
			for (int j = 0; j < t.columns.get(i).size(); j++) {
				s = t.columns.get(i).get(j).value + "_of_" + t.columns.get(i).get(j).suit;
				if (t.columns.get(i).get(j).hide == true)
					g.drawImage(loader.Back(), oriz, vert, null);
				else
					g.drawImage(loader.Front(s), oriz, vert, null);
				vert += 20;
			}
			vert = 10;
			oriz += (Values.CardDimensionX + 10);

		}
		if (Values.selected) {
			g.drawImage(loader.selUp(), 15 + (Values.selectedPosX * (10 + Values.CardDimensionX)),
					5 + (Values.selectedPosY * 20), null);
			g.drawImage(loader.selUp(), 15 + (Values.selectedPosX * (10 + Values.CardDimensionX)),
					5 + ((Values.selectedPosY + Values.cardsSelected - 1) * 20) + Values.CardDimensionY + 5, null);
			g.drawImage(loader.selSide(Values.cardsSelected), 15 + (Values.selectedPosX * (10 + Values.CardDimensionX)),
					10 + (Values.selectedPosY * 20), null);
		}
		if (Values.tip == true) {

			g.drawImage(loader.tipUp(), 15 + (Values.tipX * (10 + Values.CardDimensionX)), 5 + (Values.tipY * 20),
					null);
			g.drawImage(loader.tipUp(), 15 + (Values.tipX * (10 + Values.CardDimensionX)),
					((t.columns.get(Values.tipX).size()) * 20) + Values.CardDimensionY - 10, null);
			g.drawImage(loader.tipSide(t.columns.get(Values.tipX).size() - Values.tipY),
					15 + (Values.tipX * (10 + Values.CardDimensionX)), 10 + (Values.tipY * 20), null);
		}

	}

	public int GetColumn(int x) {
		int oriz = 20;
		for (int i = 0; i < 10; i++) {
			if (Between(x, oriz, oriz + Values.CardDimensionX))
				return i;
			oriz += (Values.CardDimensionX + 10);
		}
		return -1;
	}

	public int GetLine(int y, int col) {
		if (col < 0)
			return -1;
		int vert = 10;
		int size = t.columns.get(col).size() - 1;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < size; j++) {
				if (Between(y, vert, vert + 20))
					return j;
				vert += 20;
			}
			if (size == -1)
				return 0;
			if (Between(y, vert, vert + Values.CardDimensionY))
				return size;
			vert = 10;
		}
		return -1;
	}

	public void cardSelection(int x, int y) {
		Boolean check = true;
		if (x != -1 && y != -1 && t.columns.get(x).get(y).hide == false) {
			int size = t.columns.get(x).size() - 1;

			for (int i = y; i < size; i++) {
				if (t.columns.get(x).get(i).suit != t.columns.get(x).get(i + 1).suit
						|| t.columns.get(x).get(i).value <= t.columns.get(x).get(i + 1).value) {
					check = false;
				}
			}
			if (check) {
				Values.cardsSelected = size - y + 1;
				Values.selectedPosX = x;
				Values.selectedPosY = y;
				Values.selected = true;

			}
			rewriteFacts = true;
		}
		Values.tip = false;
	}

	public void cardMovement(int x, int y) {

		if (t.columns.get(x).size() == 0 && x != -1 && y != -1)
			for (int i = 0; i < Values.cardsSelected; i++) {
				Card sel = t.columns.get(Values.selectedPosX).get(Values.selectedPosY);

				t.columns.get(x).add(sel);
				t.columns.get(Values.selectedPosX).remove(Values.selectedPosY);
				t.columns.get(Values.selectedPosX).get(t.columns.get(Values.selectedPosX).size() - 1).hide = false;

				rewriteFacts = true;
				moves.clear();
			}

		else if (x != -1 && y != -1
				&& (t.columns.get(x).get(y).value == (t.columns.get(Values.selectedPosX).get(Values.selectedPosY).value)
						+ 1)) {

			for (int i = 0; i < Values.cardsSelected; i++) {
				Card sel = t.columns.get(Values.selectedPosX).get(Values.selectedPosY);

				t.columns.get(x).add(sel);
				t.columns.get(Values.selectedPosX).remove(Values.selectedPosY);
			}

			if (t.columns.get(Values.selectedPosX).size() > 0)
				t.columns.get(Values.selectedPosX).get(t.columns.get(Values.selectedPosX).size() - 1).hide = false;
		}
		Values.selected = false;
		Values.cardsSelected = 0;
		Values.selectedPosX = -1;
		Values.selectedPosY = -1;

	}

	public void deleteStack() {

		Boolean b = true;
		int size;
		for (int i = 0; i < 10; i++) {

			size = t.columns.get(i).size();

			for (int j = 0; j < size; j++) {

				if (t.columns.get(i).get(j).value == 13 && j + 13 <= size && t.columns.get(i).get(j).hide == false) {

					for (int s = j; s < j + 13 - 1; s++) {
						if ((t.columns.get(i).get(s).suit != t.columns.get(i).get(s + 1).suit)
								|| (t.columns.get(i).get(s).value != t.columns.get(i).get(s + 1).value + 1))
							b = false;

					}

					if (b) {
						for (int s = j; s < j + 13; s++) {
							t.columns.get(i).remove(j);
						}

						return;
					}
				}

				b = true;
			}

		}
	}

	public Boolean Between(int x, int a, int b) {
		if (x >= a && x < b)
			return true;
		return false;
	}

	public void Write() {
		String s;

		int seme = 0;
		FileOutputStream outputStream;
		try {
			PrintWriter writer = new PrintWriter("encodings/spider-instance");
			writer.print("");
			writer.close();
			outputStream = new FileOutputStream("encodings/spider-instance");

			for (int i = 0; i < 10; i++) {
				int size = t.columns.get(i).size();

				for (int j = 0; j < size; j++) {

					if (t.columns.get(i).get(j).hide == false) {
						if (t.columns.get(i).get(j).suit == "spades")
							seme = 0;
						if (t.columns.get(i).get(j).suit == "diamonds")
							seme = 1;
						if (t.columns.get(i).get(j).suit == "hearts")
							seme = 2;
						if (t.columns.get(i).get(j).suit == "clubs")
							seme = 3;

						s = "card" + "(" + i + "," + j + "," + t.columns.get(i).get(j).value + "," + seme + ")" + "."
								+ "\n";

						byte[] strToBytes = s.getBytes();
						outputStream.write(strToBytes);

					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void answerSets() {
		InputProgram program = new ASPInputProgram();

		program.addFilesPath(encodingResource);
		program.addFilesPath(instanceResource);
		handler.addProgram(program);

		try {
			ASPMapper.getInstance().registerClass(rightMove.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Output o = handler.startSync();

		answers = (AnswerSets) o;
		System.out.println("entra");
		
		for (AnswerSet a : answers.getAnswersets()) {

			try {

				for (Object obj : a.getAtoms()) {
					if (obj instanceof rightMove) {
						rightMove c = (rightMove) obj;
						System.out.println("inserisce");
//						System.out.print(c + " ");
						moves.add(c);
					}

				}
				System.out.println();
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (int i = 0; i < moves.size(); i++) {

				System.out.print(moves.get(i).column + "-");
				System.out.print(moves.get(i).line + "-");
				System.out.println(moves.get(i).to);

			}
		}
	}
	
	public void AutoMove()
	{
		if (moves.size()==0) {
			for (int i = 0; i < 10; i++) {
				t.mainDeck.get(0).hide = false;
				t.columns.get(i).add(t.mainDeck.get(0));
				t.mainDeck.remove(0);
			}
			Values.selected = false;
			Values.tip = false;
			repaint();}
		else {
			for (int i =moves.get(0).line; i<t.columns.get(moves.get(0).column).size(); i++)
			{
				t.columns.get(moves.get(0).to).add(t.columns.get(moves.get(0).column).get(i));
			}
			for (int i =moves.get(0).column; i<10; i++)
			{		
				t.columns.get(moves.get(0).column).remove(moves.get(0).line);
			}
			repaint();
			}
	}
	
	
	
	
	
}