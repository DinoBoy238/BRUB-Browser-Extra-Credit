import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

/**
 * Team BRUB
 *
 * Sam Bacon, Spencer Brandon, Lucas Rabb, Ayden Unrath
 */

public class Browser extends Application {

	@Override
	public void start( Stage primaryStage ) {
		

		WebView webView = new WebView( );
		WebEngine webEngine = webView.getEngine( );

		//default page
		webEngine.load( "https://www.mtu.edu" );
		
		//Status Bar and hover on item
		
		Label statusBar = new Label("");
		
		webEngine.getLoadWorker().stateProperty().addListener((observable,oldValue,NewValue) -> {
		Document doc = webEngine.getDocument();
		EventTarget documentTarget = (EventTarget) doc;
		//Hover Event steps
		documentTarget.addEventListener("mouseover",new EventListener(){
			@Override
			public void handleEvent(Event event) {
				org.w3c.dom.Node hoveredText = (org.w3c.dom.Node) event.getTarget();
				
				Element link = getTagAOrNull(hoveredText);
				
				if (link != null) {
					String linkText = link.getAttribute("href");
					statusBar.setText(linkText);
				}
			}
		},false);

		//Hover off item

		documentTarget.addEventListener("mouseout",new EventListener(){
			@Override
			public void handleEvent(Event event) {
				org.w3c.dom.Node unHoveredLink = (org.w3c.dom.Node) event.getTarget();
				
				Element offLink = getTagAOrNull(unHoveredLink);

				if (offLink != null){
					statusBar.setText("");
				}			
			}
		}, false);
		
		});
		
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
		
		//Status bar drawing
		HBox bottomBar = new HBox(10);
		bottomBar.getChildren().add(statusBar);

		// display steps
		BorderPane root = new BorderPane( );
		
		// Top: currently only displays search bar
		root.setTop(topBar);

		// Center: currently only displays web page
		root.setCenter(webView);
		
		//Bottom: Displays hovered link
		root.setBottom(bottomBar);	

		// default view window size
		Scene scene = new Scene( root);

		// Tab name
		primaryStage.setTitle("Awesome Web Browser");
		
		primaryStage.setScene( scene );
		primaryStage.setMaximized(true);
		primaryStage.show( );


	}

	private Element getTagAOrNull(org.w3c.dom.Node node){
			while (node != null){
				if (node instanceof Element && "a".equalsIgnoreCase(((Element) node).getTagName())){
					return (Element) node;
				}
				node = node.getParentNode();
			}
		
		return null;
	}
}
