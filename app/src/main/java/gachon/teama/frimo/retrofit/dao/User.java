package gachon.teama.frimo.retrofit.dao;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Serializable {
    private Long userPk;
    private String userId;
    private String userNN;

}
