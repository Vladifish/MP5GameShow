/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *  Creating this so that we have something common to throw
 * @author Vlad
 */
public class SessionException extends Exception {
    public SessionException(String s)
    {
        super(s);
    }
    public SessionException()
    {
        super("ERROR: Session Does Not Exist");
    }
}
