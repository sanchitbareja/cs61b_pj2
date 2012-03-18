package player;

public class AgainstRulesException extends Exception {

    /**
     *  Creates an exception object for nonexistent account "badAcctNumber".
     **/
    public AgainstRulesException() {
	super();
    } 
    
    public AgainstRulesException(String e){
	super(e);
    }    
}