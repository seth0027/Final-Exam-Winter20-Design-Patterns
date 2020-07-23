package lissajous;

import java.util.function.Consumer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lissajous.scene.AbstractScene;
import lissajous.scene.ShiftingScene;
import lissajous.scene.TracingScene;
import lissajous.animator.AbstractAnimator;
import lissajous.animator.Animator;

/**
 * 
 * 
 * @author Shahriar (Shawn) Emami
 * @version April 16, 2020
 */
public class Lissajous extends Application{

	/**
	 * size of the scene
	 */
	private double width = 700, height = 650;
	/**
	 * title of application
	 */
	private String title = "Lissajous Curves";
	/**
	 * background color of application
	 */
	private Color background = Color.LIGHTPINK;
	/**
	 * {@link BorderPane} is a layout manager that manages all nodes in 5 areas as below:
	 * 
	 * <pre>
	 * -----------------------
	 * |        top          |
	 * -----------------------
	 * |    |          |     |
	 * |left|  center  |right|
	 * |    |          |     |
	 * -----------------------
	 * |       bottom        |
	 * -----------------------
	 * </pre>
	 * 
	 * this object is passed to {@link Scene} object in {@link Chatter#start(Stage)} method.
	 */
	private BorderPane root;

	private Alert alert;
	private AbstractScene board;
	private AbstractAnimator animator;
	private Canvas canvas;

	/**
	 * this method is called at the very beginning of the JavaFX application and can be used to initialize
	 * all components in the application. however, {@link Scene} and {@link Stage} must not be created in
	 * this method. this method does not run JavaFX thread, it runs on JavaFX-Launcher thread.
	 */
	@Override
	public void init() throws Exception{
		canvas = new Canvas( width, height);

		board = new ShiftingScene();
		animator = new Animator();
		animator.setCanvas( canvas);
		animator.setMapScene( board);

		Region statusBar = createStatusBar();
		Region optionsBar = createOptionsBar();

		root = new BorderPane();
		root.setTop( optionsBar);
		root.setCenter( canvas);
		root.setBottom( statusBar);
	}

	/**
	 * <p>
	 * this method is called when JavaFX application is started and it is running on JavaFX thread.
	 * this method must at least create {@link Scene} and finish customizing {@link Stage}. these two
	 * objects must be on JavaFX thread when created.
	 * </p>
	 * <p>
	 * {@link Stage} represents the frame of your application, such as minimize, maximize and close buttons.<br>
	 * {@link Scene} represents the holder of all your JavaFX {@link Node}s.<br>
	 * {@link Node} is the super class of every javaFX class.
	 * </p>
	 * @param primaryStage - primary stage of your application that will be rendered
	 */
	@Override
	public void start( Stage primaryStage) throws Exception{
		// Alert is initialized in start because it must be created on JavaFX thread
		alert = new Alert( AlertType.INFORMATION);
		// scene holds all JavaFX components that need to be displayed in Stage
		Scene scene = new Scene( root, background);
		primaryStage.setScene( scene);
		primaryStage.setTitle( title);
		primaryStage.setResizable( false);
		// when escape key is pressed close the application
		primaryStage.addEventHandler( KeyEvent.KEY_RELEASED, ( KeyEvent event) -> {
			if( KeyCode.ESCAPE == event.getCode()){
				primaryStage.hide();
			}
		});
		// display the JavaFX application
		primaryStage.show();
		board.createScene();
		animator.start();
	}

	/**
	 * this method is called at the very end when the application is about to exit.
	 * this method is used to stop or release any resources used during the application.
	 */
	@Override
	public void stop() throws Exception{
		animator.stop();
	}

