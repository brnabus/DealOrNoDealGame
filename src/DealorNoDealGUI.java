
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author abc
 */
public class DealorNoDealGUI {

    public JFrame frame;
    public DealOrNoDeal game;
    public JPanel view;
    public String username;
    public int playersCase;
    private Toolkit kit;
    public int count;

    public DealorNoDealGUI() {
        count = 0;
        this.username = "";
        frame = new JFrame("Deal Or No Deal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        kit = Toolkit.getDefaultToolkit();
        frame.setSize(kit.getScreenSize().width, kit.getScreenSize().height);
        view = new JPanel(new BorderLayout());
        frame.add(view);
        welcome();
    }

    public void Deal(boolean x) {
        view.removeAll();
        if (x) {
            JPanel youWon = new JPanel(new BorderLayout());
            JLabel congrats = new JLabel("Congratulations you won: $" + game.Offer() + " Inside Case " + (playersCase + 1), SwingConstants.CENTER);
            youWon.add(congrats, BorderLayout.CENTER);
            view.add(youWon, BorderLayout.CENTER);
        } else {
            JPanel fresh = new JPanel(new BorderLayout());
            fresh.add(updatePrize1(), BorderLayout.WEST);
            fresh.add(updateCases(), BorderLayout.CENTER);
            fresh.add(updatePrize2(), BorderLayout.EAST);
            fresh.add(bottomPanel(), BorderLayout.SOUTH);
            view.add(fresh);
        }
        view.updateUI();
    }

    public void BankOffer() {
        JPanel dond = new JPanel(new BorderLayout());
        JPanel ynae = new JPanel();
        JLabel offer = new JLabel("The Banker Offers You: $" + game.Offer(), SwingConstants.CENTER);
        JButton yes = new JButton("Deal");
        JButton no = new JButton("No Deal");
        yes.setActionCommand("true");
        no.setActionCommand("false");
        class DoND implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {
                view.removeAll();
                Deal(Boolean.parseBoolean(ae.getActionCommand()));
            }
        }
        yes.addActionListener(new DoND());
        no.addActionListener(new DoND());
        dond.add(offer, BorderLayout.CENTER);
        ynae.add(yes);
        ynae.add(no);
        dond.add(ynae, BorderLayout.SOUTH);
        view.add(dond, BorderLayout.CENTER);
        view.updateUI();
    }

    public JPanel updateCases() {
        JPanel updated_panel = new JPanel(new GridLayout(5, 5));
        updated_panel.setPreferredSize(new Dimension(kit.getScreenSize().width / 2, kit.getScreenSize().height));
        JButton[] case_clicks = new JButton[26];

        class OpenCase implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {
                int caseToOpen = Integer.parseInt(ae.getActionCommand());
                view.removeAll();
                game.getCases()[caseToOpen].setOpen(true);
                JPanel fresh = new JPanel(new BorderLayout());
                JPanel updatedBottom = bottomPanel();
                updatedBottom.add(recentOpen(caseToOpen));
                fresh.add(updatePrize1(), BorderLayout.WEST);
                fresh.add(updateCases(), BorderLayout.CENTER);
                fresh.add(updatePrize2(), BorderLayout.EAST);
                fresh.add(updatedBottom, BorderLayout.SOUTH);
                count++;
                if (count == 6 || count == 11|| count == 15 || count == 18 || count == 20 || count == 22 || count == 23 || count == 24 ) {
                    BankOffer();
                }else if (count == 25){
                //finish to be implemented    
                }else{
                    view.add(fresh);
                    view.updateUI();
                }

            }

        }

        for (int i = 0; i < 26; i++) {
            if (game.getCases()[i].isOpen() || game.getCases()[i].isSelected()) {
            } else {
                case_clicks[i] = new JButton("" + (i + 1));
                case_clicks[i].setActionCommand("" + i);
                case_clicks[i].addActionListener(new OpenCase());
                updated_panel.add(case_clicks[i]);
            }
        }

