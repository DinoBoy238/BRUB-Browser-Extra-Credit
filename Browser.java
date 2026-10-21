import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Browser extends Application {

	@Override
	public void start( Stage primaryStage ) {
		BorderPane root = new BorderPane( );
		Scene scene = new Scene( root, 1920, 1440 );
		

		WebView webView = new WebView( );
		WebEngine webEngine = webView.getEngine( );
		
		webEngine.load( "https://www.mtu.edu" );

		root.setCenter( webView );

		// do stuff
		
		primaryStage.setScene( scene );
		primaryStage.show( );


	}

}
