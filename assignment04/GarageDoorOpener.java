package assignment04;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class GarageDoorOpener {
	public static enum DoorState {UP, DOWN};

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java GarageDoorOpener <port number> <door description>");
			System.exit(1);
		}
		int portNumber = Integer.parseInt(args[0]); 
		var opener = new GarageDoorOpener(portNumber, args[1]);
		System.out.println("\nOpening socket and waiting");
		try (
				var serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();
				// outData is not used here but it could be used for sending
				// binary data--we used it last semester to send the count of the
				// the GunballMachine (an int)
				// var outData = new DataOutputStream(clientSocket.getOutputStream());
				
				// outText is used to send text 
				var outText = new PrintWriter(clientSocket.getOutputStream(), true);
				
				// outObj is used to send serialized objects
				var outObj = new ObjectOutputStream(clientSocket.getOutputStream());
				
				// in is used to read text from the client
				var in = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()))) {
			String inputLine; 
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Received " + inputLine);
				if(inputLine.toLowerCase().equals("getdoor")) {
					// TODO use outText.println
					// to send the opener's doorDescription
				} else if(inputLine.toLowerCase().equals("getdoorstate")) {
					// this is how you send a opener's serialized stateOfDoor
					outObj.writeObject(opener.getStateOfDoor());
				} else if(inputLine.toLowerCase().equals("getstate")) {
					// TODO send the opener's serialized currentState
				} else if(inputLine.toLowerCase().startsWith("set code")) {
					    String[] parts = inputLine.split("\\s+");
					    int[] newCode = new int[] {
					        Integer.parseInt(parts[2]),
					        Integer.parseInt(parts[3]),
					        Integer.parseInt(parts[4]),
					        Integer.parseInt(parts[5])
					    };
					    setCode(newCode);
				
				} else if (inputLine.length() == 1 
						&& '0' <= inputLine.charAt(0) && '9' >= inputLine.charAt(0)) {
					// we pass the keypad digit to opener 
					opener.enterKey(inputLine.charAt(0) - '0');
				} else if (inputLine.equals("-1")) {
					// we pass translate "-1" to the int -1 and pass it to the opener 
					opener.enterKey(-1);
				} else {
					outText.println("Invalid Request");
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
					+ portNumber + " or listening for a connection");
			System.out.println(e.getClass());
			System.out.println(e.getMessage());
		}
	}

	private String doorDescription;
	private int[] code = new int[4];
	private boolean valid = false;
	private State currentState = State.WAITING;
	private DoorState stateOfDoor = DoorState.DOWN;

	public GarageDoorOpener(int portNumber, String doorDescriptionIn) {
		doorDescription = doorDescriptionIn;
		setCode(1,2,3,4);
	}
	public void doorMotorOn() {
		if (stateOfDoor == DoorState.DOWN) {
			System.out.println("Opening Door " + doorDescription);
			stateOfDoor = DoorState.UP;
		} else {
			System.out.println("Closing Door " + doorDescription);
			stateOfDoor = DoorState.DOWN;			
		}
	}
	public void enterKey(int k) {
		if(k != -1) {
			// TODO get the new value for currentState 
			// by calling the enter_key of currentState
			currentState = currentState.enter_key(k, this);
		} else {
			// TODO get the new value for currentState
			// by calling the press_open of currentState
			currentState = currentState.press_open(this);
		}
		// the following output is just for tracking the state changes
		System.out.println(currentState + ", valid = " 
				+ valid + ", code = " + Arrays.toString(code));
	}
	public void setCode(int... codeIn) {
		code = codeIn;
		// the following output is just for tracking the code setting
		System.out.println(Arrays.toString(code));
	}
	public State getCurrentState() {
		return currentState;
	}
	public String getDoorDescription() {
		return doorDescription;
	}
	public DoorState getStateOfDoor() {
		return stateOfDoor;
	}
	public int getCode(int i) {
		return code[i];
	}
	public void setValid(boolean validIn) {
		valid = validIn;		
	}
	// NOTE that getter methods for booleans should be called
	// is... instead of get...
	public boolean isValid() { 
		return valid;
	}
}
