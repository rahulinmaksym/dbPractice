package org.sillmarry.userDataBase;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String name;
    private String email;
    private int age;
    private int fragCount;
}
