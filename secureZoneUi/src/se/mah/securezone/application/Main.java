package se.mah.securezone.application;


import se.mah.securezone.client.SecureZoneClient;
import se.mah.securezone.event.SecurityEventDispatcher;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

public class Main extends Application {
	
	public static int clients = 0;
	
	@Override
	public void start(final Stage primaryStage) {
		try {
			
			final SecurityEventDispatcher dispatcher = new SecurityEventDispatcher();
			
//			Build the view
			GridPane root = new GridPane();
			root.setAlignment(Pos.TOP_CENTER);
			root.setHgap(10);
			root.setVgap(10);
			root.setPadding(new Insets(25, 25, 25, 25));
			Scene scene = new Scene(root,300,200);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Axis Socket Client");
			
			//input for first camera
			Text scenetitle = new Text("Axis Socket Client");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			root.add(scenetitle, 0, 0, 1, 1);
			    
		    Button show = new Button("Add Client");
		    show.setOnAction(event -> {
		    	++Main.clients; //FIXME this sucks
		    	
		    	final Stage dialog = new Stage();
                dialog.initModality(Modality.NONE);
                dialog.initOwner(primaryStage);
                
                GridPane pane = new GridPane();
                pane.setAlignment(Pos.TOP_CENTER);
                pane.setHgap(10);
                pane.setVgap(10);
                pane.setPadding(new Insets(25, 25, 25, 25));
    			
                Scene dialogScene = new Scene(pane, 700, 500);
                dialog.setScene(dialogScene);

                pane.add(new Text("Client " + Main.clients), 0, 0);
                
                Label ipLabel = new Label("Server ip");
                pane.add(ipLabel, 0, 1);
                
                final TextField ipText = new TextField();
                pane.add(ipText, 1, 1);
                
                Label portLabel = new Label("port");
                pane.add(portLabel, 2, 1);
                
                final TextField portText = new TextField();
                pane.add(portText, 3, 1);
                
                final ImageView imageView = new ImageView();
                imageView.setSmooth(true);
                pane.add(imageView, 0, 3, 5, 5);
                
                Button connect = new Button("Connect");
                connect.setOnAction(e -> {
                	int clientId = Main.clients;
                	String ip = ipText.getText();
                	int port = Integer.valueOf(portText.getText());
                	
            		SecureZoneClient client = new SecureZoneClient(clientId, ip, port, dispatcher, imageView);
            		new Thread(client).start();                	
                });
                
                pane.add(connect, 0, 2, 4, 4);
                dialog.show();
			});
		    
		    root.add(show, 0, 1);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
