# JAVA-PROJECT
# JAVA-PROJECT

READ THIS ENTIRE DATA FOR BETTER UNDERSTANDING THE PROJECT - IT'S A SIMPLE ENGLISH INFORMATION

Technologies required for understanding this project : Java, mySql and java database connection(for connecting java and database).

  Process to run this project sucessfully

  1. Save java files as per their class names
  2. create database named as - CollegeManagementSystem
  3. use CollegeManagementSystem; //use this command to enter into this database
  4. create tables as below :

            tableName : studentDetails
+-------------+--------------+------+-----+---------+-------+
| Field       | Type         | Null | Key | Default | Extra |
+-------------+--------------+------+-----+---------+-------+
| firstName   | varchar(100) | NO   |     | NULL    |       |
| lastName    | varchar(100) | NO   |     | NULL    |       |
| fullName    | varchar(255) | NO   |     | NULL    |       |
| email       | varchar(255) | NO   | PRI | NULL    |       |
| dateOfBirth | varchar(10)  | NO   |     | NULL    |       |
| fatherName  | varchar(255) | NO   |     | NULL    |       |
| course      | varchar(5)   | NO   |     | NULL    |       |
| feeAmount   | double       | NO   |     | NULL    |       |
| studentId   | varchar(255) | NO   | UNI | NULL    |       |
| password    | varchar(255) | NO   |     | NULL    |       |
+-------------+--------------+------+-----+---------+-------+

         tableName : teacherDetails
+--------------+--------------+------+-----+---------+-------+
| Field        | Type         | Null | Key | Default | Extra |
+--------------+--------------+------+-----+---------+-------+
| FirstName    | varchar(100) | NO   |     | NULL    |       |
| LastName     | varchar(255) | NO   |     | NULL    |       |
| FullName     | varchar(255) | NO   |     | NULL    |       |
| mobileNumber | bigint       | NO   | UNI | NULL    |       |
| personalMail | varchar(255) | NO   |     | NULL    |       |
| collegeMail  | varchar(255) | NO   | PRI | NULL    |       |
| Experience   | double       | NO   |     | NULL    |       |
| subjectName  | varchar(255) | NO   |     | NULL    |       |
| salary       | double       | NO   |     | NULL    |       |
| password     | varchar(255) | NO   |     | NULL    |       |
+--------------+--------------+------+-----+---------+-------+
          tableName : collegeAmount
+--------+--------+------+-----+---------+-------+
| Field  | Type   | Null | Key | Default | Extra |
+--------+--------+------+-----+---------+-------+
| amount | double | NO   |     | NULL    |       |
+--------+--------+------+-----+---------+-------+

//ABOUT PROJECT

Generally we have a college website where student, teacher, collegeManagement can login/Register and perform actions as per their requirements like below

                                Student :

Register - for first time visiting
           enter required details and at end it will create an unique studentID for you

login    - use studentID/mailID and password for logging into your account and perform following actions
          1. Pay College Fee - amount paid is added into collegeAmount
          2. check Fee due - returns how much amount you need to pay for college
          3. display Details - it displays all your details 

          
                                Teacher :

Apply for College - for first time visiting
                    Enter required details and finally it creates a collegeMailID for you

login             - use mobileNumber/CollegeMailID and password for logging into your account and perform actions as below
                  1. check whether salary credited or not

collegeAdministration - can perform following actions
                        1. displays students List along with details (except soft data like password etc)
                        2. displays teachers List along with details (except soft data like password etc)
                        3. paySalaryToTeachers - valid amount is debited from collegeAmount and credited to teachers Salary.
