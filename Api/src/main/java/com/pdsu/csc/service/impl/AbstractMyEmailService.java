package com.pdsu.csc.service.impl;

import com.pdsu.csc.bean.MyEmail;
import com.pdsu.csc.bean.MyEmailExample;
import com.pdsu.csc.exception.web.user.UserException;
import com.pdsu.csc.exception.web.user.email.NotFoundEmailException;
import com.pdsu.csc.service.MyEmailService;
import com.pdsu.csc.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author 半梦
 * @create 2021-02-20 21:06
 */
public abstract class AbstractMyEmailService
        implements MyEmailService, UserService {
    @Override
    public boolean countByEmail(@NonNull String email) {
        return false;
    }

    @Override
    public MyEmail selectMyEmailByEmail(@NonNull String email) throws NotFoundEmailException {
        return selectByExample(null);
    }

    @Override
    public abstract MyEmail selectMyEmailByUid(@NonNull Integer uid);

    @Override
    public boolean insert(@NonNull MyEmail myEmail) throws UserException {
        return false;
    }

    @Override
    public boolean isExistByUid(@NonNull Integer uid) {
        return false;
    }

    @Override
    public boolean deleteByExample(@NonNull MyEmailExample example) {
        return false;
    }

    @Override
    public boolean updateByExample(@NonNull MyEmail myEmail, @Nullable MyEmailExample example) {
        return false;
    }

    @Override
    @NonNull
    public List<MyEmail> selectListByExample(@Nullable MyEmailExample example) {
        return Collections.emptyList();
    }

    @Override
    public long countByExample(MyEmailExample example) {
        return 0;
    }

}
