package com.pdsu.csc.service.impl;

import com.pdsu.csc.bean.MyEmail;
import com.pdsu.csc.exception.web.user.NotFoundUidException;
import com.pdsu.csc.exception.web.user.email.EmailRepetitionException;
import com.pdsu.csc.exception.web.user.email.NotFoundEmailException;
import com.pdsu.csc.service.MyEmailService;

/**
 * @author 半梦
 * @create 2021-02-20 21:06
 */
public abstract class AbstractMyEmailService implements MyEmailService {
    @Override
    public boolean countByEmail(String email) {
        return false;
    }

    @Override
    public MyEmail selectMyEmailByEmail(String email) throws NotFoundEmailException {
        return null;
    }

    @Override
    public abstract MyEmail selectMyEmailByUid(Integer uid);

    @Override
    public boolean insert(MyEmail myEmail) throws EmailRepetitionException, NotFoundUidException {
        return false;
    }

    @Override
    public boolean countByUid(Integer uid) {
        return false;
    }
}
