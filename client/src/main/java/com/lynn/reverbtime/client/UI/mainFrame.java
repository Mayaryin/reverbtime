package com.lynn.reverbtime.client.UI;

import com.lynn.reverbtime.client.Calculations.Sabine;
import com.lynn.reverbtime.client.RoomComponent.RoomComponent;
import com.lynn.reverbtime.client.RoomComponent.RoomComponentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class mainFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField tfVolume;
    private JComboBox boxMat1;
    private JComboBox boxMat2;
    private JComboBox boxMat3;
    private JComboBox boxMat4;
    private JButton calcButton;
    private JLabel labelReverb;
    private JTextField tfSize1;
    private JTextField tfSize2;
    private JTextField tfSize3;
    private JTextField tfSize4;
    private JLabel labelMaterial;
    private JLabel labelSize;
    private JLabel labelVolume;
    private JComboBox boxMatCeil;
    private JComboBox boxMatFloor;
    private JLabel labelWall1;
    private JLabel labelWall2;
    private JLabel labelWall3;
    private JLabel labelWall4;
    private JLabel labelCeiling;
    private JLabel labelFloor;
    private JTextField tfSize5;
    private JTextField tfSize6;
    private JLabel labelMat1;
    private JLabel labelMat2;
    private JLabel labelMat3;
    private JLabel labelMat4;
    private JLabel labelMatCeil;
    private JButton buttonSubmit;
    private JTextField tfAddName;
    private JTextField tfAddAlpha;
    private JLabel labelAdd;
    private JLabel labelAlpha;
    private JLabel labelDelete;
    private JComboBox boxDelete;
    private JButton buttonDelete;
    private JLabel labelMatFloor;
    private Sabine sabine;
    protected final RoomComponentClient client;
    protected RoomComponent[] allComps;

    /*
    Constructor for the GUI
    */
    @Autowired
    public mainFrame() {
        this.client = new RoomComponentClient(WebClient.builder(), "https://reverbtime.herokuapp.com/");
        setContentPane(mainPanel);
        setTitle("ReverbTime");
        setSize(1000, 700);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        addActionListeners();
        retrieveData();
        style();
    }

    /*
     Adds action listeners to all interactice GUI items
    */
    private void addActionListeners() {
        boxMat1.addActionListener(new BoxHandler(labelMat1));
        boxMat2.addActionListener(new BoxHandler(labelMat2));
        boxMat3.addActionListener(new BoxHandler(labelMat3));
        boxMat4.addActionListener(new BoxHandler(labelMat4));
        boxMatCeil.addActionListener(new BoxHandler(labelMatCeil));
        boxMatFloor.addActionListener(new BoxHandler(labelMatFloor));
        calcButton.addActionListener(new calcButtonHandler());
        buttonSubmit.addActionListener(new submitButtonHandler());
        buttonDelete.addActionListener(new deleteButtonHandler());
    }

    /*
     Calls the service class instance to make a GET request in order to retrieve all registered room components
     from the database. Calls parser function to eventually display the names of room components in the drop down
     menus
    */
    private void retrieveData() {
        this.allComps = client.getComponents();
        parseCompNamesToComboBoxes(allComps);
    }

    /*
     Writes room component names into the models of all comobo boxes (drop down menus).
    */
    public void parseCompNamesToComboBoxes(RoomComponent[] comps) {
        List<String> names = new LinkedList<String>();
        for (RoomComponent r : comps) {
            names.add(r.getName());
        }
        String[] namesArray = names.toArray(new String[0]);
        DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>(namesArray);
        boxMat1.setModel(model1);
        DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>(namesArray);
        boxMat2.setModel(model2);
        DefaultComboBoxModel<String> model3 = new DefaultComboBoxModel<>(namesArray);
        boxMat3.setModel(model3);
        DefaultComboBoxModel<String> model4 = new DefaultComboBoxModel<>(namesArray);
        boxMat4.setModel(model4);
        DefaultComboBoxModel<String> model5 = new DefaultComboBoxModel<>(namesArray);
        boxMatCeil.setModel(model5);
        DefaultComboBoxModel<String> model6 = new DefaultComboBoxModel<>(namesArray);
        boxMatFloor.setModel(model6);
        DefaultComboBoxModel<String> modelDelete = new DefaultComboBoxModel<>(namesArray);
        boxDelete.setModel(modelDelete);
    }

    /*
     Sets font to all GUI objects and default texts to combo boxes.
    */
    public void style() {
        Font font = new Font("Futura", Font.PLAIN, 14);
        Font alphaFont = new Font("Futura", Font.PLAIN, 18);
        mainPanel.setFont(font);
        tfVolume.setFont(font);

        boxMat1.setFont(font);
        boxMat2.setFont(font);
        boxMat3.setFont(font);
        boxMat4.setFont(font);
        boxMatCeil.setFont(font);
        boxMatFloor.setFont(font);

        boxMat1.setSelectedItem("Select");
        boxMat2.setSelectedItem("Select");
        boxMat3.setSelectedItem("Select");
        boxMat4.setSelectedItem("Select");
        boxMatCeil.setSelectedItem("Select");
        boxMatFloor.setSelectedItem("Select");

        calcButton.setFont(font);
        calcButton.setBorderPainted(true);
        labelReverb.setFont(font);

        tfSize1.setFont(font);
        tfSize2.setFont(font);
        tfSize3.setFont(font);
        tfSize4.setFont(font);
        tfSize5.setFont(font);
        tfSize6.setFont(font);

        labelMaterial.setFont(font);
        labelSize.setFont(font);
        labelVolume.setFont(font);
        labelAlpha.setFont(alphaFont);

        labelWall1.setFont(font);
        labelWall2.setFont(font);
        labelWall3.setFont(font);
        labelWall4.setFont(font);
        labelCeiling.setFont(font);
        labelFloor.setFont(font);

        labelMat1.setFont(font);
        labelMat2.setFont(font);
        labelMat3.setFont(font);
        labelMat4.setFont(font);
        labelMatCeil.setFont(font);
        labelMatFloor.setFont(font);

        labelAdd.setFont(font);
        tfAddName.setFont(font);
        tfAddAlpha.setFont(font);
        buttonSubmit.setFont(font);

        labelDelete.setFont(font);
        boxDelete.setFont(font);
        boxDelete.setSelectedItem("Select");
        buttonDelete.setFont(font);
    }


    /*
    Subclass that handles item selection in combo boxes: When an item is selected the corresponding
    room component is retrieved from the server via a GET request, and its alpha value displayed on
    a label
    */
    private class BoxHandler implements ActionListener {

        private JLabel destination;

        public BoxHandler(JLabel destination) {
            this.destination = destination;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox box = (JComboBox) e.getSource();
            String name = box.getSelectedItem().toString();
            for (RoomComponent r : allComps) {
                if (r.getName() == name) {
                    destination.setText(r.getAlpha().toString());
                    break;
                }
            }
        }
    }

    /*
    Subclass that handles press of calculation button: on click, all selected items from the wall material combo boxes
    as well as texts from the size text fields are read and then handed over to the calculations class "Sabine". It also
    updates the text in the results textfield
    */
    private class calcButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<Double> alphas = List.of(labelMat1.getText(), labelMat2.getText(),
                                labelMat3.getText(), labelMat4.getText(),
                                labelMatCeil.getText(), labelMatFloor.getText())
                        .stream().map(i -> Double.parseDouble(i))
                        .collect(Collectors.toList());
                List<Double> surfaceAreas = List.of(tfSize1.getText(), tfSize2.getText(),
                                tfSize3.getText(), tfSize4.getText(), tfSize5.getText(), tfSize6.getText())
                        .stream()
                        .map(i -> Double.parseDouble(i))
                        .collect(Collectors.toList());

                sabine = new Sabine(Double.parseDouble(tfVolume.getText()), surfaceAreas, alphas);
                Double reverb = sabine.calculateReverbTime();
                String s = String.format("%,.2f", reverb);
                labelReverb.setText("<html><body><center>Reverberation Time: <br>" + s + " seconds</center></body></html>");
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormat Exception: invalid input string");
            }
        }
    }

    /*
    Subclass that handles click on submit button: New room component is created and added to database if no other
    object with the same name is already registered
    */
    private class submitButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Double alpha = Double.parseDouble(tfAddAlpha.getText());
                String name = tfAddName.getText().substring(0, 1).toUpperCase() + tfAddName.getText().substring(1);
                RoomComponent newComp = new RoomComponent(name, alpha);
                try {
                    client.addComponentToDataBase(newComp);
                } catch (Exception ex) {
                    System.out.println("Exception: Name taken");
                }
                retrieveData();
            } catch (NumberFormatException nfe) {
                System.out.println("NumberFormat Exception in alpha value field: invalid input string");
            }
        }
    }

    /*
    Subclass that handles click on delete button: Referring object is deleted from database and a new array of objects
    is loaded and distributed to the drop down menus.
    */
    private class deleteButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            client.removeComponentFromDataBase(boxDelete.getSelectedItem().toString());
            retrieveData();
        }
    }
}
