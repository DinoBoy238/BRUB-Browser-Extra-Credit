import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;

public class Browser extends Application {

	@Override
	public void start( Stage primaryStage ) {
		

		WebView webView = new WebView( );
		WebEngine webEngine = webView.getEngine( );

		//default page
		webEngine.load( "https://www.mtu.edu" );

		

		//create search bar
		TextField searchBar = new TextField();
		searchBar.setPromptText("Enter a URL");

		// allows you to click off and onto the search bar
		searchBar.setOnMouseClicked(event -> { searchBar.requestFocus(); });



		//search on hitting enter
		searchBar.setOnAction( event -> { 
			String input = searchBar.getText().trim();

			if ( input.isEmpty() ) return;

			// dino easter egg
			if ( "dino".equals(input)) webEngine.load("https://wikispeedruns.com/play/quick_play?prompt_start=Computer&prompt_end=Dinosaur&lang=en");
			
			//finds if the input is a proper URL or not
			else if ( input.startsWith("http://") || input.startsWith("https://")) webEngine.load(input);
			
			// if not a proper URL does a google search instead
			else { 
				webEngine.load("https://google.com/search?q=" + input);
			}
			
		});

	
		// Search bar drawing steps
		HBox topBar =  new HBox(10);
		topBar.getChildren().addAll(searchBar);
		
		HBox.setHgrow(searchBar, javafx.scene.layout.Priority.ALWAYS);
		



		// display steps
		BorderPane root = new BorderPane( );
		
		// Top: currently only displays search bar
		root.setTop(topBar);

		// Center: currently only displays web page
		root.setCenter(webView);
	

		// default view window size
		Scene scene = new Scene( root, 1920, 1440 );

		// Tab name
		primaryStage.setTitle("Awesome Web Browser");
		
		primaryStage.setScene( scene );
		primaryStage.show( );


	}

}
