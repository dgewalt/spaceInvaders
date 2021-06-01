package dev.thees.connect4;

import java.io.File;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Spielfeld extends Application {

	Controller controller = new Controller();

	Circle[] kreise = new Circle[42];

	private static int ZEILEN = 6;
	private static int SPALTEN = 7;
	private static int RADIUS = 50;
	private static int ABSTAND = 8;
	private static int SPIELFELDLAENGE = (2 * RADIUS + ABSTAND) * SPALTEN + ABSTAND + 10;
	private static int SPIELFELDBREITE = (2 * RADIUS + ABSTAND) * ZEILEN + ABSTAND + 10;

	private int Zug = 0;
	private boolean play = true;

	// Layout
	Stage primaryStage;
	Pane spielLayout;
	HBox homeLayout;
	VBox color;
	VBox difficulty;
	HBox menu;
	VBox startLayout;
	Pane chipLayout;

	Scene startScene;
	Scene spielScene;
	Scene homeScene;

	Label winner;
	Label wahl;
	Label schwierigkeitsgrad;
	Label farbe;
	Label vierGewinnt;
	Label runde;

	Button einfach;
	Button mittel;
	Button schwer;
	Button rot;
	Button gelb;
	Button start;
	Button importieren;
	Button exportieren;
	Button restart;

	Button einSpieler;
	Button zweiSpieler;

	Rectangle[] vorschau;
	Shape spielbrett;

	TranslateTransition fallen;

	FileChooser choose;

	@Override
	public void start(Stage Stage) {

		primaryStage = Stage;
		setzteLayout();
		erzeugeEvents();
		try {
			primaryStage.setScene(startScene);
			primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void setzteLayout() {

		BackgroundImage hintergrund = new BackgroundImage(new Image("dev/thees/connect4/desktop1.jpg", 0, 0, true, true), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

		spielLayout = new Pane();
		startLayout = new VBox();
		chipLayout = new Pane();

		color = new VBox();
		color.setAlignment(Pos.TOP_CENTER);
		color.setSpacing(10);

		difficulty = new VBox();
		difficulty.setAlignment(Pos.TOP_CENTER);
		difficulty.setSpacing(10);
		difficulty.setTranslateY(-4);

		menu = new HBox(color, difficulty);
		menu.setAlignment(Pos.BOTTOM_CENTER);
		menu.setSpacing(100);

		homeLayout = new HBox(menu);
		homeLayout.setSpacing(0);
		menu.setTranslateX(190);
		menu.setTranslateY(300);

		startLayout.setBackground(new Background(hintergrund));
		homeLayout.setBackground(new Background(hintergrund));
		homeLayout.resize(800, 800);

		schwierigkeitsgrad = new Label("Schwierigkeitsgrad:");
		schwierigkeitsgrad.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #000000;");
		schwierigkeitsgrad.setMinSize(300, 50);

		wahl = new Label("Bitte wählen:");
		wahl.setStyle(" -fx-font-size: 4em; -fx-font-weight: bold; -fx-text-fill: #000000;");
		wahl.setTranslateX(-280);
		wahl.setTranslateY(100);
		wahl.setMinSize(400, 100);
		farbe = new Label("Farbe:");
		farbe.setMinSize(80, 40);
		farbe.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #000000;");
		einfach = new Button("einfach");
		einfach.setMinSize(120, 50);
		einfach.setStyle(
				" -fx-font-size: 2em; -fx-background-color: #848484;-fx-text-fill: #FFFFFF;-fx-border-insets: 2; -fx-border-width: 2; -fx-border-color: #000000");
		mittel = new Button("mittel");
		mittel.setMinSize(120, 50);
		mittel.setStyle(" -fx-font-size: 2em; -fx-background-color: #6E6E6E;-fx-text-fill: #FFFFFF;");
		schwer = new Button("schwer");
		schwer.setMinSize(120, 50);
		schwer.setStyle(" -fx-font-size: 2em; -fx-background-color: #585858; -fx-text-fill: #FFFFFF;");
		rot = new Button("Rot");
		rot.setMinSize(100, 50);
		rot.setStyle(
				" -fx-font-size: 2em; -fx-background-color: #FF0000 ;-fx-text-fill: #000000; -fx-border-insets: 2; -fx-border-width: 2; -fx-border-color: #000000");
		gelb = new Button("Gelb");
		gelb.setMinSize(100, 50);
		gelb.setStyle(" -fx-font-size: 2em; -fx-background-color: #FFFF00;-fx-text-fill: #000000;");
		start = new Button("Start");
		start.setMinSize(300, 50);
		start.setStyle(" -fx-font-size: 2em; -fx-background-color: #04B404;-fx-text-fill: #000000;");
		start.setTranslateX(-650);
		start.setTranslateY(650);

		vierGewinnt = new Label("Vier Gewinnt");
		vierGewinnt.setStyle(" -fx-font-size: 5em; -fx-font-weight: bolder; -fx-text-fill: #FF0000;");
		einSpieler = new Button("1-Spieler");
		einSpieler.setStyle(" -fx-font-size: 2em; -fx-background-color: #D8D8D8 ;-fx-text-fill: #000000;");
		zweiSpieler = new Button("2-Spieler");
		zweiSpieler.setStyle(" -fx-font-size: 2em; -fx-background-color: #D8D8D8; -fx-text-fill: #000000;");

		startLayout.getChildren().addAll(vierGewinnt, einSpieler, zweiSpieler);
		startLayout.setSpacing(40);
		startLayout.setAlignment(Pos.CENTER);
		startLayout.resize(800, 800);

		homeLayout.getChildren().addAll(wahl, start);

		for (int i = 0; i < 42; i++) {
			kreise[i] = new Circle(RADIUS);
			kreise[i].setFill(Color.TRANSPARENT);
			chipLayout.getChildren().add(kreise[i]);
		}

		spielLayout.getChildren().add(chipLayout);

		spielbrett = new Rectangle(SPIELFELDLAENGE, SPIELFELDBREITE);

		for (int i = 0; i < ZEILEN; i++) {
			for (int j = 0; j < SPALTEN; j++) {
				Circle loch = new Circle(RADIUS);
				loch.setCenterX(RADIUS);
				loch.setCenterY(RADIUS);
				loch.setTranslateX(j * (RADIUS * 2 + ABSTAND) + 10);
				loch.setTranslateY(i * (RADIUS * 2 + ABSTAND) + 10);
				spielbrett = Shape.subtract(spielbrett, loch);
			}

		}

		spielbrett.setTranslateX(12);
		spielbrett.setTranslateY(12);
		spielbrett.setFill(Color.BLUE);
		spielLayout.getChildren().add(spielbrett);

		winner = new Label();
		winner.setTranslateX(265);
		winner.setTranslateY(730);
		winner.setVisible(false);
		runde = new Label("Runde: " + (Zug + 1));
		runde.setTranslateX(342);
		runde.setTranslateY(680);
		runde.setStyle(" -fx-font-size: 2em; -fx-text-fill: #000000;");

		importieren = new Button("Spielstand Laden");
		exportieren = new Button("Spielstand Speichern");
		importieren.setTranslateX(13);
		importieren.setTranslateY(700);
		importieren.setStyle(" -fx-font-size: 1em; -fx-background-color: #000000;-fx-text-fill: #FFFFFF;");
		exportieren.setTranslateX(13);
		exportieren.setTranslateY(740);
		exportieren.setStyle(" -fx-font-size: 1em; -fx-background-color: #000000;-fx-text-fill: #FFFFFF;");

		restart = new Button("Neustart");
		restart.setTranslateX(709);
		restart.setTranslateY(700);
		restart.setStyle(" -fx-font-size: 1em; -fx-background-color: #000000;-fx-text-fill: #FFFFFF;");

		spielLayout.getChildren().addAll(winner, runde, importieren, exportieren, restart);

		vorschau = new Rectangle[SPALTEN];
		for (int i = 0; i < SPALTEN; i++) {
			vorschau[i] = new Rectangle(2 * RADIUS + ABSTAND, SPIELFELDBREITE - 8);
			vorschau[i].setTranslateX(i * (RADIUS * 2 + ABSTAND) + 16);
			vorschau[i].setTranslateY(16);
			vorschau[i].setFill(Color.TRANSPARENT);
			spielLayout.getChildren().add(vorschau[i]);
		}

		color.getChildren().addAll(farbe, gelb, rot);
		difficulty.getChildren().addAll(schwierigkeitsgrad, einfach, mittel, schwer);

		startScene = new Scene(startLayout, 800, 800);
		spielScene = new Scene(spielLayout, 800, 800);
		homeScene = new Scene(homeLayout, 800, 800);

		choose = new FileChooser();

	}

	public void erzeugeEvents() {

		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				primaryStage.setScene(spielScene);
				if (!controller.getMehrspieler() && controller.getFarbeComputer()) {
					setzteStein(controller.getSpalteAutomat(), controller.getZeileAutomat(), controller.getFarbe(), true);
				}
			}
		});

		einSpieler.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				primaryStage.setScene(homeScene);
				controller.setzeMehrspieler(false);
			}
		});

		zweiSpieler.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				primaryStage.setScene(spielScene);
				controller.setzeMehrspieler(true);
			}
		});

		gelb.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				controller.setFarbeComputer(true);
				rot.setStyle(" -fx-font-size: 2em; -fx-background-color: #FF0000 ;-fx-text-fill: #000000;");
				gelb.setStyle(
						" -fx-font-size: 2em; -fx-background-color: #FFFF00;-fx-text-fill: #000000;-fx-border-insets: 2; -fx-border-width: 2; -fx-border-color: #000000");
			}
		});

		rot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				controller.setFarbeComputer(false);
				gelb.setStyle(" -fx-font-size: 2em; -fx-background-color: #FFFF00;-fx-text-fill: #000000;");
				rot.setStyle(
						" -fx-font-size: 2em; -fx-background-color: #FF0000 ;-fx-text-fill: #000000; -fx-border-insets: 2; -fx-border-width: 2; -fx-border-color: #000000");
			}
		});

		einfach.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				controller.setTiefe(2);
				einfach.setStyle(
						" -fx-font-size: 2em; -fx-background-color: #848484;-fx-text-fill: #FFFFFF;-fx-border-insets: 2; -fx-border-width: 2; -fx-border-color: #000000");
				mittel.setStyle(" -fx-font-size: 2em; -fx-background-color: #6E6E6E;-fx-text-fill: #FFFFFF;");
				schwer.setStyle(" -fx-font-size: 2em; -fx-background-color: #585858; -fx-text-fill: #FFFFFF;");
			}
		});

		mittel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				controller.setTiefe(4);
				einfach.setStyle(" -fx-font-size: 2em; -fx-background-color: #848484;-fx-text-fill: #FFFFFF;");
				mittel.setStyle(
						" -fx-font-size: 2em; -fx-background-color: #6E6E6E;-fx-text-fill: #FFFFFF; -fx-border-insets: 2; -fx-border-width: 2; -fx-border-color: #000000");
				schwer.setStyle(" -fx-font-size: 2em; -fx-background-color: #585858; -fx-text-fill: #FFFFFF;");
			}
		});

		schwer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				controller.setTiefe(6);
				einfach.setStyle(" -fx-font-size: 2em; -fx-background-color: #848484;-fx-text-fill: #FFFFFF;");
				mittel.setStyle(" -fx-font-size: 2em; -fx-background-color: #6E6E6E;-fx-text-fill: #FFFFFF;");
				schwer.setStyle(
						" -fx-font-size: 2em; -fx-background-color: #585858; -fx-text-fill: #FFFFFF; -fx-border-insets: 2; -fx-border-width: 2; -fx-border-color: #000000");
			}
		});

		importieren.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				File wahl = choose.showOpenDialog(primaryStage);
				if (wahl != null) {
					Zug = 0;
					for (int i = 0; i < 42; i++) {
						kreise[i].setFill(Color.TRANSPARENT);
						kreise[i].setTranslateY(0);
					}
					ZeichneFeld(controller.getVorgabe(wahl));
				}
			}
		});

		exportieren.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				File wahl = choose.showSaveDialog(primaryStage);
				if (wahl != null) {
					controller.export(wahl);
				}
			}
		});

		restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				controller.reset();
				controller.resetFarbe();
				Zug = 0;
				play = true;
				for (int i = 0; i < 42; i++) {
					kreise[i].setFill(Color.TRANSPARENT);
					kreise[i].setTranslateY(0);
				}
				winner.setVisible(false);
				winner.setTranslateX(260);
				if (!controller.getMehrspieler() && controller.getFarbeComputer()) {
					setzteStein(controller.getSpalteAutomat(), controller.getZeileAutomat(), controller.getFarbe(), true);
				}
			}
		});

		vorschau[0].setOnMouseEntered((event) -> {
			if (!controller.istSpalteVoll(0)) {
				vorschau[0].setFill(Color.rgb(255, 250, 240, 0.5));
			}
		});
		vorschau[1].setOnMouseEntered((event) -> {
			if (!controller.istSpalteVoll(1)) {
				vorschau[1].setFill(Color.rgb(255, 250, 240, 0.5));
			}
		});
		vorschau[2].setOnMouseEntered((event) -> {
			if (!controller.istSpalteVoll(2)) {
				vorschau[2].setFill(Color.rgb(255, 250, 240, 0.5));
			}
		});
		vorschau[3].setOnMouseEntered((event) -> {
			if (!controller.istSpalteVoll(3)) {
				vorschau[3].setFill(Color.rgb(255, 250, 240, 0.5));
			}
		});
		vorschau[4].setOnMouseEntered((event) -> {
			if (!controller.istSpalteVoll(4)) {
				vorschau[4].setFill(Color.rgb(255, 250, 240, 0.5));
			}
		});
		vorschau[5].setOnMouseEntered((event) -> {
			if (!controller.istSpalteVoll(5)) {
				vorschau[5].setFill(Color.rgb(255, 250, 240, 0.5));
			}
		});
		vorschau[6].setOnMouseEntered((event) -> {
			if (!controller.istSpalteVoll(6)) {
				vorschau[6].setFill(Color.rgb(255, 250, 240, 0.5));
			}
		});

		vorschau[0].setOnMouseExited((event) -> {
			vorschau[0].setFill(Color.TRANSPARENT);
		});
		vorschau[1].setOnMouseExited((event) -> {
			vorschau[1].setFill(Color.TRANSPARENT);
		});
		vorschau[2].setOnMouseExited((event) -> {
			vorschau[2].setFill(Color.TRANSPARENT);
		});
		vorschau[3].setOnMouseExited((event) -> {
			vorschau[3].setFill(Color.TRANSPARENT);
		});
		vorschau[4].setOnMouseExited((event) -> {
			vorschau[4].setFill(Color.TRANSPARENT);
		});
		vorschau[5].setOnMouseExited((event) -> {
			vorschau[5].setFill(Color.TRANSPARENT);
		});
		vorschau[6].setOnMouseExited((event) -> {
			vorschau[6].setFill(Color.TRANSPARENT);
		});

		vorschau[0].setOnMouseClicked((event) -> {
			if (!controller.istSpalteVoll(0)) {
				setzteStein(0, controller.getZeile(0), controller.getFarbe(), true);
				if (!controller.getMehrspieler()) {
					setzteStein(controller.getSpalteAutomat(), controller.getZeileAutomat(), controller.getFarbe(), true);
				}
			}
		});

		vorschau[1].setOnMouseClicked((event) -> {
			if (!controller.istSpalteVoll(1)) {
				setzteStein(1, controller.getZeile(1), controller.getFarbe(), true);
				if (!controller.getMehrspieler()) {
					setzteStein(controller.getSpalteAutomat(), controller.getZeileAutomat(), controller.getFarbe(), true);
				}
			}
		});

		vorschau[2].setOnMouseClicked((event) -> {
			if (!controller.istSpalteVoll(2)) {
				setzteStein(2, controller.getZeile(2), controller.getFarbe(), true);
				if (!controller.getMehrspieler()) {
					setzteStein(controller.getSpalteAutomat(), controller.getZeileAutomat(), controller.getFarbe(), true);
				}
			}
		});
		vorschau[3].setOnMouseClicked((event) -> {
			if (!controller.istSpalteVoll(3)) {
				setzteStein(3, controller.getZeile(3), controller.getFarbe(), true);
				if (!controller.getMehrspieler()) {
					setzteStein(controller.getSpalteAutomat(), controller.getZeileAutomat(), controller.getFarbe(), true);
				}
			}
		});
		vorschau[4].setOnMouseClicked((event) -> {
			if (!controller.istSpalteVoll(4)) {
				setzteStein(4, controller.getZeile(4), controller.getFarbe(), true);
				if (!controller.getMehrspieler()) {
					setzteStein(controller.getSpalteAutomat(), controller.getZeileAutomat(), controller.getFarbe(), true);
				}
			}
		});
		vorschau[5].setOnMouseClicked((event) -> {
			if (!controller.istSpalteVoll(5)) {
				setzteStein(5, controller.getZeile(5), controller.getFarbe(), true);
				if (!controller.getMehrspieler()) {
					setzteStein(controller.getSpalteAutomat(), controller.getZeileAutomat(), controller.getFarbe(), true);
				}
			}
		});
		vorschau[6].setOnMouseClicked((event) -> {
			if (!controller.istSpalteVoll(6)) {
				setzteStein(6, controller.getZeile(6), controller.getFarbe(), true);
				if (!controller.getMehrspieler()) {
					setzteStein(controller.getSpalteAutomat(), controller.getZeileAutomat(), controller.getFarbe(), true);
				}
			}
		});
	}

	public void ZeichneFeld(int vorgabe[]) {
		int[] spalten = new int[vorgabe.length];
		spalten = vorgabe;
		controller.resetFarbe();
		for (int i = 0; i < vorgabe.length; i++) {
			if (spalten[i] >= 0 && spalten[i] < 7) {
				setzteStein(spalten[i], controller.getZeile(spalten[i]), controller.getFarbe(), false);
			}
		}
		if (vorgabe.length % 2 == 0 && controller.getFarbeComputer()) {
			setzteStein(controller.getSpalteAutomat(), controller.getZeileAutomat(), controller.getFarbe(), true);
		}
		if (vorgabe.length % 2 == 1 && !controller.getFarbeComputer()) {
			setzteStein(controller.getSpalteAutomat(), controller.getZeileAutomat(), controller.getFarbe(), true);
		}
	}

	public void setzteStein(int spalte, int zeile, boolean rot, boolean animation) {
		if (zeile >= 0 && play) {
			if (rot) {
				kreise[Zug].setFill(Color.RED);
			} else {
				kreise[Zug].setFill(Color.YELLOW);
			}
			kreise[Zug].setCenterX(RADIUS);
			kreise[Zug].setCenterY(RADIUS);
			kreise[Zug].setTranslateX(spalte * (RADIUS * 2 + ABSTAND) + 22);
			if (animation) {
				fallen = new TranslateTransition(Duration.seconds(0.5), kreise[Zug]);
				fallen.setToY(SPIELFELDBREITE + 4 - ((zeile + 1) * (RADIUS * 2 + ABSTAND)));
				if (rot == controller.getFarbeComputer() && !controller.getMehrspieler()) {
					kreise[Zug].setTranslateY(22);
					fallen.setDelay(Duration.seconds(0.6));
				}
				fallen.play();
			} else {
				kreise[Zug].setTranslateY(SPIELFELDBREITE + 4 - ((zeile + 1) * (RADIUS * 2 + ABSTAND)));
			}
			runde.setText("Runde: " + ((int) (Zug / 2) + 1));
			Zug++;
			if (controller.checkWin()) {
				String spieler;
				play = false;
				winner.setVisible(true);
				if (rot) {
					spieler = "Rot";
					winner.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #FF0000;");
				} else {
					spieler = "Gelb";
					winner.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #D7DF01;");
				}
				winner.setText(spieler + " hat gewonnen");
			}
			if (Zug == 42) {
				winner.setTranslateX(227);
				winner.setStyle(" -fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #000000;");
				winner.setText("Das Spiel endet unentschieden");
				winner.setVisible(true);
			}
		}
	}

	public static void erzeugeSpielfeld(String[] args) {
		launch(args);
	}

}