        return updated_panel;
    }

    public JLabel recentOpen(int caseNum) {
        JLabel caseContained = new JLabel("Case Contained: $" + game.getCases()[caseNum].getDollarsInside().toString());
        return caseContained;
    }

    public JPanel bottomPanel() {
        JPanel bottom_panel = new JPanel();
        JLabel name = new JLabel("Player: " + username);
        JLabel caseSelected = new JLabel("Case Selected: " + (playersCase + 1));
        bottom_panel.add(name);
        bottom_panel.add(caseSelected);
        return bottom_panel;
    }

    public JPanel updatePrize1() {
        JPanel prize1 = new JPanel();
        prize1.setLayout(new BoxLayout(prize1, BoxLayout.Y_AXIS));
        prize1.setPreferredSize(new Dimension(kit.getScreenSize().width / 4, kit.getScreenSize().height));
        JLabel[] prizeLabels = new JLabel[13];
        for (int x = 0; x < 13; x++) {
            for (int i = 0; i < 26; i++) {
                if (game.getCases()[i].getDollarsInside() == game.prizes[x]) {
                    if (game.getCases()[i].isOpen()) {
                        prizeLabels[x] = new JLabel("$" + game.prizes[x], SwingConstants.CENTER);
                        prizeLabels[x].setForeground(Color.pink);
                    } else {
                        prizeLabels[x] = new JLabel("$" + game.prizes[x], SwingConstants.CENTER);
                    }
                }
            }
            prize1.add(prizeLabels[x]);
        }
        return prize1;
    }

    public JPanel updatePrize2() {
        JPanel prize2 = new JPanel();
        prize2.setLayout(new BoxLayout(prize2, BoxLayout.Y_AXIS));
        prize2.setPreferredSize(new Dimension(kit.getScreenSize().width / 4, kit.getScreenSize().height));
        JLabel[] prizeLabels = new JLabel[13];
        for (int x = 13; x < 26; x++) {
            for (int i = 0; i < 26; i++) {
                if (game.getCases()[i].getDollarsInside() == game.prizes[x]) {
                    if (game.getCases()[i].isOpen()) {
                        prizeLabels[x - 13] = new JLabel("$" + game.prizes[x], SwingConstants.RIGHT);
                        prizeLabels[x - 13].setForeground(Color.pink);
                    } else {
                        prizeLabels[x - 13] = new JLabel("$" + game.prizes[x], SwingConstants.RIGHT);
                    }
                }
            }
            prize2.add(prizeLabels[x - 13]);
        }
        return prize2;
    }

    public void intiateGame() {
        game = new DealOrNoDeal(username, playersCase);
        view.add(updateCases(), BorderLayout.CENTER);
        view.add(updatePrize1(), BorderLayout.WEST);
        view.add(updatePrize2(), BorderLayout.EAST);
        view.add(bottomPanel(), BorderLayout.SOUTH);
        view.updateUI();
    }

    public void choseACase() {
        JLabel a = new JLabel("Pick a case.");
        JPanel caseview = new JPanel();
        caseview.add(a);
        GridLayout buttons = new GridLayout(3, 10);
        caseview.setLayout(buttons);
        JButton[] cases = new JButton[26];
        class CaseSelection implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {
                String output = ae.getActionCommand();
                playersCase = Integer.parseInt(output);
                view.removeAll();
                view.updateUI();
                intiateGame();
            }

        }
        for (int x = 0; x < 26; x++) {
            cases[x] = new JButton("" + (x + 1));
            cases[x].setActionCommand("" + x);
            cases[x].addActionListener(new CaseSelection());
            caseview.add(cases[x]);
        }
        view.add(caseview, BorderLayout.CENTER);
        view.updateUI();
    }

    public void welcome() {
        JLabel welcome = new JLabel("Welcome to deal or no deal!", JLabel.CENTER);
        view.add(welcome);
        try {
            sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(DealorNoDealGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        JTextField userinput = new JTextField(20);

        JButton b = new JButton("Enter");
        class WelcomeAction implements ActionListener {

            JTextField textFieldStore;

            public WelcomeAction(JTextField textFieldInput) {
                textFieldStore = textFieldInput;
            }

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (textFieldStore.getText().length() < 1) {

                } else {
                    updateUser();
                    view.removeAll();
                    view.updateUI();
                    choseACase();
                }
            }

            public void updateUser() {
                username = textFieldStore.getText();
            }

        }
        b.addActionListener(new WelcomeAction(userinput));
        welcome.setText("What is your name?");
        JPanel subPanel = new JPanel();
        subPanel.add(userinput);
        subPanel.add(b);
        view.add(subPanel, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        new DealorNoDealGUI();
    }
}