	/**
	 * create a {@link MenuBar} that represent the menu bar at the top of the application.
	 * @return customized {@link MenuBar} as its super class {@link Region}.
	 */
	private Region createOptionsBar(){
		MenuItem start = new MenuItem( "Start");
		start.setOnAction( ( ActionEvent e) -> animator.start());

		SeparatorMenuItem sep = new SeparatorMenuItem();

		MenuItem exit = new MenuItem( "Exit");
		exit.setOnAction( ( ActionEvent e) -> Platform.exit());

		CheckMenuItem fps = createCheckMenuItem( "FPS", true, board.drawFPSProperty());

		Consumer< AbstractScene> createScene = s -> {
			board = s;
			board.createScene();
			board.drawFPSProperty().bind( fps.selectedProperty());
			animator.stop();
			animator.setMapScene( board);
			animator.start();
		};

		RadioMenuItem tracing = new RadioMenuItem( "Tracing");
		tracing.setOnAction( e -> createScene.accept( new TracingScene()));

		RadioMenuItem shifting = new RadioMenuItem( "Shifting");
		shifting.setSelected( true);
		shifting.setOnAction( e -> createScene.accept( new ShiftingScene()));

		ToggleGroup toggleGroup = new ToggleGroup();
		tracing.setToggleGroup( toggleGroup);
		shifting.setToggleGroup( toggleGroup);

		MenuItem info = new MenuItem( "Info");
		info.setOnAction(
				e -> {
					alert.setTitle( "Information");
					alert.setHeaderText( "Developer");
					alert.setContentText( "Shawn E.\nemamis@algonquincollege.com");
					alert.show();
				});

		Menu file = new Menu( "File");
		file.getItems().addAll( start, sep, exit);

		Menu option = new Menu( "Options");
		option.getItems().addAll( fps);

		Menu scene = new Menu( "Scenes");
		scene.getItems().addAll( tracing, shifting);

		Menu help = new Menu( "Help");
		help.getItems().addAll( info);

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll( file, option, scene, help);
		return menuBar;
	}

	/**
	 * create a {@link ToolBar} that will represent the status bar of the application.
	 * @return customized {@link ToolBar} as its super class {@link Region}.
	 */
	private Region createStatusBar(){
		Label mouseCoordLabel = new Label( "(0,0)");
		Label dragCoordLabel = new Label( "(0,0)");

		canvas.addEventHandler( MouseEvent.MOUSE_MOVED,
				e -> mouseCoordLabel.setText( "(" + e.getX() + "," + e.getY() + ")"));
		canvas.addEventHandler( MouseEvent.MOUSE_DRAGGED,
				e -> dragCoordLabel.setText( "(" + e.getX() + "," + e.getY() + ")"));

		return new ToolBar(
				new Label( "Mouse: "),
				mouseCoordLabel,
				new Label( "Drag: "),
				dragCoordLabel);
	}

	/**
	 * <p>
	 * create a {@link CheckMenuItem} with given text, initial state and {@link BooleanProperty} binding.
	 * {@link BooleanProperty} is a special class that can be binded to another {@link BooleanProperty}.
	 * this means every time bindee changes the other {@link BooleanProperty} changes as well. for example:
	 * </p>
	 * 
	 * <pre>
	 * BooleanProperty b1 = new SimpleBooleanProperty( false);
	 * BooleanProperty b2 = new SimpleBooleanProperty();
	 * b1.bind( b2);
	 * b2.set( true);
	 * System.out.println( b1.get()); // prints true
	 * IntegerProperty i1 = new SimpleIntegerProperty( 1);
	 * IntegerProperty i2 = new SimpleIntegerProperty();
	 * i1.bind( i2);
	 * i2.set( 100);
	 * System.out.println( i1.get()); // prints 100
	 * </pre>
	 * 
	 * <p>
	 * if p2 changes p1 changes as well. this relationship is NOT bidirectional.
	 * </p>
	 * @param text - String to be displayed
	 * @param isSelected - initial state, true id selected
	 * @param binding - {@link BooleanProperty} that will be notified if state of this {@link CheckMenuItem} is changed
	 * @return customized {@link CheckMenuItem}
	 */
	public CheckMenuItem createCheckMenuItem( String text, boolean isSelected, BooleanProperty binding){
		CheckMenuItem check = new CheckMenuItem( text);
		binding.bind( check.selectedProperty());
		check.setSelected( isSelected);
		return check;
	}

	/**
	 * main starting point of the application
	 * @param args - arguments provided through command line, if any
	 */
	public static void main( String[] args){
		// launch( args); method starts the javaFX application.
		// some IDEs are capable of starting JavaFX without this method.
		launch( args);
	}
}
