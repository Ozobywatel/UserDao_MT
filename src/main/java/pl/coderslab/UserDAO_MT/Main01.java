package pl.coderslab.UserDAO_MT;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.util.InputMismatchException;

public class Main01 {

    public static void main(String[] args) {

//READ USER BY ID - CHECK OK
        UserDao userDao = new UserDao();
//        User user4 = userDao.getUserById(6);
//        System.out.println(user4);

        //       INSERT NEW USER - CHECK OK

//       User newUser = new User(0,"nowalalab@o2.pl", "Lala Kowalska","hwdadadwa");
//        UserDao userDao1 = new UserDao();
//        User createUser = userDao.create(newUser);

        //       UPDATE EXISTING USER DATA - CHECK ok

//        User editUser = userDao.getUserById(10);
//        if (editUser != null) {
//
//            System.out.println(editUser);
//            editUser.setEmail("martaGIE@op.pl");
//            editUser.setUsername("Marta grunt");
//            editUser.setPassword("fenawojfeiowa");
//            userDao.updateUsersData(editUser);
//
//        } else {
//            System.out.println("There is no such user assigned to given id!");
//        }

        //DELETE USER CHECK OK
   //    userDao.deleteUser(9);

        //SHOW ALL CHECK
        User[] all = userDao.showAll();
        for (User u :
                all) {
            System.out.println(u);
        }


    }
}
