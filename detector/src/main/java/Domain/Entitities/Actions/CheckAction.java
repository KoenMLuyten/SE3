package Domain.Entitities.Actions;


import Services.Interfaces.OutgoingMessageService;


/*
* Interface that represents a certain action needed after a message has been checked;
*
* */
public interface CheckAction extends Runnable{
    int getRideId();
    void setMessageService(OutgoingMessageService messageService);
}
