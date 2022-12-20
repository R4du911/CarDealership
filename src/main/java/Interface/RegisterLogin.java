package Interface;

import Errors.CustomIllegalArgument;

public interface RegisterLogin {
    void login(int savingOption) throws CustomIllegalArgument;
    void register(int savingOption) throws CustomIllegalArgument;
}
