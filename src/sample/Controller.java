package sample;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/* https://icons8.com/icon/set/pokemon/all                          for icons */


public class Controller {
    Charset charset = Charset.forName("US-ASCII");
    //ObservableList obserSchList = FXCollections.observableArrayList();        //LINE-1

    @FXML
    private Label nextPass;

    @FXML
    private Label scid;

    @FXML
    private Button showSch;

    @FXML
    private TextArea schArea;

    @FXML
    private Label currPass;

    @FXML
    private Label currPassLabel;

    @FXML
    private Circle blinker1;

    @FXML
    private ListView<String> listOfPasses;

    @FXML
    private ComboBox sat_ids;

    @FXML
    private Line testLinePath;

    @FXML
    private Line flashLine;

    @FXML
    private Label testTimer;

    @FXML
    private MediaView media01;

    ArrayList<String> arl1= new ArrayList<String>();

    public void loadData(Path file) {
        int sq = 0;
        //obserSchList.removeAll(obserSchList);                  //LINE-2
        //String arrayLines[]=new String[100];
        //ArrayList<String> arl1= new ArrayList<String>();
        try {
            BufferedReader reader = Files.newBufferedReader(file, charset);
            String line= null;
            arl1.clear();
            while((line = reader.readLine()) != null){
                if (line.contains("TM,TC"))
                { arl1.add(line);
                    System.out.println(arl1.get(arl1.size()-1)+" Size:"+arl1.size());       // +" :"+sq  can be added at the last
                    //System.out.println("\n"+arl1+"\n");       //VALID (Shows entire array items as [, , , ])
                    if (line.contains("09:49:42")) {sq=arl1.size()-1;}
                    //System.out.println("");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            //obserSchList.addAll(arl1);                        //LINE-3
        //listOfPasses.getItems().addAll(obserSchList);         //LINE-4, line-1 to 4 was also valid instead of 'Another Line-1,2' below
        ObservableList<String> Observableitems = FXCollections.observableArrayList(arl1);     //Another Line-1
        listOfPasses.setItems(Observableitems);                                               //Another Line-2
        System.out.println("Hello there");
        //System.out.println(listOfPasses.getItems().get(3));
        //System.out.println(listOfPasses.getItems().get(sq) +"<- Array no."+sq);
        listOfPasses.getSelectionModel().select(sq);      //AWESOME
        //System.out.println(listOfPasses.getItems().contains("09:49:42"));         //Gives 'false'
        //System.out.println(listOfPasses.getSelectionModel().getSelectedItems());
        //listOfPasses.getFocusModel().focus(sq);

    }

    //ArrayList<String> arl2= new ArrayList<String>();

    public void loadDataInCheckBox1(Path file) {
        try {
            BufferedReader reader = Files.newBufferedReader(file, charset);
            String line = null;
            //arl1.clear();
            while ((line = reader.readLine()) != null ) {
                if(line.length() <= 3)
                arl1.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(arl1);
        sat_ids.setItems(observableList);
    }


    Path schFile = Paths.get("/home/ravi/Study/IntelliJ projects/Office Files/satsch.c2c");
    Path satid = Paths.get("/home/ravi/Study/IntelliJ projects/Office Files/satids.txt");
    String sc_id[] = {"C2C","C2D"};


    @FXML
    void ShowSchedule(ActionEvent event) {
        schArea.clear();
        arl1.clear();
        try (BufferedReader reader = Files.newBufferedReader(schFile, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("TM,TC") && line.contains(sc_id[1])) {
                        schArea.appendText(line +"\n");
                        scid.setText(sc_id[1]);

                } //reader.close();     //Not required
                nextPass.setText(line);
                currPass.setText(line);
            }    //How to animate the line??
            loadData(schFile);
        } catch (IOException x12) {
            System.err.format("IOException: %s%n", x12);
        }

        FadeTransition ft = new FadeTransition(Duration.seconds(0.2));
        ft.setFromValue(1.0f);
        ft.setToValue(0.0f);
        ft.setCycleCount(Animation.INDEFINITE);
        ft.setAutoReverse(true);
        ft.setNode(blinker1);
        //ft.play();

        FadeTransition ft1 = new FadeTransition(Duration.seconds(1.0));
        ft1.setFromValue(1.0f);
        ft1.setToValue(0.5f);
        ft1.setCycleCount(Animation.INDEFINITE);
        ft1.setAutoReverse(false);
        ft1.setNode(currPassLabel);
        //ft1.play();

        TranslateTransition tt = new TranslateTransition(Duration.seconds(15));
        tt.setFromX(-550);
        tt.setFromY(0);
        tt.setToX(580);
        tt.setToY(0);
        tt.setInterpolator(Interpolator.LINEAR);    //instead of "tt.setAutoReverse(false);"
        tt.setCycleCount(Timeline.INDEFINITE);  //Instead of "tt.setCycleCount(Animation.INDEFINITE);"
        tt.setNode(nextPass);
        //tt.play();

        /*PathTransition pt = new PathTransition(Duration.seconds(15),testLinePath,nextPass);
        pt.setInterpolator(Interpolator.LINEAR);
        pt.setCycleCount(Timeline.INDEFINITE);*/
        //pt.setAutoReverse(true);
        //pt.setCycleCount(Animation.INDEFINITE);*/
        //pt.setNode(nextPass);
        //pt.play();

        PathTransition pt1 = new PathTransition(Duration.seconds(2),testLinePath,flashLine);
        pt1.setAutoReverse(false);
        pt1.setCycleCount(Timeline.INDEFINITE);
        //pt1.play();

        ParallelTransition tp = new ParallelTransition(tt,ft1);
        //tp.play();
        tp.playFromStart();

        //timer t = new timer();        //VALID n WORKS
        //t.cnt = 10;
        loadDataInCheckBox1(satid);
    }
    @FXML
    void selectedListIs(MouseEvent event) {

    }

    @FXML
    void playMedia01(MouseEvent event) {

    }

    @FXML
    void listFocused() {
    //listOfPasses.toFront();
    }
    public class timer {
        int cnt = 60;
        private Timeline anime;
        private timer() {
            anime = new Timeline(new KeyFrame(Duration.seconds(1), event -> changeLabel()));
            anime.setCycleCount(Timeline.INDEFINITE);
            anime.play();
        }
        private void changeLabel() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime currT = LocalDateTime.now();
            String ct = dtf.format(currT);
            String ctUT = dtf.format(currT.minusMinutes(330));
            String cUT = Clock.systemUTC().instant().toString();
            int dn = currT.getDayOfYear();
            //System.out.println(ct+" "+ctUT+" "+cUT+"  :"+dn);     //VALID FANTASTIC
            /*if (cnt > 0){cnt--;}
            String s = cnt +"";
            testTimer.setText(s);*/
            testTimer.setText(dn + ":" + ctUT);
        }
    }
    timer t = new timer();    //VALID
}
