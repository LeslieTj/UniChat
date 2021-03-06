package gui;

import java.io.IOException;

import bubble.SpeechBox;
import bubble.SpeechDirection;
import client.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UpdateGroupChat {

	private ObservableList<Node> speechBubbles = FXCollections.observableArrayList();
	   
	@FXML
    private TextField searchBar;

    @FXML
    private ImageView ContactProfilePicture;

    @FXML
    private Label ContactName;

    @FXML
    private Label contactLastMessage;

    @FXML
    private ImageView userProfilePicture;

    @FXML
    private ImageView newConversation;

    @FXML
    private ImageView contactMenu;

    @FXML
    private Line lineSeperator;

    @FXML
    private ImageView currentProfilePicture;

    @FXML
    private Label currentConversationName;

    @FXML
    private ImageView conversationMenu;

    @FXML
    private TextField messageBox;

    @FXML
    private ImageView messageSendButton;

    @FXML 
    private ScrollPane conversationPane;
    
    @FXML 
    private VBox conversationBox;
    
    @FXML
    private ImageView backButton;

	 Client client;
	 FXMLLoader loader;
	 Stage stage;
	 int groupId;
	 String clientUsername;
	 
	 	public UpdateGroupChat(Client client, int groupId, String clientUsername) {
	 		this.client = client;
	 		this.groupId = groupId;
	 		this.clientUsername = clientUsername;
			loader = new FXMLLoader(getClass().getResource("../MainChat.fxml"));
			loader.setController(this);
			this.stage = new Stage();
			
	 	}
	 
	    @FXML
	    void contactOptions(MouseEvent event) {

	    }

	    @FXML
	    void conversationOptions(MouseEvent event) {

	    }

	    @FXML
	    void getUserInfo(MouseEvent event) {

	    }
	    
	    @FXML
	    void keyboardMessageSend() {	
	    	System.out.println("in gui keyboard message send method");
	    	String message = messageBox.getText();
	    	String user = client.user.getFirstname() + " " + client.user.getSurname();
	    	if(!message.isEmpty()) {
	    		sendMessage(message,user);
	    	}
	    	messageBox.clear();
	    	
	    } 
	    
	    @FXML
	    void searchContacts(ActionEvent event) {
	    }
	    
	    public void sendMessage(String message, String user){
	    	System.out.println("in gui keyboard sendMessage method");
	    	conversationPane.setContent(conversationBox);
	        conversationPane.prefWidthProperty().bind(conversationBox.prefWidthProperty().subtract(1));
	        conversationPane.setFitToWidth(true);
	        speechBubbles.add(new SpeechBox(message,user, SpeechDirection.RIGHT));
	        conversationBox.getChildren().add(new SpeechBox(message,user, SpeechDirection.RIGHT));
	        conversationPane.vvalueProperty().bind(conversationBox.heightProperty());
	        client.sendMessage(message,client.user.getUsername(), recipientUsername);
	        System.out.println("Sending from" + client.user.getFirstname() + "to " + recipientUsername);
	    }
	    
	    public void sendGuiMessageOnly(String message, String user) {
	    	conversationPane.setContent(conversationBox);
	        conversationPane.prefWidthProperty().bind(conversationBox.prefWidthProperty().subtract(1));
	        conversationPane.setFitToWidth(true);
	        speechBubbles.add(new SpeechBox(message,user, SpeechDirection.RIGHT));
	        conversationBox.getChildren().add(new SpeechBox(message,user, SpeechDirection.RIGHT));
	        conversationPane.vvalueProperty().bind(conversationBox.heightProperty());
	    }
	    
	    
		 public void setConversationName() {
			currentConversationName.setText(recipientActualName);	
			//currentConversationName.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		 }
	    
	    /**
	     * 
	     * @param message
	     * @param user user in the form -> user = client.user.getFirstname() + " " + client.user.getSurname();
	     */
	    public void recieveMessage(String message, String user) {
	    	speechBubbles.add(new SpeechBox(message,user, SpeechDirection.LEFT));
	    	conversationBox.getChildren().add(new SpeechBox(message,user, SpeechDirection.LEFT));
	    	conversationPane.vvalueProperty().bind(conversationBox.heightProperty());
	    }
	    
	    public void showSendMessage(String message, String user) {
	    	Platform.runLater(() -> {
	    	sendGuiMessageOnly(message,user);
	    	System.out.println(messageBox.getText());
            messageBox.setText("");
	    	});
	    }	
	    
	    public void showRecieveMessage(String message, String user) {
	    	Platform.runLater(() -> {
	    	conversationPane.setContent(conversationBox);
	        conversationPane.prefWidthProperty().bind(conversationBox.prefWidthProperty().subtract(1));
	        conversationPane.setFitToWidth(true);
	        recieveMessage(message,user);
	    	System.out.println(messageBox.getText());
            messageBox.setText("");
	    	});
	    }	
	    
//	    public void findGroupId(String recipientUsername, String clientUsername) {
//			client.findGroupId(clientUsername, recipientUsername);
//		}
	    
	    @FXML
		void backToAccountProfile2(MouseEvent e) throws IOException {
			UpdateAccountProfile2Controller update = new UpdateAccountProfile2Controller(client);
			new AccountProfile2Controller(update);
			client.updateAccountProfile2Controller = update;
			backButton.getScene().getWindow().hide();
			client.findFriends();
		}
	    
}
