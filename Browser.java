import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.web.WebHistory;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

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

		//Help Button
		Image image = new Image("questionmark.png");
		Image helpImage = new Image("HelpText.png");
		//popup graphics
		ImageView popUpImage = new ImageView(helpImage);
		popUpImage.setFitWidth(800);
		popUpImage.setPreserveRatio(true);
		StackPane popupstuff = new StackPane(popUpImage);
		Popup popup = new Popup();
		popup.setAutoHide(true);
		popup.getContent().add(popupstuff);

		//button graphics and behavior
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(35);
		imageView.setFitHeight(35);
		imageView.setPreserveRatio(true);
		Button help = new Button("",imageView);
		help.setOnAction(event -> {
			if(!popup.isShowing()){
			popup.show(primaryStage,help.getScene().getWindow().getX() + help.getLayoutX(),
				help.getScene().getWindow().getY() + help.getLayoutY() + help.getHeight() + 30);
			}
			else{
			popup.hide();
			}	
		});
					

		//search on hitting enter
		searchBar.setOnAction( event -> { 
			String input = searchBar.getText().trim();

			if ( input.isEmpty() ) return;

			// dino easter egg
			if ( "dino".equals(input)) webEngine.load("https://wikispeedruns.com/play/quick_play?prompt_start=Computer&prompt_end=Dinosaur&lang=en");
			
			//finds if the input is a proper URL or not
			else if ( input.startsWith("http://") || input.startsWith("https://")){
			       	webEngine.load(input);

			}
			// if not a proper URL does a google search instead
			else { 
				webEngine.load("https://google.com/search?q=" + input);
			}
			
		});

		//Backwards and the forwards buttons creation and logic
		WebHistory history = webEngine.getHistory();

		Button backButton = new Button("<--");
		Button forwardButton = new Button("-->");

		backButton.setOnAction(e -> history.go(-1));
		forwardButton.setOnAction(e -> history.go(1));
			
		// Search bar drawing steps
		HBox topBar =  new HBox(10);

		topBar.setPadding(new Insets(8, 12, 8, 12));
		topBar.setAlignment(Pos.CENTER_LEFT);
		topBar.getChildren().addAll(backButton, forwardButton, searchBar, help);
		HBox.setHgrow(searchBar, javafx.scene.layout.Priority.ALWAYS);
		HBox.setHgrow(help, javafx.scene.layout.Priority.NEVER);

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
		primaryStage.titleProperty().bind(webEngine.titleProperty());
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
