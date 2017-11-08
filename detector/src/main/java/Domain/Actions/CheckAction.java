package Domain.Actions;


import Services.Interfaces.OutgoingMessageService;

public interface CheckAction extends Runnable{
    int getRideId();
    void setMessageService(OutgoingMessageService messageService);
}
