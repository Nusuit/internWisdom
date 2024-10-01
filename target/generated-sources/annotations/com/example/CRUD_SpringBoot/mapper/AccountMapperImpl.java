package com.example.CRUD_SpringBoot.mapper;

import com.example.CRUD_SpringBoot.dto.request.AccountRegisterRequest;
import com.example.CRUD_SpringBoot.entity.Account;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-30T17:27:56+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toAccount(AccountRegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        Account.AccountBuilder account = Account.builder();

        account.id( request.getId() );
        account.email( request.getEmail() );
        account.userName( request.getUserName() );
        account.passWord( request.getPassWord() );
        account.refreshToken( request.getRefreshToken() );
        account.role( request.getRole() );

        return account.build();
    }
}
