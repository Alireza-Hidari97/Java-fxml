package com.example.alirezahaidari_comp228lab4;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class StudentsInformation extends Application{

    final ToggleGroup radioGroup = new ToggleGroup();

    //List  to display the chosen courses
    ListView<String> courseList = new ListView<>();

    //The personal information of the student
    TextField [] studentInfomation = new TextField[7];

    //To add major and displaying in textarea
    String major = "";

    //To add activity and display it in textarea
    String activities = "";

    //checkboxes of activities

    CheckBox chk_Box1 = new CheckBox("Student Council");
    CheckBox chk_Box2 = new CheckBox("Volunteer Work");


    //text area that is going to be displayed at the bottom
    TextArea infoDisplayArea = new TextArea("");



    public static void main(String[] args)
    {
        launch(StudentsInformation.class, args);
    }


    @Override
    public void start(Stage stage)
    {
        // Border pane
        BorderPane border = new BorderPane();

        HBox hbox = addHBox();
        border.setTop(hbox);
        border.setLeft(addLeftVBox());

        // Flow pane set to the right
        border.setRight(addFlowPane());

        //Grid pane set to the center
        border.setCenter(addAnchorPane(addGridPane()));

        border.setBottom(addBottomVBox());

        Scene scene = new Scene(border);
        stage.setScene(scene);
        stage.setTitle("Student Information");
        stage.show();
    }

    /* HBox  */

    private HBox addHBox()
    {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 15));
        hbox.setSpacing(10);

        return hbox;
    }

    /* VBox  */

    private VBox addLeftVBox()
    {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15));
        vbox.setSpacing(25);
        vbox.setPrefHeight(400);

        /* Labels Definitions */
        Text[] fieldTags = new Text[]
                {

                        new Text("Name: "),
                        new Text("Address: "),
                        new Text("Province: "),
                        new Text("City: "),
                        new Text("Postal Code: "),
                        new Text("Phone Number: "),
                        new Text("Email: ")
                };


        for (Text tag:fieldTags)
        {
            tag.setFont(Font.font("Times New Roman ", FontWeight.BOLD, 14));
            VBox.setMargin(tag, new Insets(0, 0, 0, 8));
            vbox.getChildren().add(tag);
        }

        return vbox;
    }


    /* Grid */
    private GridPane addGridPane()
    {

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setPadding(new Insets(0, 20, 0, 10));

        /*add the  number required from the TextFields to the grid
         and in the studentInfo array  */
        for(int i=0; i<studentInfomation.length;i++)
        {
            TextField textField = new TextField();
            grid.add(textField, 0, i);
            studentInfomation[i] = textField;
        }

        //Checkboxes for extra activities
        Text checkBoxesTitle = new Text("Activities: ");
        checkBoxesTitle.setFont(Font.font("Times New Roman", 14));
        grid.add(checkBoxesTitle,1,0);

        grid.add(chk_Box1,1,1);
        grid.add(chk_Box2,1,2);


        return grid;
    }

    /* check which CheckBox is selected Method */
    public void checkActivities()
    {
        if(chk_Box1.isSelected() || chk_Box2.isSelected()  )
        {
            activities="" +
                    "\n Active in : ";
            if(chk_Box1.isSelected())
            {
                activities+=" Student Council,";
            }
            else
            {
                activities.replace(" Student Council,", "");
            }
            if(chk_Box2.isSelected()){
                activities+=" Volunteer Work,";
            }
            else
            {
                activities.replace(" Volunteer Work,", "");
            }

        }
        else
        {
            activities="\n No Additional activities";
        }
    }

    /* Flow pane with major and courses list */
    private FlowPane addFlowPane()
    {

        FlowPane flowpanes = new FlowPane();
        flowpanes.setPadding(new Insets(10, 10, 10, 10));
        flowpanes.setVgap(10);
        flowpanes.setHgap(10);
        flowpanes.setPrefWrapLength(150);


        RadioButton rbCompScience = new RadioButton("Computer Science");
        rbCompScience.setToggleGroup(radioGroup);

        RadioButton rbBusiness = new RadioButton("Business");
        rbBusiness.setToggleGroup(radioGroup);

        ComboBox<String> combo_Box = new ComboBox<>();
        combo_Box.setPrefWidth(100);


        radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {

            //Method to change the choice of items in comboBox
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle)
            {
                if (radioGroup.getSelectedToggle() != null && radioGroup.getSelectedToggle()==rbBusiness)
                {
                    ObservableList<String> options = FXCollections.observableArrayList("Marketing", "Management", "Economics");
                    combo_Box.getItems().clear();
                    courseList.getItems().clear();
                    combo_Box.getItems().addAll(options);
                    major = "Business";
                }
                else
                {
                    ObservableList<String> options = FXCollections.observableArrayList("Java", "Python", "C#");
                    combo_Box.getItems().clear(); //Clearing Existing Items
                    courseList.getItems().clear(); //Clearing Existing Course List
                    combo_Box.getItems().addAll(options); //adding new Courses for Computer Science
                    major = "Computer Science";
                }
            }
        });


        // Handler Creation and Registration
        combo_Box.setOnAction(e ->
        {
            if(!courseList.getItems().contains(combo_Box.getSelectionModel().getSelectedItem())&&
                    combo_Box.getSelectionModel().getSelectedItem()!=null )
            {
                courseList.getItems().add(combo_Box.getSelectionModel().getSelectedItem());
            }
        });

        courseList.setPrefHeight(100);
        flowpanes.getChildren().addAll(rbCompScience,rbBusiness,combo_Box,courseList);
        return flowpanes;
    }



    /* "Display buttons" */
    private AnchorPane addAnchorPane(GridPane grid)
    {

        AnchorPane anchorpane = new AnchorPane();

        Button buttonDisplay = new Button("Display");

        buttonDisplay.setOnMouseClicked((new EventHandler<MouseEvent>()
        {
            //Method that the display of the student's information in the TextArea
            public void handle(MouseEvent event)
            {
                infoDisplayArea.clear(); //clear area for updating info

                infoDisplayArea.appendText("Personal information: "+ gatherPersonalInfo()+
                        activities+
                        "\nMajor: "+ major +
                        "\nCourses: "+courseList.getItems().toString()
                        .replace("[", "").replace("]", ""));
            }
        }));

        HBox hbx = new HBox();
        hbx.setPadding(new Insets(1, 10, 10, 10));
        hbx.getChildren().add(buttonDisplay);

        anchorpane.getChildren().addAll(grid,hbx);
        AnchorPane.setBottomAnchor(hbx, 14.0);
        AnchorPane.setRightAnchor(hbx, 14.0);
        AnchorPane.setTopAnchor(grid, 14.0);

        return anchorpane;
    }

    //Gather  personal information in one String
    public StringBuilder gatherPersonalInfo()
    {
        StringBuilder mainInformation = new StringBuilder();
        checkActivities();
        for (TextField t:studentInfomation)
        {
            if(!t.getText().equals("")){
                mainInformation.append(t.getText()).append(", ");}

        }
        return mainInformation;
    }

    // Display student's information in TextArea
    private VBox addBottomVBox()
    {

        VBox textAreaInfo = new VBox();
        textAreaInfo.setPadding(new Insets(10));
        textAreaInfo.setPrefHeight(250);

        textAreaInfo.getChildren().add(infoDisplayArea);

        return textAreaInfo;
    }

}
