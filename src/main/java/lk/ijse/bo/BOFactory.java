package lk.ijse.bo;

import lk.ijse.bo.custom.impl.ProgrammeBOImpl;
import lk.ijse.bo.custom.impl.RegistrationBOImpl;
import lk.ijse.bo.custom.impl.StudentBOImpl;
import lk.ijse.bo.custom.impl.UserBOImpl;


public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        STUDENT, PROGRAMME, USER ,REGISTRATION

    }

    public SuperBO getBO(BOTypes boTypes) {

        switch (boTypes) {


            case PROGRAMME:
                return new ProgrammeBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case USER:
                return new UserBOImpl();
            case REGISTRATION:
                return new RegistrationBOImpl();
            default:
                return null;
        }
    }
}
