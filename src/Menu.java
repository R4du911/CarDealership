import Controller.Controller;
import Controller.SalespersonController;
import Interface.RegisterLogin;
import Model.Repo.InMemoInventory;
import Model.Salesperson;
import View.SalespersonView;

public class Menu implements RegisterLogin {

    private Controller controller;
    private InMemoInventory inventory;

    @Override
    public void login() {

    }

    @Override
    public void register(){

    }

    @Override
    public void logout() {

    }

    public void menu(){

        //if(login -> salesperson)
        this.inventory = new InMemoInventory();
        Salesperson salesperson = new Salesperson("Admin", "admin", "Costel", "Marculescu", 234.0, inventory);
        SalespersonView view = new SalespersonView();
        this.controller = new SalespersonController(salesperson,view);
    }
}
