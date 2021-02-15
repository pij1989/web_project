package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.controller.command.RequestParameter;
import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.creator.UserCreator;
import com.pozharsky.dmitri.model.creator.VerificationTokenCreator;
import com.pozharsky.dmitri.model.dao.TokenDao;
import com.pozharsky.dmitri.model.dao.TransactionManager;
import com.pozharsky.dmitri.model.dao.UserDao;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.entity.Token;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.error.ApplicationError;
import com.pozharsky.dmitri.model.error.ErrorType;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.util.PasswordEncrypter;
import com.pozharsky.dmitri.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final int ZERO = 0;
    private static final int AMOUNT_ATTEMPT = 3;
    private static final UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> loginUser(String email, String password, AtomicInteger blockingCount) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            if (checkEmailAndPassword(email, password)) {
                UserDao userDao = new UserDao();
                transactionManager.init(userDao);
                String findPassword = userDao.findPasswordByEmail(email);
                if (!findPassword.isBlank()) {
                    if (PasswordEncrypter.checkPassword(password, findPassword)) {
                        return userDao.findUserByEmail(email);
                    } else {
                        decrementCount(blockingCount);
                    }
                } else {
                    ApplicationError applicationError = ApplicationError.getInstance();
                    applicationError.addError(ErrorType.ERROR_USER_NOT_EXIST);
                }
            }
            return Optional.empty();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public boolean registrationUser(Map<String, String> userForm) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            boolean isCreate = false;
            UserValidator userValidator = UserValidator.getInstance();
            if (userValidator.isValidRegistrationForm(userForm)) {
                UserDao userDao = new UserDao();
                TokenDao tokenDao = new TokenDao();
                transactionManager.initTransaction(userDao, tokenDao);
                String email = userForm.get(RequestParameter.EMAIL);
                Optional<User> optionalUser = userDao.findUserByEmail(email);
                if (optionalUser.isEmpty()) {
                    String password = userForm.get(RequestParameter.PASSWORD);
                    String hashPassword = PasswordEncrypter.encryptPassword(password);
                    User user = UserCreator.createUser(userForm);
                    Optional<Long> optionalId = userDao.create(user, hashPassword);
                    if (optionalId.isPresent()) {
                        long userId = optionalId.get();
                        Token token = VerificationTokenCreator.createVerificationToken();
                        tokenDao.create(token, userId);
                        isCreate = true;
                    }
                } else {
                    ApplicationError errors = ApplicationError.getInstance();
                    errors.addError(ErrorType.ERROR_USER_EXIST);
                }
                transactionManager.commit();
            }
            return isCreate;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public boolean changeUserStatus(long id, StatusType statusType) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            UserDao userDao = new UserDao();
            transactionManager.initTransaction(userDao);
            boolean isChange = userDao.updateUserStatusById(id, statusType);
            transactionManager.commit();
            return isChange;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            UserDao userDao = new UserDao();
            transactionManager.init(userDao);
            return userDao.findUserByEmail(email);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            UserDao userDao = new UserDao();
            transactionManager.init(userDao);
            return userDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public boolean blockUser(String email, AtomicInteger blockingCount) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            boolean isBlocked = false;
            if (blockingCount.get() == ZERO) {
                UserDao userDao = new UserDao();
                transactionManager.initTransaction(userDao);
                Optional<User> optionalUser = userDao.findUserByEmail(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    if (!user.getStatusType().equals(StatusType.BLOCKED)) {
                        isBlocked = userDao.updateUserStatusById(user.getId(), StatusType.BLOCKED);
                    } else {
                        isBlocked = true;
                    }
                }
                transactionManager.commit();
                blockingCount.set(AMOUNT_ATTEMPT);
            }
            return isBlocked;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public boolean changeUserPassword(String email, String oldPassword, String newPassword) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            boolean isChange = false;
            UserDao userDao = new UserDao();
            transactionManager.initTransaction(userDao);
            String findPassword = userDao.findPasswordByEmail(email);
            if (!findPassword.isBlank()) {
                if (PasswordEncrypter.checkPassword(oldPassword, findPassword)) {
                    String hashPassword = PasswordEncrypter.encryptPassword(newPassword);
                    isChange = userDao.updatePasswordByEmail(email, hashPassword);
                }
            }
            transactionManager.commit();
            return isChange;
        } catch (DaoException e) {
            logger.error(e);
            transactionManager.rollback();
            throw new ServiceException(e);
        } finally {
            transactionManager.endTransaction();
        }
    }

    private boolean checkEmailAndPassword(String email, String password) {
        boolean isValid = true;
        UserValidator validator = UserValidator.getInstance();
        ApplicationError applicationError = ApplicationError.getInstance();
        if (!validator.isEmail(email)) {
            applicationError.addError(ErrorType.ERROR_USER_EMAIL);
            isValid = false;
        }
        if (!validator.isPassword(password)) {
            applicationError.addError(ErrorType.ERROR_USER_PASSWORD);
            isValid = false;
        }
        return isValid;
    }

    private void decrementCount(AtomicInteger count) {
        if (count.get() > ZERO) {
            count.getAndDecrement();
        }
    }
}
